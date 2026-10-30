package fr.efrei.residence;

public enum TypeLogement {
    STUDIO(new RegleStudio()),
    T1(new RegleT1()),
    T2(new RegleT2()),
    COLOCATION(new RegleColocation());

    private final RegleTypeLogement regle;

    TypeLogement(RegleTypeLogement regle) {
        this.regle = regle;
    }

    public RegleTypeLogement regle() {
        return regle;
    }

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
