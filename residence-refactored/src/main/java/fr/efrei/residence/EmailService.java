package fr.efrei.residence;

public class EmailService {

    public static final String SMTP_HOST = "smtp.campusvert.fr";
    public static final String EXPEDITEUR = "gestion@campusvert.fr";

    // garde une trace des emails envoyés (demandé par l'administration)
    private final NotificationJournal journal;

    public EmailService(NotificationJournal journal) {
        this.journal = journal;
    }

    public boolean send(String destinataire, String sujet, String corps) {
        if (!Utils.emailValide(destinataire)) {
            return false;
        }
        System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
        System.out.println("        " + corps);
        journal.addEmail(destinataire, sujet, corps);
        return true;
    }
}
