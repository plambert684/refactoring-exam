package fr.efrei.residence;

import fr.efrei.residence.contrat.Canal;
import fr.efrei.residence.contrat.DemandeAttribution;
import fr.efrei.residence.contrat.DemandeDevis;
import fr.efrei.residence.contrat.DemandeInscription;
import fr.efrei.residence.contrat.DemandeLogement;
import fr.efrei.residence.contrat.EtatBail;
import fr.efrei.residence.contrat.EtatLogement;
import fr.efrei.residence.contrat.Notification;
import fr.efrei.residence.contrat.Option;
import fr.efrei.residence.contrat.StatutBail;
import fr.efrei.residence.contrat.SystemeResidence;
import fr.efrei.residence.contrat.TypeLogement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

/**
 * Branche le code historique sur les tests de contrat.
 * <p>
 * Tout le « bricolage » nécessaire pour tester l'ancien code (état statique à vider,
 * conversion des codes {@code String}, booléens d'options…) est confiné ici.
 */
public class NouveauSystemeAdapter implements SystemeResidence {

    private GestionResidence gestion;
    private EtudiantRepository etudiantRepository;
    private BailRepository bailRepository;
    private NotificationJournal journal;
    private GenerateurQuittance generateurQuittance;
    private Residence residence;
    private CalculateurLoyer calculateur;

    @Override
    public void reinitialiser() {
        residence = new Residence();
        etudiantRepository = new EtudiantRepositoryMemoire();
        bailRepository = new BailRepositoryMemoire();
        journal = new NotificationJournal();
        CanalNotificationFactory canaux = new CanalNotificationFactory(
                new NotificationEmail(new EmailService(journal)),
                new NotificationSms(new SmsService(journal)),
                new NotificationCourrier(new CourrierService(journal))
        );
        generateurQuittance = new GenerateurQuittance();
        ServiceNotification serviceNotifications = new ServiceNotification(canaux);
        calculateur = new CalculateurLoyer(new AssembleurLoyer());
        ServiceAttribution attributions = new ServiceAttribution(residence, bailRepository,
                calculateur, new GenerateurIdentifiantBail(), serviceNotifications);
        ServiceRapport rapport = new ServiceRapport(residence, bailRepository, journal);
        ServiceBail serviceBail = new ServiceBail(bailRepository, serviceNotifications);
        ServiceLogement serviceLogement = new ServiceLogement(residence);
        gestion = new GestionResidence(
                etudiantRepository,
                bailRepository,
                new GenerateurIdentifiantBail(),
                serviceNotifications,
                journal,
                new ServiceInscription(etudiantRepository, new GenerateurIdentifiantEtudiant()),
                new ServiceRelance(bailRepository, serviceNotifications),
                residence,
                attributions,
                rapport,
                serviceBail,
                serviceLogement
        );
    }

    @Override
    public Optional<String> inscrire(DemandeInscription d) {
        Etudiant e = gestion.inscrireEtudiant(d.nom(), d.prenom(), d.email(), d.telephone(), d.adresse(),
                fr.efrei.residence.Profil.valueOf(d.profil().name()), d.echelonBourse(), d.ressourcesMensuelles(), d.pmr(), fr.efrei.residence.Canal.valueOf(d.canalPrefere().name()));
        return Optional.ofNullable(e).map(Etudiant::getId);
    }

    @Override
    public boolean ajouterLogement(DemandeLogement d) {
        int param = d.type() == TypeLogement.COLOCATION ? d.places() : d.surfaceBalcon();
        return gestion.ajouterLogement(fr.efrei.residence.TypeLogement.valueOf(d.type().name()), d.numero(), d.surface(), d.etage(), d.meuble(), param) != null;
    }

    @Override
    public Optional<EtatLogement> logement(String numero) {
        Logement l = residence.find(numero);
        if (l == null) {
            return Optional.empty();
        }
        int occupants = l.getNombreOccupants();
        
        return Optional.of(new EtatLogement(l.getNumero(), TypeLogement.valueOf(l.getType().name()), l.getNombrePlaces(),
                occupants, l.estAccessiblePMR()));
    }

    @Override
    public OptionalDouble devisLoyer(DemandeDevis d) {
        Logement l = residence.find(d.numeroLogement());
        if (l == null) {
            return OptionalDouble.empty();
        }
        Set<Option> o = d.options();
        return OptionalDouble.of(calculateur.calculerLoyer(l,
                fr.efrei.residence.Profil.valueOf(d.profil().name()), d.echelonBourse(),
                convertirOptions(o)));
    }

    @Override
    public Optional<String> attribuer(DemandeAttribution d) {
        Etudiant e = etudiant(d.refEtudiant());
        if (e == null) {
            return Optional.empty();
        }
        Set<Option> o = d.options();
        Bail b = gestion.attribuerLogement(e, fr.efrei.residence.TypeLogement.valueOf(d.type().name()),
                convertirOptions(o), d.dateSignature());
        return Optional.ofNullable(b).map(Bail::getId);
    }

    @Override
    public Optional<EtatBail> consulter(String idBail) {
        Bail b = bailRepository.trouver(idBail);
        if (b == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatBail(b.getId(), b.getLogement().getNumero(), StatutBail.valueOf(b.getStatut().name()),
                b.getLoyerHorsCharges(), b.getCharges(), b.getLoyerChargesComprises(), b.getCaution(),
                b.getFraisDossier(), b.getDateFin(), b.soldeDu, b.cautionRestituee));
    }

    @Override
    public boolean entrer(String idBail, LocalDate date) {
        return gestion.entrerDansLesLieux(idBail, date);
    }

    @Override
    public boolean payer(String idBail, YearMonth mois, LocalDate date, double montant) {
        return gestion.payerLoyer(idBail, mois.toString(), date, montant);
    }

    @Override
    public boolean donnerPreavis(String idBail, LocalDate date) {
        return gestion.donnerPreavis(idBail, date);
    }

    @Override
    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return gestion.sortir(idBail, date, retenuesDegats);
    }

    @Override
    public boolean annuler(String idBail) {
        return gestion.annulerBail(idBail);
    }

    @Override
    public Optional<String> quittance(String idBail, YearMonth mois) {
        return Optional.ofNullable(bailRepository.trouver(idBail))
                .map(b -> generateurQuittance.generate(b, mois.toString()));
    }

    @Override
    public void relancerImpayes() {
        gestion.relancerImpayes();
    }

    @Override
    public String rapport() {
        return gestion.genererRapport();
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> notifications = new ArrayList<>();
        for (String[] email : journal.emails()) {
            notifications.add(new Notification(Canal.EMAIL, email[0], email[1]));
        }
        for (String[] sms : journal.sms()) {
            notifications.add(new Notification(Canal.SMS, sms[0], sms[1]));
        }
        for (String[] courrier : journal.courriers()) {
            notifications.add(new Notification(Canal.COURRIER, courrier[0], courrier[1]));
        }
        return notifications;
    }

    private Etudiant etudiant(String ref) {
        return etudiantRepository.trouver(ref);
    }

    private Set<fr.efrei.residence.Option> convertirOptions(Set<Option> options) {
        Set<fr.efrei.residence.Option> resultat = java.util.EnumSet.noneOf(fr.efrei.residence.Option.class);
        for (Option option : options) {
            resultat.add(fr.efrei.residence.Option.valueOf(option.name()));
        }
        return resultat;
    }
}