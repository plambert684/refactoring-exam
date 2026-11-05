package fr.efrei.residence;

public class AssembleurLoyer {

    private static final int ETAGE_REZ_DE_CHAUSSEE = 0;

    public Loyer assembler(Logement logement, boolean parking, boolean menage, boolean laverie, boolean fibre, boolean assurance) {

        RegleTypeLogement regle = logement.getType().regle();

        Loyer loyer = new LoyerDeBase(logement);

        if (logement.isMeuble()) {
            loyer = new LoyerMeuble(loyer);
        }

        if (logement.getEtage() == ETAGE_REZ_DE_CHAUSSEE) {
            loyer = new LoyerRezDeChaussee(loyer);
        }

        if (menage) {
            loyer = new LoyerMenage(loyer, regle.majorationMenage());
        }

        if (parking) {
            loyer = new LoyerParking(loyer);
        }

        if (laverie) {
            loyer = new LoyerLaverie(loyer);
        }

        if (fibre) {
            loyer = new LoyerFibre(loyer, regle.prixFibre());
        }

        if (assurance) {
            loyer = new LoyerAssurance(loyer);
        }

        return loyer;
    }
}
