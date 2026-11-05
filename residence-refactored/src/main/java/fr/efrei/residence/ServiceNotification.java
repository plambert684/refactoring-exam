package fr.efrei.residence;

public class ServiceNotification {

    private final CanalNotificationFactory factory;

    public ServiceNotification(CanalNotificationFactory factory) {
        this.factory = factory;
    }

    public void notify(Etudiant etudiant, String sujet, String message) {
        CanalNotification canal =
                factory.pour(etudiant.getCanalPrefere());

        canal.envoyer(etudiant, sujet, message);
        if (etudiant.getProfil().regle().recoitEmailEnPlus()
                && etudiant.getCanalPrefere() != Canal.EMAIL) {
            notifyByMail(etudiant, "[EN] " + sujet, message);
        }
    }

    public void notifyByMail(Etudiant etudiant, String sujet, String message) {
        factory.pour(Canal.EMAIL).envoyer(etudiant, sujet, message);
    }

    public void relancer(Etudiant etudiant, Bail bail) {
        CanalNotification canal =
                factory.pour(etudiant.getCanalPrefere());

        canal.relancer(etudiant, bail);
    }
}