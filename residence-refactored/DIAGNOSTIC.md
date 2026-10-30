# Diagnostic du code historique

| Défaut | Emplacement | Principe violé |
|---|---|---|
| Une seule classe gère inscription, parc, attribution, cycle de vie du bail, relances, notifications et rapport | `GestionResidence` | S (responsabilité unique) |
| Le bail met en forme sa propre quittance | `Bail.genererQuittance` | S |
| Le modèle affiche sur la console (nettoyage, messages) | `Colocation.nettoyerPartiesCommunes`, `GestionResidence` | S |
| La persistance mélange stockage et génération de SQL | `Database` | S |
| Ajouter un type de logement oblige à modifier plusieurs `switch`/`if` | `Residence.initialiser`, `GestionResidence.ajouterLogement`, `CalculateurLoyer.calculerLoyer`, `GestionResidence.genererRapport` | O (ouvert/fermé) |
| Ajouter un profil oblige à modifier tous les `if` sur le profil (remise, solvabilité, dépôt, frais, préavis, pénalité) | `CalculateurLoyer`, `GestionResidence.attribuerLogement`, `payerLoyer`, `donnerPreavis` | O |
| Ajouter une option oblige à modifier la signature et le calcul (5 booléens) | `CalculateurLoyer.calculerLoyer`, `Bail`, `GestionResidence.attribuerLogement` | O |
| Ajouter un canal oblige à modifier le choix du service à deux endroits | `GestionResidence.notifier`, `GestionResidence.relancerImpayes` | O |
| Une colocation refuse `loger()` / `liberer()` : elle ne peut pas remplacer un `Logement` | `Colocation` | L (substitution de Liskov) |
| Le code client doit tester le type réel (`instanceof Colocation`) | `GestionResidence`, adaptateur | L |
| Pour une même méthode, `T1` renvoie `null` ou `0` là où `Studio` lève une exception | `T1.getColocataires`, `T1.getSurfaceBalcon` | L |
| L'interface impose colocataires, balcon et parties communes à tous les logements | `ILogement` | I (ségrégation des interfaces) |
| Méthodes inutiles implémentées par des `UnsupportedOperationException` | `Studio`, `T2`, `Colocation.getSurfaceBalcon` | I |
| `catch` vide pour masquer ces exceptions dans le rapport | `GestionResidence.genererRapport` | I |
| Singleton global pour la persistance | `Database.getInstance` | D (inversion des dépendances) |
| État global `static` modifiable | `Residence.logements`, `EmailService.journal`, `SmsService.journal`, `CourrierService.journal`, `SmsService.compteurSms`, `CourrierService.totalAffranchissement`, `Etudiant.compteur`, `GestionResidence.compteurBail` | D |
| Les services sont créés en dur (`new`) dans le code métier au lieu d'être injectés | `GestionResidence` (services de notification, calculateur) | D |
