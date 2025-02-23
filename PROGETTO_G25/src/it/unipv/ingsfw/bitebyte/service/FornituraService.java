package it.unipv.ingsfw.bitebyte.service;

import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.dao.FornituraDAO;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class FornituraService {
	
	private FornituraDAO fornituraDAO;
	
	public FornituraService() {
		this.fornituraDAO = new FornituraDAO();
	}
    
    ArrayList<Fornitura> getFornitoriInfo(Stock stock) {
    	return fornituraDAO.getFornitoriInfo(stock);
    }
}
