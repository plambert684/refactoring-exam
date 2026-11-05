package fr.efrei.residence;

import java.util.List;

public class T2 extends Logement {

    private double surfaceBalcon;

    public T2(String numero, double surface, int etage, boolean meuble, double surfaceBalcon) {
        super(numero, TypeLogement.T2, surface, etage, meuble);
        this.surfaceBalcon = surfaceBalcon;
    }

 @Override
    public double getSurfaceBalcon() {
        return surfaceBalcon;
    }
}