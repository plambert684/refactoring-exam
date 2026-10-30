package fr.efrei.residence;

/** Seul endroit qui sait construire chaque type de logement. */
public class LogementFactory {

    /** Crée le logement du type demandé, ou renvoie null si le type est absent. */
    public static Logement creer(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case STUDIO:
                return new Studio(numero, surface, etage, meuble);
            case T1:
                return new T1(numero, surface, etage, meuble);
            case T2:
                return new T2(numero, surface, etage, meuble, param);
            case COLOCATION:
                return new Colocation(numero, surface, etage, meuble, param);
            default:
                return null;
        }
    }
}
