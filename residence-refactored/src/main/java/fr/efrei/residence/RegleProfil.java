package fr.efrei.residence;

import java.util.Optional;

public interface RegleProfil {

    double TAUX_PENALITE = 0.05;
    double PENALITE_MIN = 10;

    double appliquerRemise(double montant, int echelon);

    boolean estSolvable(double ressources, double loyerChargesComprises);

    double depotGarantie(double loyerHorsCharges);

    double fraisDossier();

    int dureePreavisJours();

    default double penaliteRetard(double loyerChargesComprises) {
        return Utils.arrondir(Math.max(loyerChargesComprises * TAUX_PENALITE, PENALITE_MIN));
    }

    default Optional<String> motifRefusInscription(int echelon, String telephone) {
        return Optional.empty();
    }

    default boolean recoitEmailEnPlus() {
        return false;
    }
}
