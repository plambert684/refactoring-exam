package fr.efrei.residence;

public class ServiceRelance {

    private final BailRepository bailRepository;
    private final ServiceNotification notifications;

    public ServiceRelance(BailRepository bailRepository, ServiceNotification notifications) {
        this.bailRepository = bailRepository;
        this.notifications = notifications;
    }

    public void notifyOverdue() {
        System.out.println("--- Relance des impayés");
        for (Bail bail : bailRepository.tous()) {
            if (bail.soldeDu <= 0 || bail.getStatut() == StatutBail.ANNULE) continue;
            notifications.relancer(bail.getEtudiant(), bail);
        }
    }
}
