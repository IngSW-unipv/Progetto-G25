package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.ArrayList;


public class StockService {
    
    private StockDAO stockDAO = new StockDAO();
    
    public ArrayList<Stock> getStockByInventario(int idInventario) {
        return stockDAO.getStockByInventario(idInventario);
    }
    
    public void addStock(Stock stock) {
        stockDAO.addStock(stock);
    }
    
    public void updateStock(Stock stock) {
        stockDAO.updateStock(stock);
    }
    
    public void deleteStock(int idInventario) {
        stockDAO.deleteStock(idInventario);
    }
}
