package fr.efrei.residence;

public class CalculateurLoyer {

    private static final double CENTIMES = 100.0;

    private final AssembleurLoyer assembleur;

    public CalculateurLoyer() {
        this.assembleur = new AssembleurLoyer();
    }

    public double calculerLoyer(Logement logement, Profil profil, int echelon, boolean parking, boolean menage, boolean laverie, boolean fibre, boolean assurance) {

        double montantAvecOptions = assembleur.assembler(logement,parking,menage,laverie,fibre,assurance).montant();

        double montantAvecRemise = profil.regle().appliquerRemise(montantAvecOptions, echelon);

        return Math.round(montantAvecRemise * CENTIMES) / CENTIMES;
    }

    public double calculerCharges(Logement logement) {
        return logement.getType().regle().charges();
    }
}