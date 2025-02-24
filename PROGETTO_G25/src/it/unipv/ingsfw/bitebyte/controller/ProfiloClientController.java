package it.unipv.ingsfw.bitebyte.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    //pulsanti ProfiloCliente
    @FXML
    private Button idport;
    @FXML
    private Button idmodifica;
    @FXML
    private Button logout;
    
    //pulsanti ModificaProfilo
    @FXML
    private Button salvaModifica;
    @FXML
    private Button tornaProfilo;

    private ClientService clienteService;

    public ProfiloClientController() {
        this.clienteService = new ClientService();
    }

    @FXML
    public void initialize() {
        if (Sessione.getInstance().getClienteConnesso() != null) {
            caricaDatiProfilo();
        } else {
            System.out.println("Errore: Nessun cliente connesso.");
            return;
        }

        txtNome.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                controllaNomeOCognome();
            }
        });

        txtCognome.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                controllaNomeOCognome();
            }
        });

        txtUsername.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                controllaUsername();
            }
        });
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
        txtDataNascita.setText(dataNascita != null ? dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A");
    
      /*  if (cliente.getDataN() != null) {
            txtDataNascita.setText(cliente.getDataN().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            txtDataNascita.setText("N/A");
        }
        */      
    }

    @FXML
    private void controllaNomeOCognome() {
        validaCampo(txtNome, nomeL, "Nome");
        validaCampo(txtCognome, cognomeL, "Cognome");
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
        String password = txtPassword.getText();
        if (!clienteService.isPasswordValida(password)) {
            passwordL.setText("Min. 8 caratteri, un numero,\n una lettera maiuscola e un carattere speciale.");
            passwordL.setTextFill(Color.RED);
        } else {
            passwordL.setText("");
        }
    }

    @FXML
    private void controllaUsername() {
        String nuovoUsername = txtUsername.getText();
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
    	Cliente clienteModificato = new Cliente(nome, cognome, password, nomeUtente);
		clienteService.aggiornaProfilo(clienteModificato);
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
	
    /*
    
    public void modificaProfilo(String nuovoNome, String nuovoCognome, String nuovoUsername, String nuovaPassword) {
        Cliente clienteModificato = new Cliente(
                Sessione.getInstance().getClienteConnesso().getCf(),
                nuovoNome,
                nuovoCognome,
                nuovoUsername,
                nuovaPassword
        );

        boolean aggiornamentoRiuscito = clienteService.aggiornaProfilo(clienteModificato);

        if (aggiornamentoRiuscito) {
            Sessione.getInstance().setClienteConnesso(clienteModificato);
            System.out.println("Profilo aggiornato con successo.");
        } else {
            System.out.println("Errore nell'aggiornamento del profilo.");
        }
    }
    
    
 // Metodo compatibile con onAction
    @FXML
    public void onModificaProfilo(ActionEvent event) {
        // Recupera i dati dalle TextField (o altri input)
        String nuovoNome = nomeTXT.getText();
        String nuovoCognome = cognomeTXT.getText();
        String nuovoUsername = usernameTXT.getText();
        String nuovaPassword = passTXT.getText();

        // Chiama il metodo originale
        modificaProfilo(nuovoNome, nuovoCognome, nuovoUsername, nuovaPassword);
    }*/
    
    
	@FXML
	public void cambiaScena(javafx.event.ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		System.out.println("Pulsante premuto: " + clickedButton.getId());
		Stage stage = (Stage) clickedButton.getScene().getWindow(); // Ottieni lo Stage dal bottone premuto
		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("idport")) {
			System.out.println("sono in switch scene");
			switchScene(stage, "PortafoglioVirtuale.fxml", "Portafoglio Virtuale");
		} else if (clickedButton.getId().equals("idmodifica")) {
			System.out.println("sono in switch scene");
			switchScene(stage, "ModificaProfilo.fxml", "Modifica Profilo");
		} else if (clickedButton.getId().equals("logout")) {
			// dovrei chiamare il metodo logout per annullare la sessione?
			System.out.println("sono in switch scene");
			switchScene(stage, "login-view.fxml", "Login");
		}else if (clickedButton.getId().equals("tornaProfilo")) {
			System.out.println("sono in switch scene");
			switchScene(stage, "ProfiloCliente.fxml", "Profilo Cliente");
		}
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
    
        

    public void logout() {
        Sessione.getInstance().logout();
        mostraLogin();
    }

    private void mostraLogin() {
        System.out.println("Sei stato disconnesso. Torna alla schermata di login.");
    }    

}
