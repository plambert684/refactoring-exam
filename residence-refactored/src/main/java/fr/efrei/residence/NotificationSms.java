package fr.efrei.residence;

public class NotificationSms implements CanalNotification {

    private final SmsService sms;

    public NotificationSms(SmsService sms) {
        this.sms = sms;
    }

    public void envoyer(Etudiant e, String sujet, String message) {
        sms.envoyerSms(e.getTelephone(), sujet + " - " + message);
    }

    public void relancer(Etudiant e, Bail b) {
        sms.envoyerSms(e.getTelephone(), "Campus Vert : " + Utils.euros(b.soldeDu) + " impayés sur " + b.getId());
    }
}
