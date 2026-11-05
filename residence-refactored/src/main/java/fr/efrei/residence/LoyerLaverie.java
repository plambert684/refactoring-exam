package fr.efrei.residence;

public class LoyerLaverie extends DecorateurLoyer {

    private static final double PRIX_LAVIERIE = 12;

    public LoyerLaverie(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() + PRIX_LAVIERIE;
    }
}
