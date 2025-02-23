package it.unipv.ingsfw.bitebyte.service;

import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.dao.ProdottoDAO;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public class ProdottoService {
	
	private ProdottoDAO prodottoDAO;
	public ProdottoService() {
		this.prodottoDAO = new ProdottoDAO();
	}
	
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) {
    	return prodottoDAO.getProdottiByCategoria(stock, categoria);
    }

}
