package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

public class ServiceAttribution {

    private final Residence residence;
    private final BailRepository bailRepository;
    private final CalculateurLoyer calculateur;
    private final GenerateurIdentifiantBail identifiants;
    private final ServiceNotification notifications;

    public ServiceAttribution(Residence residence, BailRepository bailRepository,
                              CalculateurLoyer calculateur, GenerateurIdentifiantBail identifiants,
                              ServiceNotification notifications) {
        this.residence = residence;
        this.bailRepository = bailRepository;
        this.calculateur = calculateur;
        this.identifiants = identifiants;
        this.notifications = notifications;
    }

    public Bail assign(Etudiant etudiant, TypeLogement type, Set<Option> options,
                       LocalDate dateSignature) {
        System.out.println("--- Attribution d'un " + type + " à " + etudiant.getNomComplet());
        Logement choisi = null;
        double loyer = 0;
        double charges = 0;
        for (Logement logement : residence.all()) {
            if (logement.getType() != type || !logement.estLibre()) continue;
            if (etudiant.isPmr() && !logement.estAccessiblePMR()) continue;
            double candidat = calculateur.calculerLoyer(logement, etudiant.getProfil(),
                    etudiant.getEchelonBourse(), options);
            double chargesCandidat = calculateur.calculerCharges(logement);
            if (!etudiant.getProfil().regle().estSolvable(
                    etudiant.getRessourcesMensuelles(), candidat + chargesCandidat)) {
                System.out.println("  " + logement.getNumero() + " : ressources insuffisantes ("
                        + Utils.euros(candidat + chargesCandidat) + "/mois)");
                continue;
            }
            choisi = logement;
            loyer = candidat;
            charges = chargesCandidat;
            break;
        }
        if (choisi == null) {
            System.out.println("  Aucun logement disponible pour " + etudiant.getNomComplet());
            return null;
        }
        choisi.loger(etudiant);

        String id = identifiants.prochainId();
        Bail bail = new Bail(id, etudiant, choisi, dateSignature, options);
        bail.setLoyerHorsCharges(loyer);
        bail.setCharges(charges);
        RegleProfil regle = etudiant.getProfil().regle();
        bail.setCaution(Utils.arrondir(regle.depotGarantie(loyer)));
        bail.setFraisDossier(regle.fraisDossier());
        bailRepository.sauvegarder(bail);
        notifications.notify(etudiant, "Votre bail " + id,
                "Bienvenue à la " + Residence.NOM + " ! Logement " + choisi.getNumero()
                        + ", loyer " + Utils.euros(bail.getLoyerChargesComprises()) + " charges comprises.");
        return bail;
    }
}
