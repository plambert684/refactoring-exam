package fr.efrei.residence;

public class LoyerMeuble extends DecorateurLoyer {

    private static final double MAJORATION_MEUBLE = 1.15;

    public LoyerMeuble(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() * MAJORATION_MEUBLE;
    }
}