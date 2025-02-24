package it.unipv.ingsfw.bitebyte.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class ProfiloClientController {

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
    @FXML
    private Label nomeL;
    @FXML
    private Label cognomeL;
    @FXML
    private Label passwordL;
    @FXML
    private Label usernameL;

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

    public void logout() {
        Sessione.getInstance().logout();
        mostraLogin();
    }

    private void mostraLogin() {
        System.out.println("Sei stato disconnesso. Torna alla schermata di login.");
    }    

}
