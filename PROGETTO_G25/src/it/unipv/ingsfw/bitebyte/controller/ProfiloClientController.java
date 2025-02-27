package it.unipv.ingsfw.bitebyte.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ProfiloClientController {

	// ProfiloCliente ha questi campi da riempire
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtCognome;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField txtCf;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtDataNascita;

	// pulsanti ProfiloCliente
	@FXML
	private Button idport;
	@FXML
	private Button idmodifica;
	@FXML
	private Button logout;
	@FXML
	private Button btnConnetti;

	@FXML
	public void initialize() {
		if (Sessione.getInstance().getClienteConnesso() != null) {
			caricaDatiProfilo();
		} else {
			System.out.println("Errore: Nessun cliente connesso.");
			return;
		}
	}

	@FXML
	private void caricaDatiProfilo() {
		Cliente cliente = Sessione.getInstance().getClienteConnesso();

		txtNome.setText(cliente.getNome());
		txtCognome.setText(cliente.getCognome());
		txtUsername.setText(cliente.getUsername());
		txtPassword.setText(cliente.getPassword());
		txtCf.setText(cliente.getCf());
		txtEmail.setText(cliente.getEmail());

		LocalDate dataNascita = cliente.getDataN();
		txtDataNascita
				.setText(dataNascita != null ? dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A");

	}

	@FXML
	public void cambiaScena(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		SwitchSceneUtils switchSceneUtils = new SwitchSceneUtils();
		System.out.println("Pulsante premuto: " + clickedButton.getId());
		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("idport")) {
			if (Sessione.getInstance().getPortafoglioCliente() == null) {
				switchSceneUtils.Scene(clickedButton, "TipologiaPagamento.fxml", "Tipologia Pagamento");
			} else {
				switchSceneUtils.Scene(clickedButton, "PortafoglioVirtuale.fxml", "Portafoglio Virtuale");
			}
		} else if (clickedButton.getId().equals("idmodifica")) {
			switchSceneUtils.Scene(clickedButton, "ModificaProfiloCliente.fxml", "Modifica Profilo");
		} else if (clickedButton.getId().equals("logout")) {
			Sessione.getInstance().logout();
			AlertUtils.showAlert("Successo", "Effettuato Logout");
			switchSceneUtils.Scene(clickedButton, "login-view.fxml", "Login");
		} else if (clickedButton.getId().equals("btnConnetti")) {
			switchSceneUtils.Scene(clickedButton, "collegamentoDistributore.fxml", "Connettiti al distributore");
		} else if (clickedButton.getId().equals("btnStoricoOrdini")) {
			switchSceneUtils.Scene(clickedButton, "StoricoOrdini.fxml", "Storico degli ordini");
		}
	}

}
