package fr.efrei.residence;

import java.time.LocalDate;
import java.time.YearMonth;

public class ServiceBail {

    private static final int DUE_DAY = 5;
    private static final int REFUND_DELAY_DAYS = 30;

    private final BailRepository baux;
    private final ServiceNotification notifications;

    public ServiceBail(BailRepository baux, ServiceNotification notifications) {
        this.baux = baux;
        this.notifications = notifications;
    }

    public boolean enter(String id, LocalDate date) {
        Bail bail = baux.trouver(id);
        if (bail == null || bail.getStatut() != StatutBail.SIGNE) return false;
        if (date.isBefore(bail.getDateSignature())) return false;
        bail.setDateEntree(date);
        bail.setStatut(StatutBail.ACTIF);
        baux.mettreAJour(bail);
        notifications.notify(bail.getEtudiant(), "État des lieux d'entrée",
                "Votre état des lieux d'entrée du logement " + bail.getLogement().getNumero()
                        + " est fait le " + Utils.date(date)
                        + ". Remise des clés effectuée. Pensez à souscrire une assurance habitation si ce n'est pas déjà fait.");
        return true;
    }

    public boolean pay(String id, String month, LocalDate paymentDate, double amount) {
        Bail bail = baux.trouver(id);
        if (bail == null || !(bail.getStatut() == StatutBail.ACTIF
                || bail.getStatut() == StatutBail.PREAVIS)) return false;
        LocalDate dueDate = YearMonth.parse(month).atDay(DUE_DAY);
        double penalty = paymentDate.isAfter(dueDate)
                ? bail.getEtudiant().getProfil().regle().penaliteRetard(bail.getLoyerChargesComprises())
                : 0;
        double due = Utils.arrondir(bail.getLoyerChargesComprises() + penalty);
        bail.paiements.add(new Paiement(month, paymentDate, amount, penalty, due));
        bail.soldeDu = Utils.arrondir(bail.soldeDu + due - amount);
        baux.mettreAJour(bail);
        if (amount < due) {
            notifications.notify(bail.getEtudiant(), "Paiement incomplet " + month,
                    "Nous avons reçu " + Utils.euros(amount) + " sur " + Utils.euros(due)
                            + ". Reste dû : " + Utils.euros(Utils.arrondir(due - amount)) + ".");
        } else {
            notifications.notify(bail.getEtudiant(), "Quittance " + month,
                    "Merci, votre loyer de " + month + " est réglé.");
        }
        return true;
    }

    public boolean giveNotice(String id, LocalDate date) {
        Bail bail = baux.trouver(id);
        if (bail == null || bail.getStatut() != StatutBail.ACTIF) return false;
        int days = bail.getEtudiant().getProfil().regle().dureePreavisJours();
        bail.setDateFin(date.plusDays(days));
        bail.setStatut(StatutBail.PREAVIS);
        baux.mettreAJour(bail);
        notifications.notify(bail.getEtudiant(), "Préavis de départ",
                "Votre préavis de " + days + " jours est enregistré. Fin du bail le "
                        + Utils.date(bail.getDateFin()) + ".");
        return true;
    }

    public boolean leave(String id, LocalDate date, double damageRetention) {
        Bail bail = baux.trouver(id);
        if (bail == null || bail.getStatut() != StatutBail.PREAVIS
                || date.isBefore(bail.getDateFin())) return false;
        Logement logement = bail.getLogement();
        logement.liberer(bail.getEtudiant());
        logement.apresSortie();
        double unpaid = Math.max(bail.soldeDu, 0);
        double refund = bail.getCaution() - damageRetention - unpaid;
        double remaining = Math.max(-refund, 0);
        bail.retenues = Utils.arrondir(damageRetention);
        bail.cautionRestituee = Utils.arrondir(Math.max(refund, 0));
        bail.soldeDu = Utils.arrondir(remaining);
        bail.setStatut(StatutBail.TERMINE);
        baux.mettreAJour(bail);
        String message = "Votre dépôt de garantie : " + Utils.euros(bail.cautionRestituee)
                + " vous sera restitué sous " + REFUND_DELAY_DAYS + " jours.";
        if (remaining > 0) message += " Il reste " + Utils.euros(bail.soldeDu) + " à régler.";
        notifications.notify(bail.getEtudiant(), "Sortie du logement " + logement.getNumero(), message);
        return true;
    }

    public boolean cancel(String id) {
        Bail bail = baux.trouver(id);
        if (bail == null || bail.getStatut() != StatutBail.SIGNE) return false;
        Logement logement = bail.getLogement();
        logement.liberer(bail.getEtudiant());
        bail.setStatut(StatutBail.ANNULE);
        baux.mettreAJour(bail);
        notifications.notify(bail.getEtudiant(), "Annulation du bail " + id,
                "Votre bail est annulé. Les frais de dossier (" + Utils.euros(bail.getFraisDossier())
                        + ") restent acquis.");
        return true;
    }
}
