package fr.efrei.residence;

import java.util.HashMap;
import java.util.Map;

public class EtudiantRepositoryMemoire implements EtudiantRepository {

    private final Map<String, Etudiant> etudiants = new HashMap<>();

    @Override
    public Etudiant trouverParEmail(String email) {
        return etudiants.get(email);
    }

    @Override
    public Etudiant trouver(String id) {
        return etudiants.values().stream()
                .filter(etudiant -> etudiant.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void sauvegarder(Etudiant etudiant) {
        etudiants.put(etudiant.getEmail(), etudiant);
    }
}