package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.ClientService;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModificaProfiloController {
	// ModificaProfilo ha questi campi da riempire
	@FXML
	private TextField nomeTXT;
	@FXML
	private TextField cognomeTXT;
	@FXML
	private TextField usernameTXT;
	@FXML
	private TextField passTXT;

	// label ModificaProfilo
	@FXML
	private Label nomeL;
	@FXML
	private Label cognomeL;
	@FXML
	private Label passwordL;
	@FXML
	private Label usernameL;

	// pulsanti ModificaProfilo
	@FXML
	private Button salvaModifica;
	@FXML
	private Button tornaProfilo;

	private ClientService clienteService;
	private final SwitchSceneUtils switchScene;

	public ModificaProfiloController() {
		this.clienteService = new ClientService();
		this.switchScene = new SwitchSceneUtils();
	}

	@FXML
	private void controllaNomeOCognome() {
		validaCampo(nomeTXT, nomeL, "Nome");
		validaCampo(cognomeTXT, cognomeL, "Cognome");
	}

	private void validaCampo(TextField campo, Label erroreLabel, String tipoCampo) {
		if (!clienteService.isCampoValido(campo.getText())) {
			erroreLabel.setText(tipoCampo + " non valido!");
			erroreLabel.setTextFill(Color.RED);
		} else {
			erroreLabel.setText("");
		}
	}

	@FXML
	private void controllaPassword(KeyEvent evento) {
		String password = passTXT.getText();
		if (!clienteService.isPasswordValida(password)) {
			passwordL.setText("Min. 8 caratteri, un numero,\n una lettera maiuscola e un carattere speciale.");
			passwordL.setTextFill(Color.RED);
		} else {
			passwordL.setText("");
		}
	}

	@FXML
	private void controllaUsername() {
		String nuovoUsername = usernameTXT.getText();
		String usernameAttuale = Sessione.getInstance().getClienteConnesso().getUsername();

		if (nuovoUsername.equals(usernameAttuale)) {
			usernameL.setText("È uguale allo username precedente!");
			usernameL.setTextFill(Color.RED);
			return;
		}

		if (!clienteService.isUsernameDisponibile(nuovoUsername)) {
			usernameL.setText("Username già in uso.");
			usernameL.setTextFill(Color.RED);
		} else {
			usernameL.setText("");
		}
	}

	@FXML
	public void modificaDati(ActionEvent event) {
		String nomeUtente = usernameTXT.getText();
		String password = passTXT.getText();
		String nome = nomeTXT.getText();
		String cognome = cognomeTXT.getText();
		Cliente clienteconnesso = Sessione.getInstance().getClienteConnesso();
		Cliente clienteModificato = new Cliente(Sessione.getInstance().getClienteConnesso().getCf(), nome, cognome,
				nomeUtente, password);
		clienteModificato.setDataN(clienteconnesso.getDataN());
		clienteModificato.setEmail(clienteconnesso.getEmail());
		clienteService.aggiornaProfilo(clienteModificato);
		Sessione.getInstance().setClienteConnesso(clienteModificato);
		
		Sessione.getInstance().logout();
		AlertUtils.showAlert("Successo", "DATI MODIFICATI!");
		switchScene.Scene(salvaModifica, "login-view.fxml", "Login");
	}

	@FXML
	public void cambiaScena(javafx.event.ActionEvent event) {
		System.out.println("Pulsante per tornare al profilo premuto");
		switchScene.Scene(tornaProfilo, "ProfiloCliente.fxml", "Profilo Cliente");
	}

}
