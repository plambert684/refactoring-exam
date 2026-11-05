package fr.efrei.residence;

public class LoyerMenage extends DecorateurLoyer {

    private final double majoration;

    public LoyerMenage(Loyer loyer, double majoration) {
        super(loyer);
        this.majoration = majoration;
    }

    @Override
    public double montant() {
        return loyer.montant() * majoration;
    }
}