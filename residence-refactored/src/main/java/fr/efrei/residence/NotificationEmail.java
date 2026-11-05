package fr.efrei.residence;

public class NotificationEmail implements CanalNotification {

    private final EmailService email;

    public NotificationEmail(EmailService email) {
        this.email = email;
    }

    public void envoyer(Etudiant e, String sujet, String message) {
        email.send(e.getEmail(), sujet, message);
    }

    public void relancer(Etudiant e, Bail b) {
        email.send(e.getEmail(), "RELANCE - loyer impayé",
                "Bonjour " + e.getPrenom() + ", il reste " + Utils.euros(b.soldeDu) + " à régler sur le bail " + b.getId() + ".");
    }
}
