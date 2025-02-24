package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.dao.SpedizioneDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class SpedizioneService {
	
	private SpedizioneDAO spedizioneDAO;
	
	public SpedizioneService() {
		this.spedizioneDAO = new SpedizioneDAO();
	}
    
	public ArrayList<Spedizione> getAllSpedizioni() {
		return spedizioneDAO.getAllSpedizioni();
	}
	// Metodo per salvare la spedizione
    public void salvaSpedizione(Map<Integer, Integer> quantitaTotalePerProdotto, Map<Integer, BigDecimal> prezzoTotalePerProdotto) {
    	String idSpedizione = generaIdSpedizione();
    	for (Integer idProdotto : quantitaTotalePerProdotto.keySet()) {
            int quantita = quantitaTotalePerProdotto.get(idProdotto);
            BigDecimal prezzoTotale = prezzoTotalePerProdotto.get(idProdotto);
            salvaSpedizioneSuDB(idSpedizione, idProdotto, quantita, prezzoTotale);
        }
    }
    
	private void salvaSpedizioneSuDB(String idSpedizione, int idProdotto, int quantitatotperprod, BigDecimal prezzototperprod) {
		spedizioneDAO.salvaSpedizione(idSpedizione, idProdotto, quantitatotperprod, prezzototperprod);
	}
	
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
