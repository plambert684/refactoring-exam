package fr.efrei.residence;

import java.util.Optional;

public class RegleInternational implements RegleProfil {

    private static final String PREFIXE_INTERNATIONAL = "+";
    private static final double COEF_SOLVABILITE = 3;
    private static final double MOIS_DEPOT = 2;
    private static final double FRAIS_DOSSIER = 150;
    private static final int PREAVIS_JOURS = 30;

    public double appliquerRemise(double montant, int echelon) {
        return montant;
    }

    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * COEF_SOLVABILITE;
    }

    public double depotGarantie(double loyerHorsCharges) {
        return loyerHorsCharges * MOIS_DEPOT;
    }

    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    public int dureePreavisJours() {
        return PREAVIS_JOURS;
    }

    @Override
    public Optional<String> motifRefusInscription(int echelon, String telephone) {
        if (telephone == null || !telephone.startsWith(PREFIXE_INTERNATIONAL)) {
            return Optional.of("numéro international (+XX) obligatoire");
        }
        return Optional.empty();
    }

    @Override
    public boolean recoitEmailEnPlus() {
        return true;
    }
}
