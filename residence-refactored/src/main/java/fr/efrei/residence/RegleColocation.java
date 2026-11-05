package fr.efrei.residence;

public class RegleColocation implements RegleTypeLogement {

    private static final double PRIX_M2 = 11;
    private static final double MAJORATION_MENAGE_COLOCATION = 1.04;
    private static final double CHARGES_COLOCATION = 25;
    private static final int PLACES_MIN = 2;

    public double loyerBase(Logement l) {
        return l.getSurface() * PRIX_M2 / l.getNombrePlaces();
    }

    @Override
    public double majorationMenage() {
        return MAJORATION_MENAGE_COLOCATION; // parties communes déjà nettoyées
    }

    @Override
    public double prixFibre() {
        return 0; // fibre incluse en coloc
    }

    @Override
    public double charges() {
        return CHARGES_COLOCATION;
    }

    @Override
    public int ajusterParametre(int param) {
        return Math.max(param, PLACES_MIN);
    }

}
