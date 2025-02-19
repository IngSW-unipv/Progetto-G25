package it.unipv.ingsfw.bitebyte.test;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;

import java.util.List;

public class TestDistributoriVicini {

    public static void main(String[] args) {
        // Crea un'istanza del DAO
        DistributoreDAO dao = new DistributoreDAO();

        // Definisci l'ID del distributore corrente e il raggio massimo (in km)
        int currentDistributoreId = 1; // Sostituisci con un ID esistente nel tuo database
        double maxDistanzaKm = 5.0;    // Raggio di ricerca in chilometri

        System.out.println("Test per getDistributoriVicini per il distributore con ID " + currentDistributoreId);
        System.out.println("Raggio di ricerca: " + maxDistanzaKm + " km");

        // Ottieni il distributore corrente
        Distributore distributoreCorrente = dao.getDistributoreById(currentDistributoreId);
        if (distributoreCorrente == null) {
            System.out.println("Distributore con ID " + currentDistributoreId + " non trovato.");
            return;
        }

        // Recupera la lista di distributori vicini entro il raggio specificato
        List<Distributore> vicini = dao.getDistributoriVicini(currentDistributoreId, maxDistanzaKm);

        // Stampa i risultati
        if (vicini.isEmpty()) {
            System.out.println("Nessun distributore trovato entro " + maxDistanzaKm + " km.");
        } else {
            System.out.println("Distributori trovati:");
            for (Distributore d : vicini) {
                double distanza = CalcolaDistanza.calcolaDistanza(distributoreCorrente, d);
                System.out.printf("ID: %d, Tipo: %s, Indirizzo: %s, Distanza: %.2f km%n",
                        d.getIdDistr(), d.getTipo(), d.getVia() + " " + d.getNCivico(), distanza);
            }
        }
    }
}
