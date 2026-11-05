package fr.efrei.residence;

public class ServiceLogement {

    private final Residence residence;

    public ServiceLogement(Residence residence) {
        this.residence = residence;
    }

    public Logement add(String code, String numero, double surface, int etage,
                        boolean meuble, int param) {
        if (existeDeja(numero)) return null;
        TypeLogement type = TypeLogement.depuisCode(code);
        if (type == null) {
            System.out.println("Type de logement inconnu : " + code);
            return null;
        }
        return creer(type, numero, surface, etage, meuble, param);
    }

    public Logement add(TypeLogement type, String numero, double surface, int etage,
                        boolean meuble, int param) {
        if (existeDeja(numero)) return null;
        if (type == null) {
            System.out.println("Type de logement inconnu : null");
            return null;
        }
        return creer(type, numero, surface, etage, meuble, param);
    }

    private boolean existeDeja(String numero) {
        if (residence.find(numero) == null) return false;
        System.out.println("Logement " + numero + " existe déjà");
        return true;
    }

    private Logement creer(TypeLogement type, String numero, double surface, int etage,
                           boolean meuble, int param) {
        Logement logement = LogementFactory.creer(type, numero, surface, etage, meuble,
                type.regle().ajusterParametre(param));
        if (logement == null) {
            System.out.println("Type de logement inconnu : " + type);
            return null;
        }
        residence.add(logement);
        System.out.println("Logement ajouté : " + numero + " (" + type + ")");
        return logement;
    }
}
