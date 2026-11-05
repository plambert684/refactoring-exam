package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class NotificationJournal {

    private final List<String[]> emails = new ArrayList<>();
    private final List<String[]> sms = new ArrayList<>();
    private final List<String[]> courriers = new ArrayList<>();
    private double postageCost;

    public void addEmail(String recipient, String subject, String body) {
        emails.add(new String[]{recipient, subject, body});
    }

    public void addSms(String phone, String message) {
        sms.add(new String[]{phone, message});
    }

    public void addLetter(String address, String subject, String body) {
        courriers.add(new String[]{address, subject, body});
        postageCost += CourrierService.PRIX_TIMBRE;
    }

    public List<String[]> emails() {
        return List.copyOf(emails);
    }

    public List<String[]> sms() {
        return List.copyOf(sms);
    }

    public List<String[]> courriers() {
        return List.copyOf(courriers);
    }

    public int smsCount() {
        return sms.size();
    }

    public double postageCost() {
        return postageCost;
    }
}
