package fr.efrei.residence;

public class LoyerCollocation extends DecorateurLoyer {

    public LoyerCollocation(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant();
    }
}
