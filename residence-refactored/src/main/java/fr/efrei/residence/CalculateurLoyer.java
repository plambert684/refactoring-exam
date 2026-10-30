package fr.efrei.residence;

public class CalculateurLoyer {

    private static final double MAJORATION_MEUBLE = 1.15;
    private static final int ETAGE_REZ_DE_CHAUSSEE = 0;
    private static final double MINORATION_REZ_DE_CHAUSSEE = 0.95;
    private static final double PRIX_PARKING = 45;
    private static final double PRIX_LAVERIE = 12;
    private static final double PRIX_ASSURANCE = 9;
    private static final double CENTIMES = 100.0;

    /** Loyer mensuel hors charges, options comprises, remise déduite. */
    public double calculerLoyer(Logement l, Profil profil, int echelon, boolean parking, boolean menage,
                                boolean laverie, boolean fibre, boolean assurance) {
        RegleTypeLogement regle = l.getType().regle();
        double loyer = regle.loyerBase(l);
        if (l.isMeuble()) loyer = loyer * MAJORATION_MEUBLE;
        if (l.getEtage() == ETAGE_REZ_DE_CHAUSSEE) loyer = loyer * MINORATION_REZ_DE_CHAUSSEE;
        if (menage) loyer = loyer * regle.majorationMenage();
        if (parking) loyer = loyer + PRIX_PARKING;
        if (laverie) loyer = loyer + PRIX_LAVERIE;
        if (fibre) loyer = loyer + regle.prixFibre();
        if (assurance) loyer = loyer + PRIX_ASSURANCE;
        loyer = profil.regle().appliquerRemise(loyer, echelon);
        return Math.round(loyer * CENTIMES) / CENTIMES;
    }

    public double calculerCharges(Logement l) {
        return l.getType().regle().charges();
    }
}
