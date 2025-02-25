package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.ClientService;
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
    
    //pulsanti ModificaProfilo
    @FXML
    private Button salvaModifica;
    @FXML
    private Button tornaProfilo;

    private ClientService clienteService;

    public ModificaProfiloController() {
        this.clienteService = new ClientService();
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
    	Cliente clienteModificato = new Cliente(Sessione.getInstance().getClienteConnesso().getCf(), nome, cognome, nomeUtente, password);
		clienteModificato.setDataN(clienteconnesso.getDataN());
		clienteModificato.setEmail(clienteconnesso.getEmail());
    	clienteService.aggiornaProfilo(clienteModificato);
    	Sessione.getInstance().setClienteConnesso(clienteModificato);
		showAlert("Successo", "DATI MODIFICATI!");
		Stage stage = (Stage) salvaModifica.getScene().getWindow();
		switchScene(stage, "ProfiloCliente.fxml", "Profilo Cliente");
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
	
	@FXML
	public void cambiaScena(javafx.event.ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		System.out.println("Pulsante premuto: " + clickedButton.getId());
		Stage stage = (Stage) clickedButton.getScene().getWindow(); // Ottieni lo Stage dal bottone premuto
		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("tornaProfilo")) {
			System.out.println("sono in switch scene");
			switchScene(stage, "ProfiloCliente.fxml", "Profilo Cliente");
		}
	}
    
    
    
    

}
