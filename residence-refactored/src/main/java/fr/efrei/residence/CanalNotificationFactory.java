package fr.efrei.residence;

/** Seul endroit qui sait construire le service de notification d'un canal. */
public class CanalNotificationFactory {

    public static CanalNotification pour(Canal canal) {
        switch (canal) {
            case EMAIL:
                return new NotificationEmail();
            case SMS:
                return new NotificationSms(new SmsService());
            case COURRIER:
                return new NotificationCourrier(new CourrierService());
            default:
                throw new IllegalArgumentException("Canal inconnu : " + canal);
        }
    }
}
