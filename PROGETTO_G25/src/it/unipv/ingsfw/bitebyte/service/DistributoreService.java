/**
 * Servizio per la gestione dei distributori.
 * Fornisce metodi per ottenere informazioni sui distributori.
 */
package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;

import java.util.List;

public class DistributoreService {
	
	private DistributoreDAO distributoreDAO;
	
	/**
	 * Costruttore della classe. 
	 * Inizializza un'istanza di {@link DistributoreDAO} per l'interazione con il database.
	 */
	public DistributoreService() {
		this.distributoreDAO  = new DistributoreDAO();
	}
	
	/**
	 * Recupera la lista di tutti i distributori disponibili nel database.
	 * 
	 * @return una lista di {@link Distributore}
	 */
	public List<Distributore> getAllDistributori() {
		return distributoreDAO.getAllDistributori();
	}
}
