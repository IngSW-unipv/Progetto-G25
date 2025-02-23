package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.List;

public class StockService {
    
    private StockDAO stockDAO = new StockDAO();
    
    public List<Stock> getStockByInventario(int inventarioId) {
        return stockDAO.getStockByInventario(inventarioId);
    }
    
    public void addStock(Stock stock) {
        stockDAO.addStock(stock);
    }
    
    public void updateStock(Stock stock) {
        stockDAO.updateStock(stock);
    }
    
    public void deleteStock(int inventarioId) {
        stockDAO.deleteStock(inventarioId);
    }
}
