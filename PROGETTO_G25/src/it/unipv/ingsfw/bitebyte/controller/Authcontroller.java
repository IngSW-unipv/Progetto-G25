//CONTROLLER
package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.AmministratoreDAO;
import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.AmministratoreService;
import it.unipv.ingsfw.bitebyte.services.AuthService;
import it.unipv.ingsfw.bitebyte.services.ValidationService;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Authcontroller implements Initializable {

	@FXML
	private TextField usernameLogin;
	@FXML
	private PasswordField passwordLogin;
	@FXML
	private Label erroreLogin;

	@FXML
	private TextField usernameReg;
	@FXML
	private TextField emailReg;
	@FXML
	private TextField cfReg;
	@FXML
	private DatePicker dataNReg;
	@FXML
	private TextField nomeReg;
	@FXML
	private TextField cognomeReg;
	@FXML
	private PasswordField passwordReg;
	@FXML
	private PasswordField passwordConfReg; // passwordConfRef = conferma password
	@FXML
	private Label erroreRegEmail;
	@FXML
	private Label erroreRegPassword;

	// Pulsanti login-view
	@FXML
	private Button login;
	@FXML
	private Button bottoneregistrati;

	// Pulsanti registration-view
	@FXML
	private Button tornaLogin;
	@FXML
	private Button registrato;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Questo metodo verrà chiamato quando la vista è completamente caricata
		System.out.println("Metodo initialize chiamato!");
	}

	@FXML
	public void cambiaScena(javafx.event.ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		//Stage stage = (Stage) clickedButton.getScene().getWindow();
		SwitchSceneUtils switchSceneUtils = new SwitchSceneUtils();
		if (clickedButton.getId().equals("pulsanteVaiALogin")) {
			switchSceneUtils.Scene(clickedButton, "login-view.fxml", "Login");
		} else if (clickedButton.getId().equals("bottoneregistrati")) {
			switchSceneUtils.Scene(clickedButton, "registration-view.fxml", "Registrazione");
		} else if (clickedButton.getId().equals("tornaLogin")) {
			switchSceneUtils.Scene(clickedButton, "login-view.fxml", "Login");
		} else if (clickedButton.getId().equals("registrato")) {
			switchSceneUtils.Scene(clickedButton, "login-view.fxml", "Login");
		}
	}

	@FXML
	public void accedi() {
		String nomeUtente = usernameLogin.getText();
		String password = passwordLogin.getText();
        if (nomeUtente.equals("root")) { 
        	System.out.println("Prova root");
        	AmministratoreDAO amministratoreDAO = new AmministratoreDAO();
        	AmministratoreService amministratoreService = new AmministratoreService(amministratoreDAO);
        	amministratoreService.loginAmministratore(password);

        }
        ClienteDAO clienteDAO = new ClienteDAO(); // Crea un'istanza di ClienteDAO
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO(); // Crea un'istanza di
																				// PortafoglioVirtualeDAO

		AuthService authService = new AuthService(clienteDAO, portafoglioDAO); // Passa i DAO al costruttore di
																				// AuthService
		Cliente cliente = authService.login(nomeUtente, password);
		if (cliente != null) {
			AlertUtils.showAlert("Successo", "Acesso eseguito correttamente");
			//Stage stage = (Stage) login.getScene().getWindow();
			SwitchSceneUtils switchSceneUtils = new SwitchSceneUtils();
			switchSceneUtils.Scene(login, "ProfiloCliente.fxml", "Login");
		} else {
			AlertUtils.showAlert("Errore", "Credenziali errate");
		}
	}

	// Controlla l'email in tempo reale mentre l'utente digita
	@FXML
	private void controllaEmail(KeyEvent evento) {
		String email = emailReg.getText();
		if (!ValidationService.emailFormatoValido(email)) {
			erroreRegEmail.setText("L'email deve terminare con @universitadipavia.it");
			erroreRegEmail.setTextFill(Color.RED);
		} else {
			erroreRegEmail.setText(""); // Nasconde il messaggio d'errore
		}
	}

	@FXML
	private void controllaPassword(KeyEvent evento) {
		String password = passwordReg.getText();
		if (!ValidationService.passwordValida(password)) {
			erroreRegPassword.setText("Min. 8 caratteri, un numero, una lettera maiuscola e un carattere speciale.");
			erroreRegPassword.setTextFill(Color.RED);
		} else {
			erroreRegPassword.setText(""); // Nasconde il messaggio d'errore
		}
	}

	@FXML
	public void controlloCampi() {
		System.out.println("Bottone premuto!");

		String cf = cfReg.getText().toUpperCase();
		String nomeUtente = usernameReg.getText();
		String email = emailReg.getText();
		String password = passwordReg.getText();
		String confirmPassword = passwordConfReg.getText();
		String nome = nomeReg.getText();
		String cognome = cognomeReg.getText();
		LocalDate dataNascita = dataNReg.getValue();

		ClienteDAO clienteDAO = new ClienteDAO();

		String errore = ValidationService.controlloCampi(cf, nomeUtente, email, password, confirmPassword, nome,
				cognome, dataNascita, clienteDAO);

		if (errore != null) {
			AlertUtils.showAlert("Errore", errore);
			return;
		}

		Cliente nuovoCliente = new Cliente(cf, nome, cognome, email, password, dataNascita, nomeUtente);
		clienteDAO.registraCliente(nuovoCliente);
		AlertUtils.showAlert("Successo", "REGISTRAZIONE COMPLETATA!");
		SwitchSceneUtils switchSceneUtils = new SwitchSceneUtils();
		//Stage stage = (Stage) registrato.getScene().getWindow();
		switchSceneUtils.Scene(registrato, "login-view.fxml", "Login");
	}

}