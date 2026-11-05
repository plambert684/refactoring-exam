package fr.efrei.residence;

public interface RegleTypeLogement {

    double MAJORATION_MENAGE = 1.08;
    double PRIX_FIBRE = 20;
    double CHARGES = 35;

    double loyerBase(Logement l);

    default double majorationMenage() {
        return MAJORATION_MENAGE;
    }

    default double prixFibre() {
        return PRIX_FIBRE;
    }

    default double charges() {
        return CHARGES;
    }

    default int ajusterParametre(int param) {
        return param;
    }

}
