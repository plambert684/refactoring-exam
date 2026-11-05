package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

public class GestionResidence {

    private final ServiceInscription inscriptions;
    private final ServiceLogement logements;
    private final ServiceAttribution attributions;
    private final ServiceBail baux;
    private final ServiceRelance relances;
    private final ServiceRapport rapport;

    public GestionResidence(EtudiantRepository etudiantRepository,
                            BailRepository bailRepository,
                            GenerateurIdentifiantBail generateurIdentifiantBail,
                            ServiceNotification notifications,
                            NotificationJournal journal,
                            ServiceInscription inscriptions,
                            ServiceRelance relances,
                            Residence residence,
                            ServiceAttribution attributions,
                            ServiceRapport rapport,
                            ServiceBail serviceBail,
                            ServiceLogement serviceLogement) {
        this.inscriptions = inscriptions;
        this.logements = serviceLogement;
        this.attributions = attributions;
        this.baux = serviceBail;
        this.relances = relances;
        this.rapport = rapport;
    }

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone,
                                     String adresse, Profil profil, int echelonBourse,
                                     double ressources, boolean pmr, Canal canalPrefere) {
        return inscriptions.register(nom, prenom, email, telephone, adresse, profil,
                echelonBourse, ressources, pmr, canalPrefere);
    }

    public Logement ajouterLogement(TypeLogement type, String numero, double surface,
                                    int etage, boolean meuble, int param) {
        return logements.add(type, numero, surface, etage, meuble, param);
    }

    public Logement ajouterLogement(String code, String numero, double surface,
                                    int etage, boolean meuble, int param) {
        TypeLogement type = TypeLogement.depuisCode(code);
        return type == null ? null : ajouterLogement(type, numero, surface, etage, meuble, param);
    }

    public Bail attribuerLogement(Etudiant etudiant, TypeLogement type, Set<Option> options,
                                  LocalDate dateSignature) {
        return attributions.assign(etudiant, type, options, dateSignature);
    }

    public boolean entrerDansLesLieux(String id, LocalDate date) {
        return baux.enter(id, date);
    }

    public boolean payerLoyer(String id, String mois, LocalDate date, double montant) {
        return baux.pay(id, mois, date, montant);
    }

    public boolean donnerPreavis(String id, LocalDate date) {
        return baux.giveNotice(id, date);
    }

    public boolean sortir(String id, LocalDate date, double retenuesDegats) {
        return baux.leave(id, date, retenuesDegats);
    }

    public boolean annulerBail(String id) {
        return baux.cancel(id);
    }

    public void relancerImpayes() {
        relances.notifyOverdue();
    }

    public void afficherRapport() {
        System.out.println();
        System.out.println(genererRapport());
    }

    public String genererRapport() {
        return rapport.generate();
    }
}
