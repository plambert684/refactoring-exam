package fr.efrei.residence;

public interface EtudiantRepository {

    Etudiant trouverParEmail(String email);

    Etudiant trouver(String id);

    void sauvegarder(Etudiant etudiant);

}
