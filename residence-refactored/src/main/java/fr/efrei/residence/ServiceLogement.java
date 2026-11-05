package fr.efrei.residence;

public class ServiceLogement {

    private final Residence residence;

    public ServiceLogement(Residence residence) {
        this.residence = residence;
    }

    public Logement add(TypeLogement type, String numero, double surface, int etage,
                        boolean meuble, int param) {
        if (residence.find(numero) != null || type == null) return null;
        Logement logement = LogementFactory.creer(type, numero, surface, etage, meuble,
                type.regle().ajusterParametre(param));
        if (logement == null) return null;
        residence.add(logement);
        return logement;
    }
}
