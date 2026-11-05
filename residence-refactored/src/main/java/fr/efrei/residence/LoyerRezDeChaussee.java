package fr.efrei.residence;

public class LoyerRezDeChaussee extends DecorateurLoyer {

    private static final double MINORATION_REZ_DE_CHAUSSEE = 0.95;

    public LoyerRezDeChaussee(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() * MINORATION_REZ_DE_CHAUSSEE;
    }
}
