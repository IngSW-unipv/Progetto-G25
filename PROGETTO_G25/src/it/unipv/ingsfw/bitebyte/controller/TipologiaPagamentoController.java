package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TipologiaPagamentoController {
	
	// Bottoni TipologiaPagamento
    @FXML
    private Button sceglibancomat;
    @FXML
    private Button sceglipaypal;
    
    
    @FXML
	public void cambiaScena(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		Stage stage = (Stage) clickedButton.getScene().getWindow(); // Ottieni lo Stage dal bottone premuto
		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("sceglibancomat")) {
			System.out.println("sono in switch scene");
			PortafoglioVirtuale nuovoPortafoglio = new PortafoglioVirtuale(Sessione.getInstance().getPortafoglioCliente().generaIdCasuale(), 0.00, TipologiaPagamento.BANCOMAT);
			Sessione.getInstance().setPortafoglioCliente(nuovoPortafoglio);
			switchScene(stage, "Bancomat.fxml", "Bancomat");
		} else if (clickedButton.getId().equals("sceglipaypal")) {
			showAlert("PayPal", "ti sei connesso al tuo account PayPal");
			PortafoglioVirtuale nuovoPortafoglio = new PortafoglioVirtuale(Sessione.getInstance().getPortafoglioCliente().generaIdCasuale(), 0.00, TipologiaPagamento.PAYPAL);
			Sessione.getInstance().setPortafoglioCliente(nuovoPortafoglio);
			switchScene(stage, "PortafoglioVirtuale.fxml", "PortafoglioVirtuale");	
		}
    }
    
    private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private void switchScene(Stage stage, String fxml, String title) {
		try {
			System.out.println("sono in switch scene");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/" + fxml));
			Parent root = loader.load();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Questo mostrerà eventuali errori
		}
	}

	
    
    
}
