package fr.efrei.residence;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ServiceRapport {

    private static final Map<TypeLogement, String> LABELS = Map.of(
            TypeLogement.STUDIO, "Studios      : ",
            TypeLogement.T1, "T1           : ",
            TypeLogement.T2, "T2           : ",
            TypeLogement.COLOCATION, "Colocation   : ");
    private static final Map<TypeLogement, String> SUFFIXES = Map.of(
            TypeLogement.STUDIO, "", TypeLogement.T1, "", TypeLogement.T2, "",
            TypeLogement.COLOCATION, " places");

    private final Residence residence;
    private final BailRepository bailRepository;
    private final NotificationJournal journal;

    public ServiceRapport(Residence residence, BailRepository bailRepository,
                          NotificationJournal journal) {
        this.residence = residence;
        this.bailRepository = bailRepository;
        this.journal = journal;
    }

    public String generate() {
        Map<TypeLogement, Integer> places = new EnumMap<>(TypeLogement.class);
        Map<TypeLogement, Integer> occupied = new EnumMap<>(TypeLogement.class);
        double balconies = 0;
        for (Logement logement : residence.all()) {
            places.merge(logement.getType(), logement.getNombrePlaces(), Integer::sum);
            occupied.merge(logement.getType(),
                    logement.getType().regle().placesOccupees(logement), Integer::sum);
            try {
                balconies += logement.getSurfaceBalcon();
            } catch (UnsupportedOperationException ignored) {
            }
        }
        StringBuilder report = new StringBuilder();
        report.append("=============== RAPPORT ").append(Residence.NOM).append(" ===============\n");
        int total = 0;
        int occupiedTotal = 0;
        for (TypeLogement type : TypeLogement.values()) {
            int capacity = places.getOrDefault(type, 0);
            int count = occupied.getOrDefault(type, 0);
            report.append(LABELS.get(type)).append(count).append("/").append(capacity)
                    .append(SUFFIXES.get(type)).append("\n");
            total += capacity;
            occupiedTotal += count;
        }
        report.append("Occupation   : ").append(Math.round(occupiedTotal * 100.0 / total)).append(" %\n");
        report.append("Balcons      : ").append(balconies).append(" m²\n");
        double rents = 0;
        double deposits = 0;
        double unpaid = 0;
        List<Bail> baux = bailRepository.tous();
        for (Bail bail : baux) {
            if (bail.getStatut() == StatutBail.ACTIF || bail.getStatut() == StatutBail.PREAVIS) {
                rents += bail.getLoyerChargesComprises();
                deposits += bail.getCaution();
            }
            if (bail.soldeDu > 0) unpaid += bail.soldeDu;
        }
        report.append("Loyers CC    : ").append(Utils.euros(Utils.arrondir(rents))).append(" / mois\n");
        report.append("Cautions     : ").append(Utils.euros(Utils.arrondir(deposits))).append("\n");
        report.append("Impayés      : ").append(Utils.euros(Utils.arrondir(unpaid))).append("\n");
        report.append("SMS envoyés  : ").append(journal.smsCount()).append("\n");
        report.append("Timbres      : ").append(Utils.euros(Utils.arrondir(journal.postageCost()))).append("\n");
        report.append("============================================================");
        return report.toString();
    }
}
