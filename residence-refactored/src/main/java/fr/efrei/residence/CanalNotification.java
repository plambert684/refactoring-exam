package fr.efrei.residence;

/** Un moyen de joindre un étudiant. */
public interface CanalNotification {

    void envoyer(Etudiant e, String sujet, String message);

    void relancer(Etudiant e, Bail b);
}
