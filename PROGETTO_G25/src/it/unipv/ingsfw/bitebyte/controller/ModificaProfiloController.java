package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.ClientService;
import it.unipv.ingsfw.bitebyte.services.ValidationService;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

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

	private void validaCampo(TextField campo, Label label, String tipoCampo) {
		label.setText(tipoCampo + " obbligatorio!");
		label.setTextFill(Color.RED);
	}

	@FXML
	private void controllaPassword(KeyEvent evento) {
		String password = passTXT.getText();
		if (!ValidationService.passwordValida(password)) {
			passwordL.setText("Min. 8 caratteri, un numero,\nuna lettera maiuscola e un carattere speciale.");
			passwordL.setTextFill(Color.RED);
		} else {
			passwordL.setText("");
		}
	}

	// USERNAME DA SISTEMARE
	/*@FXML
	private void controllaUsername1() {
		String nuovoUsername = usernameTXT.getText();
		String usernameAttuale = Sessione.getInstance().getClienteConnesso().getUsername();

		if (nuovoUsername.equals(usernameAttuale)) {
			usernameL.setText("Uguale allo username precedente!");
			usernameL.setTextFill(Color.RED);
			return;
		} else {
			usernameL.setText("");
		}
	}*/

	@FXML
	private boolean controllaUsername2() {
		String nuovoUsername = usernameTXT.getText();
		if (!clienteService.isUsernameDisponibile(nuovoUsername)) { // se lo username non è disponibile
			usernameL.setText("Non puoi mettere un Username già in uso o uguale al precedente");
			usernameL.setTextFill(Color.RED);
			return false;
		} else {
			usernameL.setText("");
		}
		
		return true;
	}

	@FXML
	public void modificaDati(ActionEvent event) {
		String nomeUtente = usernameTXT.getText();
		String password = passTXT.getText();
		String nome = nomeTXT.getText();
		String cognome = cognomeTXT.getText();

		if (!(ValidationService.passwordValida(password) && ValidationService.isNomeValido(nome)
				&& ValidationService.isNomeValido(cognome) && ValidationService.isNomeValido(nomeUtente)
				&& controllaUsername2())) {
			AlertUtils.showAlert("Errore", "Inserisci campi validi");
			return;
		}
		Cliente clienteModificato = new Cliente(Sessione.getInstance().getClienteConnesso().getCf(), nome, cognome,
				Sessione.getInstance().getClienteConnesso().getEmail(), password,
				Sessione.getInstance().getClienteConnesso().getDataN(), nomeUtente);
		clienteService.aggiornaProfilo(clienteModificato);
		Sessione.getInstance().setClienteConnesso(clienteModificato);
		AlertUtils.showAlert("Successo", "DATI MODIFICATI!");
		switchScene.Scene(salvaModifica, "ProfiloCliente.fxml", "Profilo Cliente");
	}

	@FXML
	public void cambiaScena(ActionEvent event) {
		System.out.println("Pulsante per tornare al profilo premuto");
		switchScene.Scene(tornaProfilo, "ProfiloCliente.fxml", "Profilo Cliente");
	}

}
