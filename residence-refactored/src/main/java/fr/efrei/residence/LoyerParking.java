package fr.efrei.residence;

public class LoyerParking extends DecorateurLoyer {

    private static final double PRIX_PARKING = 45;

    public LoyerParking(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() + PRIX_PARKING;
    }
}
