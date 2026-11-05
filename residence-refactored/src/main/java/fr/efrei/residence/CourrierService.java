package fr.efrei.residence;

public class CourrierService {

    public static final double PRIX_TIMBRE = 1.29;
    private final NotificationJournal journal;

    public CourrierService(NotificationJournal journal) {
        this.journal = journal;
    }

    public void send(String nom, String adresse, String objet, String texte) {
        journal.addLetter(adresse, objet, texte);
        System.out.println("[COURRIER] " + nom + ", " + adresse + " | Objet : " + objet);
        System.out.println("        " + texte);
    }
}
