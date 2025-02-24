
/**
 * Classe ProdottoController gestisce la logica dell'interfaccia grafica
 * per il collegamento a un distributore nella piattaforma BiteByte.
 * Consente all'utente di inserire un codice di distributore, collegarsi
 * a esso e visualizzare la schermata dei prodotti disponibili.
 * 
 * @author Anna
 */

package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.service.DistributoreService;
import it.unipv.ingsfw.bitebyte.view.ViewManager;


// Importo le classi di JavaFX necessarie per gestire l'interfaccia grafica e mostrare finestre di dialogo.
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

public class ProdottoController {

    @FXML
    private TextField codiceDistributoreField;
    @FXML
    private Button collegatiButton;
    
    private DistributoreService distributoreService;
    
    /**
    * Costruttore della classe ProdottoController.
    * Inizializza un'istanza di DistributoreService per gestire le operazioni sui distributori.
    */

    public ProdottoController() {
        distributoreService = new DistributoreService(); // Inizializzazione del servizio
    }

    /**
     * Metodo initialize() eseguito automaticamente quando l'interfaccia viene caricata.
     * Utilizza Platform.runLater() per spostare il focus sul pulsante "Collegati"
     * e garantire che il focus iniziale non sia sul campo di testo.
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> collegatiButton.requestFocus());
    }
    
    
    /**
     * Metodo onCollegatiClicked() viene eseguito quando l'utente clicca sul pulsante "Collegati".
     * Legge il codice del distributore inserito e tenta di recuperare il distributore corrispondente.
     * Se il codice è vuoto o non valido, mostra un messaggio di errore.
     * Se il distributore viene trovato, apre la schermata dei prodotti disponibili.
     */
    
    @FXML
    public void onCollegatiClicked() {
        String codiceDistributore = codiceDistributoreField.getText();

        if (codiceDistributore.trim().isEmpty()) {     //trim per rimuovere gli spazi
            showError("Errore", "Inserisci un codice distributore.");
            return;
        }
        
       // Converte il codice del distributore in un numero intero e utilizza il servizio per recuperare il distributore corrispondente.
        try {
            int idDistributore = Integer.parseInt(codiceDistributore);
            Distributore distributore = distributoreService.getDistributoreById(idDistributore); // Usa il servizio

            if (distributore != null) {
                showInfo("Collegamento riuscito", "Sei connesso al distributore "  
                         + distributore.getIdDistr() + ": " + distributore.getTipo());

                // Utilizza ViewManager per aprire la schermata ProdottiCliente
                ProdottiClienteController prodController = ViewManager.getInstance()
                        .showStageWithController("/prodottiCliente.fxml", 800, 600, "BiteByte - Prodotti Cliente");
                
                // Passa i dati necessari al nuovo controller
              //  prodController.setIdInventario(distributore.getIdInventario());
                prodController.setDistributoreCorrente(distributore);
                
                // Chiudi la finestra corrente
                Stage currentStage = (Stage) collegatiButton.getScene().getWindow();
                currentStage.close();
            } else {
                showError("Errore", "Distributore non trovato.");
            }
        } catch (NumberFormatException e) {
            showError("Errore", "Il codice del distributore deve essere un numero intero.");
        }
    }

    /**
     * Metodo privato per mostrare un messaggio di errore all'utente.
     * 
     * @param title   Il titolo del messaggio di errore.
     * @param message Il contenuto del messaggio di errore.
     */
    
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Metodo privato per mostrare un messaggio informativo all'utente.
     * 
     * @param title   Il titolo del messaggio informativo.
     * @param message Il contenuto del messaggio informativo.
     */

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

	
	
	
	
	
	
	
	

