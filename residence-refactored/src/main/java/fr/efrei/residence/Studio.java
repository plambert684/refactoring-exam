package fr.efrei.residence;

import java.util.List;

public class Studio extends Logement {

    public Studio(String numero, double surface, int etage, boolean meuble) {
        super(numero, TypeLogement.STUDIO, surface, etage, meuble);
    }
}