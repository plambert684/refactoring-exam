package fr.efrei.residence;

import java.util.List;

public interface BailRepository {

    Bail trouver(String id);

    void sauvegarder(Bail bail);

    void mettreAJour(Bail bail);

    List<Bail> tous();
}