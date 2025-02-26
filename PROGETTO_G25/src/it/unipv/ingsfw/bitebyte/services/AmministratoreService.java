package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.controller.GestionePController;
import it.unipv.ingsfw.bitebyte.dao.AmministratoreDAO;
import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        	GestionePController gestionePController = new GestionePController();
        	Scene scene = new Scene(gestionePController.getView().getView(), 800, 600);
        	Stage stage = new Stage();
        	stage.setTitle("Gestione prodotti");
        	stage.setScene(scene);
        	stage.show();
        	
        }
    	
    }
    
    private boolean esisteAmministratore(String pass) {
    	return amministratoreDAO.esisteAmministratore(pass); 	
    }
}