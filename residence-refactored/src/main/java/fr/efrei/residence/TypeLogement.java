package fr.efrei.residence;

public enum TypeLogement {
    STUDIO, T1, T2, COLOCATION;

    /** Type correspondant au code texte, ou null si le code est inconnu. */
    public static TypeLogement depuisCode(String code) {
        for (TypeLogement t : values()) {
            if (t.name().equals(code)) {
                return t;
            }
        }
        return null;
    }
}
