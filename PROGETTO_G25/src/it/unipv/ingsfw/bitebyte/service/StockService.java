package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.List;

public class StockService {
    private StockDAO stockDAO = new StockDAO();

    public List<Stock> getStockByInventario(int idInventario) {
        return stockDAO.getStockByInventario(idInventario);
    }
    
    public List<Stock> getStocksByInventario(int idInventario) {
        return stockDAO.getStockByInventario(idInventario);
    }
    
    public void aggiornaQuantita(Stock stock) {
        stockDAO.updateStock(stock); // Chiama il DAO per aggiornare il DB
    }
}
