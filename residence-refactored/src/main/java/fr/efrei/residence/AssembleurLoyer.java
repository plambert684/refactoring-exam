package fr.efrei.residence;

import java.util.Set;

public class AssembleurLoyer {

    private static final int ETAGE_REZ_DE_CHAUSSEE = 0;

    public Loyer assembler(Logement logement, Set<Option> options) {

        RegleTypeLogement regle = logement.getType().regle();

        Loyer loyer = new LoyerDeBase(logement);

        if (logement.isMeuble()) {
            loyer = new LoyerMeuble(loyer);
        }

        if (logement.getEtage() == ETAGE_REZ_DE_CHAUSSEE) {
            loyer = new LoyerRezDeChaussee(loyer);
        }

        if (options.contains(Option.MENAGE)) {
            loyer = new LoyerMenage(loyer, regle.majorationMenage());
        }

        if (options.contains(Option.PARKING)) {
            loyer = new LoyerParking(loyer);
        }

        if (options.contains(Option.LAVERIE)) {
            loyer = new LoyerLaverie(loyer);
        }

        if (options.contains(Option.FIBRE)) {
            loyer = logement.getType() == TypeLogement.COLOCATION
                    ? new LoyerCollocation(loyer)
                    : new LoyerFibre(loyer, regle.prixFibre());
        }

        if (options.contains(Option.ASSURANCE)) {
            loyer = new LoyerAssurance(loyer);
        }

        return loyer;
    }
}
