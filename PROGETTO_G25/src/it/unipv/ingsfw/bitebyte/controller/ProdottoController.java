package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.service.DistributoreService;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProdottoController {

    @FXML
    private TextField codiceDistributoreField;

    @FXML
    private Button collegatiButton;

    private DistributoreService distributoreService;

    public ProdottoController() {
        this.distributoreService = new DistributoreService();
    }

    @FXML
    public void onCollegatiClicked() {
        String codiceDistributore = codiceDistributoreField.getText();

        if (codiceDistributore.trim().isEmpty()) {
            showError("Errore", "Inserisci un codice distributore.");
            return;
        }

        try {
            int idDistributore = Integer.parseInt(codiceDistributore);
            Distributore distributore = distributoreService.getDistributoreById(idDistributore);

            if (distributore != null) {
                showInfo("Collegamento riuscito", 
                    "Sei connesso al distributore " + distributore.getIdDistr() + ": " + distributore.getTipo());

                // Apriamo la nuova finestra
                ViewManager.showProdottiCliente(distributore);

                // Chiudiamo la finestra corrente
                ((Stage) collegatiButton.getScene().getWindow()).close();
            } else {
                showError("Errore", "Distributore non trovato.");
            }
        } catch (NumberFormatException e) {
            showError("Errore", "Il codice del distributore deve essere un numero intero.");
        }
    }

    // Metodo per mostrare messaggi di errore
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metodo per mostrare messaggi informativi
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

	

	
	
	
	
	