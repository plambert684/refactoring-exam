package fr.efrei.residence;

public class RegleT2 implements RegleTypeLogement {

    private static final double PRIX_M2 = 12;
    private static final double FORFAIT = 100;

    public double loyerBase(Logement l) {
        return l.getSurface() * PRIX_M2 + FORFAIT;
    }
}
