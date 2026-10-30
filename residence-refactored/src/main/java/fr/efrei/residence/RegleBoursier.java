package fr.efrei.residence;

import java.util.Optional;

public class RegleBoursier implements RegleProfil {

    private static final int ECHELON_MIN = 0;
    private static final int ECHELON_MAX = 7;
    private static final double REMISE_PAR_ECHELON = 0.05;
    private static final double REMISE_MAX = 0.30;
    private static final int PREAVIS_JOURS = 15;

    public double appliquerRemise(double montant, int echelon) {
        double taux = echelon * REMISE_PAR_ECHELON;
        if (taux > REMISE_MAX) taux = REMISE_MAX;
        return montant * (1 - taux);
    }

    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return true;
    }

    public double depotGarantie(double loyerHorsCharges) {
        return 0;
    }

    public double fraisDossier() {
        return 0;
    }

    public int dureePreavisJours() {
        return PREAVIS_JOURS;
    }

    @Override
    public double penaliteRetard(double loyerChargesComprises) {
        return 0;
    }

    @Override
    public Optional<String> motifRefusInscription(int echelon, String telephone) {
        if (echelon < ECHELON_MIN || echelon > ECHELON_MAX) {
            return Optional.of("échelon de bourse invalide");
        }
        return Optional.empty();
    }
}
