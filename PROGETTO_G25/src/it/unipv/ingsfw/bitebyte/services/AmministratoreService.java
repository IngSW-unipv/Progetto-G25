package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.AmministratoreDAO;
import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;

public class AmministratoreService {
	
	private final AmministratoreDAO amministratoreDAO;
	
	
	
    public AmministratoreService(AmministratoreDAO amministratoreDAO) {   
        this.amministratoreDAO = amministratoreDAO;
    }
	
    public void loginAmministratore (String password) {
        AmministratoreDAO amministratoreDAO = new AmministratoreDAO(); // Crea un'istanza del DAO
        AmministratoreService amministratoreService = new AmministratoreService(amministratoreDAO);
        boolean esisteA = amministratoreService.esisteAmministratore(password);
        if (esisteA) {
        	System.out.println("Prova Amministratore");
        	
        }
    	
    }
    
    private boolean esisteAmministratore(String pass) {
    	return amministratoreDAO.esisteAmministratore(pass); 	
    }
}
