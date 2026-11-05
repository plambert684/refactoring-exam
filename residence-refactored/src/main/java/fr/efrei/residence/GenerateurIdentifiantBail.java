package fr.efrei.residence;

public class GenerateurIdentifiantBail {

    private int nextNbr = 1;

    public String prochainId() {
        return String.format("BAIL-%03d", nextNbr++);
    }
}