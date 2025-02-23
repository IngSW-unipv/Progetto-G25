package it.unipv.ingsfw.bitebyte.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class StockService {
	
	private StockDAO stockDAO;
	
	public StockService() {
		this.stockDAO = new StockDAO();
	}
	
	public ArrayList<Stock> getStockByInventario(int idInventario) {
		return stockDAO.getStockByInventario(idInventario);
	}
	
	public void sostituisciStock(Stock stock, int idProdotto) {
		stockDAO.sostituisciStock(stock, idProdotto);
	}
	
	public boolean updatePrice(int idProdotto, int idInventario, BigDecimal nuovoPrezzo) {
		return stockDAO.updatePrice(idProdotto, idInventario, nuovoPrezzo);
	}
	
	public ArrayList<Stock> getStockByProdotto(int idProdotto) {
		return stockDAO.getStockByProdotto(idProdotto);
	}
	
	public void updateStock(Stock stock) {
		stockDAO.updateStock(stock);
	}
	
}
