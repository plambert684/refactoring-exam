package fr.efrei.residence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BailRepositoryMemoire implements BailRepository {

    private final Map<String, Bail> baux = new HashMap<>();

    @Override
    public Bail trouver(String id) {
        return baux.get(id);
    }

    @Override
    public void sauvegarder(Bail bail) {
        baux.put(bail.getId(), bail);
    }

    @Override
    public void mettreAJour(Bail bail) {
        baux.put(bail.getId(), bail);
    }

    @Override
    public List<Bail> tous() {
        List<Bail> resultat = new ArrayList<>(baux.values());
        resultat.sort((a, b) -> a.getId().compareTo(b.getId()));
        return resultat;
    }
}