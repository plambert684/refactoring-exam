package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursLoyerTest {

    private static final Loyer LOYER_100 = () -> 100;

    @Test
    void appliqueLaMajorationMeuble() {
        assertEquals(115, new LoyerMeuble(LOYER_100).montant(), 0.000001);
    }

    @Test
    void appliqueLaReductionRezDeChaussee() {
        assertEquals(95, new LoyerRezDeChaussee(LOYER_100).montant());
    }

    @Test
    void appliqueLaMajorationMenage() {
        assertEquals(108, new LoyerMenage(LOYER_100, 1.08).montant());
    }

    @Test
    void ajouteLeParking() {
        assertEquals(145, new LoyerParking(LOYER_100).montant());
    }

    @Test
    void ajouteLaLaverie() {
        assertEquals(112, new LoyerLaverie(LOYER_100).montant());
    }

    @Test
    void ajouteLaFibre() {
        assertEquals(120, new LoyerFibre(LOYER_100, 20).montant());
    }

    @Test
    void inclutLaFibreEnColocation() {
        assertEquals(100, new LoyerCollocation(LOYER_100).montant());
    }

    @Test
    void ajouteLAssurance() {
        assertEquals(109, new LoyerAssurance(LOYER_100).montant());
    }
}
