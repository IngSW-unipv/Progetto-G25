package it.unipv.ingsfw.bitebyte.test;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;

import java.util.List;

public class TestDistributoreDAO {

    public static void main(String[] args) {
DistributoreDAO distributoreDAO = new DistributoreDAO();
        
        // ID del distributore da testare (assicurati che esista nel database)
        int idTest = 1;

        // Recupera il distributore dal database
        Distributore distributore = distributoreDAO.getDistributoreById(idTest);

        if (distributore != null) {
            System.out.println("Distributore recuperato: " + distributore);
            System.out.println("Stock associati:");
            
            List<Stock> stockList = distributore.getStockList();
            for (Stock stock : stockList) {
                System.out.println(stock);
            }
        } else {
            System.out.println("Nessun distributore trovato con ID: " + idTest);
        }
    }

}

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	/*
    	
    	
    	
        // Crea un'istanza di DistributoreDAO per i test
        DistributoreDAO distributoreDAO = new DistributoreDAO();

        // 1. Recupera un distributore esistente (assumendo che ci sia già un distributore con ID 1)
        Distributore distributore = distributoreDAO.getDistributoreById(1);
        
        // Verifica che il distributore sia stato recuperato correttamente
        if (distributore != null) {
            System.out.println("Distributore recuperato: " + distributore);
            
            // 2. Recupera gli stock associati al distributore
            List<Stock> stockList = distributoreDAO.getStockByDistributore(1);
            System.out.println("Stock del distributore:");
            for (Stock stock : stockList) {
                System.out.println(stock);
            }
        } else {
            System.out.println("Distributore non trovato con ID 1.");
        }
    }
}

*/
