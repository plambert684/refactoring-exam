package fr.efrei.residence;

public class RegleT1 implements RegleTypeLogement {

    private static final double PRIX_M2 = 13;
    private static final double FORFAIT = 50;

    public double loyerBase(Logement l) {
        return l.getSurface() * PRIX_M2 + FORFAIT;
    }
}
