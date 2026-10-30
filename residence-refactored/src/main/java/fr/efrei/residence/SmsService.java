package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class SmsService {
    private static final int LONGUEUR_MIN_NUMERO = 10;
    private static final int LONGUEUR_MAX_SMS = 160;
    private static final String SUITE_TRONQUEE = "...";

    public static int compteurSms = 0;
    public static List<String[]> journal = new ArrayList<String[]>();

    public void envoyerSms(String numero, String message) {
        if (numero == null || numero.length() < LONGUEUR_MIN_NUMERO) {
            System.out.println("[SMS] numéro invalide : " + numero);
            return;
        }
        if (message.length() > LONGUEUR_MAX_SMS) {
            message = message.substring(0, LONGUEUR_MAX_SMS - SUITE_TRONQUEE.length()) + SUITE_TRONQUEE;
        }
        compteurSms++;
        journal.add(new String[]{numero, message});
        System.out.println("[SMS] -> " + numero + " : " + message);
    }
}
