package fr.efrei.residence;

public enum Profil {
    STANDARD(new RegleStandard()),
    BOURSIER(new RegleBoursier()),
    ALTERNANT(new RegleAlternant()),
    INTERNATIONAL(new RegleInternational());

    private final RegleProfil regle;

    Profil(RegleProfil regle) {
        this.regle = regle;
    }

    public RegleProfil regle() {
        return regle;
    }
}
