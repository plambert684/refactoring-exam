package fr.efrei.residence;

import java.util.Optional;

public class ServiceInscription {

    private final EtudiantRepository etudiantRepository;
    private final GenerateurIdentifiantEtudiant identifiants;

    public ServiceInscription(EtudiantRepository etudiantRepository,
                              GenerateurIdentifiantEtudiant identifiants) {
        this.etudiantRepository = etudiantRepository;
        this.identifiants = identifiants;
    }

    public Etudiant register(String nom, String prenom, String email, String telephone, String adresse,
                             Profil profil, int echelonBourse, double ressources, boolean pmr,
                             Canal canalPrefere) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("Inscription refusée : nom obligatoire");
            return null;
        }
        if (!Utils.emailValide(email)) {
            System.out.println("Inscription refusée : email invalide (" + email + ")");
            return null;
        }
        if (etudiantRepository.trouverParEmail(email) != null) {
            System.out.println("Inscription refusée : " + email + " déjà inscrit");
            return null;
        }
        if (profil == null) {
            System.out.println("Inscription refusée : profil inconnu " + profil);
            return null;
        }
        Optional<String> motifRefus = profil.regle().motifRefusInscription(echelonBourse, telephone);
        if (motifRefus.isPresent()) {
            System.out.println("Inscription refusée : " + motifRefus.get());
            return null;
        }
        if (canalPrefere == Canal.SMS && telephone == null) {
            System.out.println("Inscription refusée : téléphone obligatoire pour les SMS");
            return null;
        }
        if (canalPrefere == Canal.COURRIER && (adresse == null || adresse.isEmpty())) {
            System.out.println("Inscription refusée : adresse obligatoire pour le courrier");
            return null;
        }

        Etudiant etudiant = new Etudiant(identifiants.nextId(), nom, prenom, email, telephone, adresse, profil,
                echelonBourse, ressources, pmr, canalPrefere);
        etudiantRepository.sauvegarder(etudiant);
        System.out.println("Étudiant inscrit : " + etudiant.getId() + " "
                + etudiant.getNomComplet() + " (" + profil + ")");
        return etudiant;
    }
}
