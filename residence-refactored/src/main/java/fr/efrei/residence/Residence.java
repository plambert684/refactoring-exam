package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class Residence {

    public static final String NOM = "Résidence Campus Vert";

    public static final String[] CATALOGUE = {
            "STUDIO;A001;26;0;true;0",
            "STUDIO;A102;22;1;true;0",
            "STUDIO;B103;20;1;false;0",
            "T1;A104;28;1;true;0",
            "T1;B105;30;1;false;0",
            "T2;A201;45;2;false;6",
            "T2;B202;48;2;true;0",
            "COLOCATION;B301;80;3;true;4",
            "COLOCATION;C001;63;0;true;3",
    };

    private final List<Logement> logements = new ArrayList<>();

    public Residence() {
        initialize();
    }

    public void initialize() {
        logements.clear();
        for (String ligne : CATALOGUE) {
            String[] t = ligne.split(";");
            Logement logement = LogementFactory.creer(TypeLogement.depuisCode(t[0]), t[1],
                    Double.parseDouble(t[2]), Integer.parseInt(t[3]),
                    Boolean.parseBoolean(t[4]), Integer.parseInt(t[5]));
            if (logement != null) logements.add(logement);
        }
    }

    public Logement find(String numero) {
        return logements.stream()
                .filter(logement -> logement.getNumero().equals(numero))
                .findFirst()
                .orElse(null);
    }

    public void add(Logement logement) {
        logements.add(logement);
    }

    public List<Logement> all() {
        return List.copyOf(logements);
    }
}
