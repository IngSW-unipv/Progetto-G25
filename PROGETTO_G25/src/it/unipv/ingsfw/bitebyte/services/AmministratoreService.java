package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.controller.GestionePController;
import it.unipv.ingsfw.bitebyte.dao.AmministratoreDAO;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Servizio per la gestione delle operazioni relative all'amministratore.
 * Fornisce metodi per il login e la verifica dell'esistenza di un amministratore nel sistema.
 */
public class AmministratoreService {

    private AmministratoreDAO amministratoreDAO;

    /**
     * Costruttore di default.
     * Inizializza il DAO per la gestione degli amministratori.
     */
    public AmministratoreService() {   
        this.amministratoreDAO = new AmministratoreDAO();
    }

    /**
     * Gestisce il login dell'amministratore.
     * Se le credenziali sono corrette, apre l'interfaccia di gestione dei prodotti.
     *
     * @param password La password dell'amministratore.
     */
    public void loginAmministratore(String password) {
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

    /**
     * Verifica se esiste un amministratore con la password specificata.
     *
     * @param pass La password dell'amministratore da verificare.
     * @return true se l'amministratore esiste, altrimenti false.
     */
    private boolean esisteAmministratore(String pass) {
        return amministratoreDAO.esisteAmministratore(pass);     
    }
}
