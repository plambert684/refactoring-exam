package fr.efrei.residence;

public class GenerateurIdentifiantEtudiant {

    private int nextNumber = 1;

    public String nextId() {
        return "ETU-" + nextNumber++;
    }
}
