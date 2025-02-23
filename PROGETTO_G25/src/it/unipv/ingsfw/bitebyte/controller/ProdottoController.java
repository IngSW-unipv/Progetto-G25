package it.unipv.ingsfw.bitebyte.controller;

import java.io.IOException;
import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProdottoController {

    @FXML
    private TextField codiceDistributoreField;
    @FXML
    private Button collegatiButton;

    private DistributoreDAO distributoreDAO;

    public ProdottoController() {
        distributoreDAO = new DistributoreDAO();
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
            Distributore distributore = distributoreDAO.getDistributoreById(idDistributore);

            if (distributore != null) {
                showInfo("Collegamento riuscito", "Sei connesso al distributore "  
                         + distributore.getIdDistr() + ": " + distributore.getTipo());

                // Utilizza ViewManager per aprire la schermata ProdottiCliente
                ProdottiClienteController prodController = ViewManager.getInstance()
                        .showStageWithController("/prodottiCliente.fxml", 800, 600, "BiteByte - Prodotti Cliente");
                
                // Passa i dati necessari al nuovo controller
                prodController.setIdInventario(distributore.getIdInventario());
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

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

	
	
	
	
	
	
	
	
	

