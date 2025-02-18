package it.unipv.ingsfw.bitebyte.controller;
import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage; 

public class Authcontroller {

	   	private ClienteDAO clienteDAO = new ClienteDAO();

	    @FXML private TextField usernameLogin;
	    @FXML private PasswordField passwordLogin;
	    @FXML private Label erroreLogin;

	    @FXML private TextField usernameReg;
	    @FXML private TextField emailReg;
	    @FXML private TextField cfReg;
	    @FXML private DatePicker dataNReg;
	    @FXML private TextField nomeReg;
	    @FXML private TextField cognomeReg;
	    @FXML private PasswordField passwordReg;
	    @FXML private PasswordField passwordConfReg;			//passwordConfRef = conferma password
	    @FXML private Label erroreRegEmail;
	    @FXML private Label erroreRegPassword;					
	    
	    
	    

	    @FXML private Button pulsanteAccedi;
	    @FXML private Button pulsanteRegistrati;
	    @FXML private Button pulsanteVaiARegistrazione;
	    @FXML private Button pulsanteVaiALogin;

	    @FXML
	   //commento di prova
	    public void inizializza() {
	        // Controllo in tempo reale mentre l'utente digita
	    	emailReg.setOnKeyReleased(this::controllaEmail);
	    	passwordReg.setOnKeyReleased(this::controllaPassword);
	    	
	    	pulsanteVaiARegistrazione.setOnAction(event -> switchScene("registration-view.fxml", "Registrazione"));
	    	pulsanteRegistrati.setOnAction(event -> controlloCampi());
	    }

	    @FXML
	    public void accedi() {
	        String nomeUtente = usernameLogin.getText();
	        String password = passwordLogin.getText();

	        if (clienteDAO.verificaLogin(nomeUtente, password)) {
	        	erroreLogin.setText("Accesso effettuato con successo!");
	        	erroreLogin.setTextFill(Color.GREEN);
	        } else {
	        	erroreLogin.setText("Nome utente o password errati!");
	        	erroreLogin.setTextFill(Color.RED);
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
	    private void controllaEmail(KeyEvent evento) {
	        String email = emailReg.getText();
	        if (!emailFormatoValido(email)) {
	        	erroreRegEmail.setText("L'email deve terminare con @universitadipavia.it");
	        	erroreRegEmail.setTextFill(Color.RED);
	        } else {
	        	erroreRegEmail.setText(""); // Nasconde il messaggio d'errore
	        }
	    }

	    // Controlla la password in tempo reale mentre l'utente digita
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
	        return email.endsWith("@universitadipavia.it");
	    }

	    // Controlla se la password è valida (min. 8 caratteri, un numero, una maiuscola, un carattere speciale)
	    private boolean passwordValida(String password) {
	        return password.length() >= 8 &&
	               password.matches(".*\\d.*") && // Deve contenere almeno un numero
	               password.matches(".*[A-Z].*") && // Deve contenere almeno una lettera maiuscola
	               password.matches(".*[!@#$%^&*()].*"); // Deve contenere almeno un carattere speciale
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
	        
	        if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || 
	                password.isEmpty() || confirmPassword.isEmpty() || 
	                dataNascita == null) {
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
	        
	        switchScene("login-view.fxml", "Login");
	        	        
	    }
	    
	    private void switchScene(String fxml, String title) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
	            Parent root = loader.load();
	            Stage stage = (Stage) pulsanteAccedi.getScene().getWindow();
	            stage.setTitle(title);
	            stage.setScene(new Scene(root));
	            stage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	
}
