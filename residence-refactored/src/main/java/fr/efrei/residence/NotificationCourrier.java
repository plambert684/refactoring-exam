package fr.efrei.residence;

public class NotificationCourrier implements CanalNotification {

    private final CourrierService courrier;

    public NotificationCourrier(CourrierService courrier) {
        this.courrier = courrier;
    }

    public void envoyer(Etudiant e, String sujet, String message) {
        courrier.send(e.getNomComplet(), e.getAdresse(), sujet, message);
    }

    public void relancer(Etudiant e, Bail b) {
        courrier.send(e.getNomComplet(), e.getAdresse(), "Relance loyer impayé",
                "Il reste " + Utils.euros(b.soldeDu) + " à régler sur le bail " + b.getId() + ".");
    }
}
