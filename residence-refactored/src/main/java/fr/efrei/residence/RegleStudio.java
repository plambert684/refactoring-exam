package fr.efrei.residence;

public class RegleStudio implements RegleTypeLogement {

    private static final double PRIX_M2 = 14;
    private static final double LOYER_MIN = 350;

    public double loyerBase(Logement l) {
        double loyer = l.getSurface() * PRIX_M2;
        if (loyer < LOYER_MIN) loyer = LOYER_MIN;
        return loyer;
    }
}
