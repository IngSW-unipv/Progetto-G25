package it.unipv.ingsfw.bitebyte.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import it.unipv.ingsfw.bitebyte.dao.BancomatDAO;
import it.unipv.ingsfw.bitebyte.models.Bancomat;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BancomatController {
	
	   @FXML
	    private TextField cvv;
	    @FXML
	    private TextField circuito;
	    @FXML
	    private TextField numcarta;
	    @FXML
	    private TextField titolare;
	    @FXML
	    private TextField datascad;
	    @FXML
	    private Button pulsanteSalva;

		public void registraCarta() {
			String numCarta = numcarta.getText();
			String tit = titolare.getText();
			String circ = circuito.getText();
			int codice = Integer.parseInt(cvv.getText());
			YearMonth dataScad = YearMonth.parse(datascad.getText());
			
			Bancomat datiBancomat = new Bancomat(numCarta, tit, dataScad, circ, codice);
			BancomatDAO bancomatDAO = new BancomatDAO();
			bancomatDAO.creaBancomat(datiBancomat, Sessione.getInstance().getClienteConnesso());
			showAlert("Successo", "DATI BANCOMAT CARICATI!");
			Stage stage = (Stage) pulsanteSalva.getScene().getWindow();
			switchScene(stage, "PortafoglioVirtuale.fxml", "Portafoglio Virtuale");
			Sessione.getInstance().getPortafoglioCliente().setTipologiaPagamento(TipologiaPagamento.BANCOMAT);
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
