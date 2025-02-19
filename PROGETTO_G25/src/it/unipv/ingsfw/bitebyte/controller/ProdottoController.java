package it.unipv.ingsfw.bitebyte.controller;

import java.io.IOException;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProdottoController {

    @FXML
    private TextField codiceDistributoreField; // Campo dove l'utente inserisce il codice del distributore

    @FXML
    private Button collegatiButton; // Bottone per connettersi al distributore

    private DistributoreDAO distributoreDAO;

    public ProdottoController() {
        distributoreDAO = new DistributoreDAO(); // Istanza del DAO per accedere ai distributori
    }

    // Metodo per il click sul bottone 'Collegati'
    @FXML
    public void onCollegatiClicked() {
        String codiceDistributore = codiceDistributoreField.getText(); // Prende il valore inserito

        // Verifica se il campo è vuoto
        if (codiceDistributore.trim().isEmpty()) {
            showError("Errore", "Inserisci un codice distributore.");
            return;
        }

        try {
            // Prova a convertire l'input in un numero intero
            int idDistributore = Integer.parseInt(codiceDistributore);
            
            // Cerca il distributore nel database
            Distributore distributore = distributoreDAO.getDistributoreById(idDistributore);

            if (distributore != null) {
                // Se il distributore è trovato, mostriamo un messaggio di successo
                showInfo("Collegamento riuscito", "Sei connesso al distributore "  
                         + distributore.getIdDistr() + ": " + distributore.getTipo());
                
                // Carica il file FXML per la schermata dei prodotti
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/prodottiCliente.fxml"));
                AnchorPane root = loader.load();
                
                // Ottieni il controller della nuova schermata
                ProdottiClienteController prodClientController = loader.getController();
                // Passa i dati necessari (ad es. l'ID inventario)
                prodClientController.setIdInventario(distributore.getIdInventario());
                
                // Crea la nuova scena e una nuova finestra NON è STATA GIà CRESTA DALL'ALTRO CONTROLLERR??
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("BiteByte - Prodotti Cliente");
                stage.setWidth(800);
                stage.setHeight(600);
                stage.show();
                
                // Chiudi la finestra corrente
                ((Stage) collegatiButton.getScene().getWindow()).close();
            } else {
                // Se il distributore non è trovato
                showError("Errore", "Distributore non trovato.");
            }
        } catch (NumberFormatException e) {
            // Se l'input non è un numero intero
            showError("Errore", "Il codice del distributore deve essere un numero intero.");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Errore", "Impossibile caricare la schermata dei prodotti.");
        }
    }


    // Metodo per mostrare messaggi di errore
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metodo per mostrare informazioni
    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
     

}

	
	
	
	
	
	
	
	
	
	
	
	
	/*
    private StockDAO stockDAO;  // Riferimento al DAO

    // Costruttore per inizializzare il DAO
    public ProdottoController(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public void verificaDisponibilita(String idProdotto) {
        // Recupera la lista di Stock per tutti gli inventari
        ArrayList<Stock> stocks = stockDAO.getStockByInventario(1); // Supponiamo che l'inventario sia identificato da 1. Puoi personalizzare questa parte

        Stock stockTrovato = null;

        // Cerca l'oggetto Stock per il prodotto selezionato
        for (Stock stock : stocks) {
            if (stock.getProdotto().getIdProdotto().equals(idProdotto)) {
                stockTrovato = stock;
                break;
            }
        }

        // Se il prodotto è trovato, verifica la disponibilità
        if (stockTrovato != null) {
            if (stockTrovato.getQuantitaDisp() <= 0) {
                mostraMessaggioErrore("Prodotto non disponibile", "Il prodotto selezionato non è disponibile nel distributore.");
            } else {
                System.out.println("Il prodotto è disponibile.");
            }
        } else {
            mostraMessaggioErrore("Prodotto non trovato", "Il prodotto selezionato non esiste nel distributore.");
        }
    }

    private void mostraMessaggioErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}

*/
