//CONTROLLER
package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;

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

	private ClienteDAO clienteDAO = new ClienteDAO();

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

	@FXML
	private Button pulsanteAccedi;
	@FXML
	private Button pulsanteRegistrati;
	@FXML
	private Button pulsanteVaiARegistrazione;
	@FXML
	private Button pulsanteVaiALogin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Questo metodo verrà chiamato quando la vista è completamente caricata
		System.out.println("Metodo initialize chiamato!");
	}

	@FXML
	public void cambiaScena(javafx.event.ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		Stage stage = (Stage) clickedButton.getScene().getWindow(); // Ottieni lo Stage dal bottone premuto
		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("pulsanteVaiALogin")) {
			System.out.println("sono in switch scene");
			switchScene(stage, "login-view.fxml", "Login");
		} else if (clickedButton.getId().equals("pulsanteRegistrati")) {
			switchScene(stage, "registration-view.fxml", "Registrazione");
		} else if (clickedButton.getId().equals("pulsanteVaiARegistrazione")) {
			switchScene(stage, "registration-view.fxml", "Registrazione");
		}
	}

//	@FXML
/**	public void cambiaScena2(ActionEvent event) {
*		String nomeUtente = usernameLogin.getText();
*		String password = passwordLogin.getText();
*		Sessione.getInstance().setClienteConnesso(clienteDAO.getCliente(nomeUtente, password));
	*	
	*	if (clienteDAO.verificaLogin(nomeUtente, password)) {
	*		Sessione.getInstance().setClienteConnesso(clienteDAO.getCliente(nomeUtente, password));
*
*			try {
*				FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfiloCliente.fxml"));
*				Parent root = loader.load();
*
*				ProfiloClienteController controller = loader.getController();
*				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
*				Scene scene = new Scene(root);
*				stage.setScene(scene);
*				stage.show();
*			} catch (IOException e) {
*				e.printStackTrace();
*				System.out.println("Errore nel caricamento della vista: ProfiloCliente");
*			}
*		} else {
*			showAlert("Errore", "Credenziali errate");
*			return;
*		}
*		

	}*/

	@FXML
	public void accedi() {
		String nomeUtente = usernameLogin.getText();
		String password = passwordLogin.getText();
		Sessione.getInstance().setClienteConnesso(clienteDAO.getCliente(nomeUtente, password));

		if (clienteDAO.verificaLogin(nomeUtente, password)) {
			Sessione.getInstance().setClienteConnesso(clienteDAO.getCliente(nomeUtente, password));
			showAlert("Successo", "Acesso eseguito correttamente");
			Stage stage = (Stage) pulsanteAccedi.getScene().getWindow();
			switchScene(stage, "ProfiloCliente.fxml", "Profilo");
		} else {
			showAlert("Errore", "Credenziali errate");
			return;
		}
	}

	@FXML
	public void controlliOnTime() {

		String email = emailReg.getText();
		String password = passwordReg.getText();

		if (!emailFormatoValido(email)) {
			erroreRegEmail.setText("Errore nei dati inseriti!");
			erroreRegEmail.setTextFill(Color.RED);
			return;
		}

		if (!passwordValida(password)) {
			erroreRegPassword.setText("Errore nei dati inseriti!");
			erroreRegPassword.setTextFill(Color.RED);
			return;
		}

	}

	// Controlla l'email in tempo reale mentre l'utente digita
	@FXML
	private void controllaEmail(KeyEvent evento) {
		System.out.println("prova");
		String email = emailReg.getText();
		if (!emailFormatoValido(email)) {
			erroreRegEmail.setText("L'email deve terminare con @universitadipavia.it");
			erroreRegEmail.setTextFill(Color.RED);
		} else {
			erroreRegEmail.setText(""); // Nasconde il messaggio d'errore
		}
	}

	// Controlla la password in tempo reale mentre l'utente digita
	@FXML
	private void controllaPassword(KeyEvent evento) {
		String password = passwordReg.getText();
		if (!passwordValida(password)) {
			erroreRegPassword.setText("Min. 8 caratteri, un numero, una lettera maiuscola e un carattere speciale.");
			erroreRegPassword.setTextFill(Color.RED);
		} else {
			erroreRegPassword.setText(""); // Nasconde il messaggio d'errore
		}
	}

	// Controlla se l'email è valida
	private boolean emailFormatoValido(String email) {
		System.out.println(email);
		return email.endsWith("@universitadipavia.it");
	}

	// Controlla se password valida
	public boolean passwordValida(String password) {
		if (password == null) {
			return false; // La password non può essere nulla
		}

		// controlla che la password abbia:
	    // - Almeno 8 caratteri
	    // - Almeno una lettera maiuscola
	    // - Almeno un numero
	    // - Almeno un carattere speciale
	    String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";
		
		return password.matches(regex);
	}
	

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void controlloCampi() {
		System.out.println("Bottonepremuto!");
		String cf = cfReg.getText().toUpperCase();
		String nomeUtente = usernameReg.getText();
		String email = emailReg.getText();
		String password = passwordReg.getText();
		String confirmPassword = passwordConfReg.getText();
		String nome = nomeReg.getText();
		String cognome = cognomeReg.getText();
		LocalDate dataNascita = dataNReg.getValue();

		if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty()
				|| confirmPassword.isEmpty() || dataNascita == null) {
			showAlert("Errore", "Tutti i campi devono essere compilati");
			return;
		}

		if (!password.equals(confirmPassword)) {
			showAlert("Errore", "Le password non coincidono.");
			return;
		}

		if (clienteDAO.esisteUsername(nomeUtente)) {
			showAlert("Errore", "Username già esistente.");
			return;
		}

		if (clienteDAO.esisteCliente(email)) {
			showAlert("Errore", "Email già registrata.");
			return;
		}

		Cliente nuovoCliente = new Cliente(cf, nome, cognome, email, password, dataNascita, nomeUtente);
		clienteDAO.registraCliente(nuovoCliente);
		showAlert("Successo", "REGISTRAZIONE COMPLETATA!");
		Stage stage = (Stage) pulsanteRegistrati.getScene().getWindow();
		switchScene(stage, "login-view.fxml", "Login");

	}

	private void switchScene(Stage stage, String fxml, String title) {
		try {
			System.out.println("sono in switch scene");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxml));
			Parent root = loader.load();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Questo mostrerà eventuali errori
		}
	}
}