package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer {

    private static final double CENTIMES = 100.0;

    private final AssembleurLoyer assembleur;

    public CalculateurLoyer() {
        this.assembleur = new AssembleurLoyer();
    }

    public double calculerLoyer(Logement logement, Profil profil, int echelon, Set<Option> options) {

        double montantAvecOptions = assembleur.assembler(logement, options).montant();

        double montantAvecRemise = profil.regle().appliquerRemise(montantAvecOptions, echelon);

        return Math.round(montantAvecRemise * CENTIMES) / CENTIMES;
    }

    public double calculerCharges(Logement logement) {
        return logement.getType().regle().charges();
    }
}