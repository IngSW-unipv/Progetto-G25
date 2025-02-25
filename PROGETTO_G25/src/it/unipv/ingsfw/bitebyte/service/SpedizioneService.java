package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.dao.SpedizioneDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Servizio che gestisce le operazioni relative alle spedizioni, come il recupero delle spedizioni
 * e il salvataggio di nuove spedizioni nel database.
 * Utilizza il {@link SpedizioneDAO} per interagire con il database delle spedizioni.
 */
public class SpedizioneService {
    
    private SpedizioneDAO spedizioneDAO;

    /**
     * Costruttore del servizio SpedizioneService. Inizializza l'oggetto {@link SpedizioneDAO} per
     * interagire con il database.
     */
    public SpedizioneService() {
        this.spedizioneDAO = new SpedizioneDAO();
    }
    
    /**
     * Recupera tutte le spedizioni dal database.
     * 
     * @return Una lista di oggetti {@link Spedizione} contenente tutte le spedizioni presenti nel database.
     */
    public ArrayList<Spedizione> getAllSpedizioni() {
        return spedizioneDAO.getAllSpedizioni();
    }

    /**
     * Salva una nuova spedizione nel database, utilizzando i dati passati per ciascun prodotto.
     * 
     * @param quantitaTotalePerProdotto Una mappa che associa l'ID del prodotto alla sua quantità totale.
     * @param prezzoTotalePerProdotto Una mappa che associa l'ID del prodotto al suo prezzo totale.
     */
    public void salvaSpedizione(Map<Integer, Integer> quantitaTotalePerProdotto, Map<Integer, BigDecimal> prezzoTotalePerProdotto) {
        String idSpedizione = generaIdSpedizione();
        for (Integer idProdotto : quantitaTotalePerProdotto.keySet()) {
            int quantita = quantitaTotalePerProdotto.get(idProdotto);
            BigDecimal prezzoTotale = prezzoTotalePerProdotto.get(idProdotto);
            salvaSpedizioneSuDB(idSpedizione, idProdotto, quantita, prezzoTotale);
        }
    }

    /**
     * Salva i dati di una spedizione nel database.
     * 
     * @param idSpedizione L'ID della spedizione.
     * @param idProdotto L'ID del prodotto.
     * @param quantitatotperprod La quantità del prodotto.
     * @param prezzototperprod Il prezzo totale del prodotto.
     */
    private void salvaSpedizioneSuDB(String idSpedizione, int idProdotto, int quantitatotperprod, BigDecimal prezzototperprod) {
        spedizioneDAO.salvaSpedizione(idSpedizione, idProdotto, quantitatotperprod, prezzototperprod);
    }

    /**
     * Genera un ID univoco per una spedizione. L'ID è una stringa di 5 caratteri
     * casuali, composta da lettere maiuscole e numeri.
     * 
     * @return Una stringa che rappresenta un ID di spedizione univoco.
     */
    public String generaIdSpedizione() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
