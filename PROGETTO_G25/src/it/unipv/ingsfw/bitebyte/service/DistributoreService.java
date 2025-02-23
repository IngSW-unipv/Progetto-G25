package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.List;

public class DistributoreService {
    
    private DistributoreDAO distributoreDAO = new DistributoreDAO();
    
    public Distributore getDistributoreById(int idDistributore) {
        return distributoreDAO.getDistributoreById(idDistributore);
    }
    
    public List<Distributore> getAllDistributori() {
        return distributoreDAO.getAllDistributori();
    }
    
    public List<Distributore> getDistributoriConProdottoDisponibileByName(int idDistributore, String nomeProdotto) {
        return distributoreDAO.getDistributoriConProdottoDisponibileByName(idDistributore, nomeProdotto);
    }
    
    public void addDistributore(Distributore distributore) {
        distributoreDAO.addDistributore(distributore);
    }
    
    public void updateDistributore(Distributore distributore) {
        distributoreDAO.updateDistributore(distributore);
    }
    
    public void deleteDistributore(int idDistributore) {
        distributoreDAO.deleteDistributore(idDistributore);
    }
    
    public List<Stock> getStockByDistributore(int idDistributore) {
        return distributoreDAO.getStockByDistributore(idDistributore);
    }
}
