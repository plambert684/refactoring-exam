package fr.efrei.residence;

public class GenerateurQuittance {

    public String generate(Bail bail, String mois) {
        Paiement paiement = bail.findPayment(mois);
        if (paiement == null) {
            return "Aucun paiement pour " + mois;
        }

        String options = formatOptions(bail);
        String q = "";
        q += "==========================================\n";
        q += "   RÉSIDENCE CAMPUS VERT - QUITTANCE\n";
        q += "==========================================\n";
        q += "Bail      : " + bail.getId() + "\n";
        q += "Locataire : " + bail.getEtudiant().getNomComplet() + " (" + bail.getEtudiant().getProfil() + ")\n";
        q += "Logement  : " + bail.getLogement().getNumero() + " - " + bail.getLogement().getType() + " "
                + bail.getLogement().getSurface() + " m²";
        if (bail.getLogement().isMeuble()) q += " meublé";
        q += "\n";
        q += "Période   : " + mois + "\n";
        q += "Options   : " + options + "\n";
        q += "------------------------------------------\n";
        q += "Loyer HC  : " + Utils.euros(bail.getLoyerHorsCharges()) + "\n";
        q += "Charges   : " + Utils.euros(bail.getCharges()) + "\n";
        if (paiement.penalite > 0) q += "Pénalité  : " + Utils.euros(paiement.penalite) + "\n";
        q += "Total dû  : " + Utils.euros(paiement.du) + "\n";
        q += "Payé le   : " + Utils.date(paiement.date) + " : " + Utils.euros(paiement.montant) + "\n";
        if (paiement.montant < paiement.du) {
            q += "RESTE DÛ  : " + Utils.euros(Utils.arrondir(paiement.du - paiement.montant)) + "\n";
        }
        q += "==========================================";
        return q;
    }

    private String formatOptions(Bail bail) {
        StringBuilder options = new StringBuilder();
        if (bail.menage) options.append("ménage, ");
        if (bail.parking) options.append("parking, ");
        if (bail.laverie) options.append("laverie, ");
        if (bail.fibre) options.append("fibre, ");
        if (bail.assurance) options.append("assurance, ");
        return options.length() == 0
                ? "aucune"
                : options.substring(0, options.length() - 2);
    }
}
