package fr.efrei.residence;

/** Seul endroit qui sait construire le service de notification d'un canal. */
public class CanalNotificationFactory {

    private final CanalNotification email;
    private final CanalNotification sms;
    private final CanalNotification courrier;

    public CanalNotificationFactory(CanalNotification email,
                                    CanalNotification sms,
                                    CanalNotification courrier) {
        this.email = email;
        this.sms = sms;
        this.courrier = courrier;
    }

    public CanalNotification pour(Canal canal) {
        switch (canal) {
            case EMAIL:
                return email;
            case SMS:
                return sms;
            case COURRIER:
                return courrier;
            default:
                throw new IllegalArgumentException("Canal inconnu : " + canal);
        }
    }
}
