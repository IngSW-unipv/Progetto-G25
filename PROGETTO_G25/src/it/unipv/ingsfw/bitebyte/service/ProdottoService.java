package it.unipv.ingsfw.bitebyte.service;

import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.dao.ProdottoDAO;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;

/**
 * Classe di servizio per la gestione dei prodotti nel caso di
 * interazioni col database.
 */
public class ProdottoService {
	
	private ProdottoDAO prodottoDAO;

    /**
     * Costruttore della classe ProdottoService.
     * Inizializza il DAO dei prodotti che realizza le operazioni sul DB.
     */
	public ProdottoService() {
		this.prodottoDAO = new ProdottoDAO();
	}
	
    /**
     * Ottiene una lista di prodotti appartenenti a una specifica categoria e relativi a uno stock.
     * 
     * @param stock Lo stock di riferimento.
     * @param categoria La categoria dei prodotti da filtrare.
     * @return Una lista di prodotti appartenenti alla categoria specificata.
     */
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) {
    	return prodottoDAO.getProdottiByCategoria(stock, categoria);
    }
}