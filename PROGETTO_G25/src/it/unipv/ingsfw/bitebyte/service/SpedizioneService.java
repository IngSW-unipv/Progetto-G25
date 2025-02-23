package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.dao.SpedizioneDAO;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SpedizioneService {
	
	private SpedizioneDAO spedizioneDAO;
	
	public SpedizioneService() {
		this.spedizioneDAO = new SpedizioneDAO();
	}
    
	public ArrayList<Spedizione> getAllSpedizioni() {
		return spedizioneDAO.getAllSpedizioni();
	}
	
	public void salvaSpedizione(String idSpedizione, int idProdotto, int quantitatotperprod, BigDecimal prezzototperprod) {
		spedizioneDAO.salvaSpedizione(idSpedizione, idProdotto, quantitatotperprod, prezzototperprod);
	}
}
