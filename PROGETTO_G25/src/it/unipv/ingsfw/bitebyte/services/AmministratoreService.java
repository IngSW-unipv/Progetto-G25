package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.AmministratoreDAO;
import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;

public class AmministratoreService {
	
	private AmministratoreDAO amministratoreDAO;
	
	
	
    public AmministratoreService() {   
        this.amministratoreDAO = new AmministratoreDAO();
    }
	
    public void loginAmministratore (String password) {
        // Crea un'istanza del DAO
        //AmministratoreService amministratoreService = new AmministratoreService(amministratoreDAO);
        boolean esisteA = esisteAmministratore(password);
        if (esisteA) {
        	System.out.println("Prova Amministratore");
        	
        }
    	
    }
    
    private boolean esisteAmministratore(String pass) {
    	return amministratoreDAO.esisteAmministratore(pass); 	
    }
}