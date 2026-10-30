package fr.efrei.residence;

public class NotificationEmail implements CanalNotification {

    public void envoyer(Etudiant e, String sujet, String message) {
        EmailService.envoyer(e.getEmail(), sujet, message);
    }

    public void relancer(Etudiant e, Bail b) {
        EmailService.envoyer(e.getEmail(), "RELANCE - loyer impayé",
                "Bonjour " + e.getPrenom() + ", il reste " + Utils.euros(b.soldeDu) + " à régler sur le bail " + b.getId() + ".");
    }
}
