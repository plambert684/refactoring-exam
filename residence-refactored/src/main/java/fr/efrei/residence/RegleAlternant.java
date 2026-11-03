package fr.efrei.residence;

public class RegleAlternant implements RegleProfil {

    private static final double COEF_REMISE = 0.90;
    private static final double COEF_SOLVABILITE = 2;
    private static final double FRAIS_DOSSIER = 100;
    private static final int PREAVIS_JOURS = 15;

    public double appliquerRemise(double montant, int echelon) {
        return montant * COEF_REMISE;
    }

    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * COEF_SOLVABILITE;
    }

    public double depotGarantie(double loyerHorsCharges) {
        return loyerHorsCharges;
    }

    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    public int dureePreavisJours() {
        return PREAVIS_JOURS;
    }
}
