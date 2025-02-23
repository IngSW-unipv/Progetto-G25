package it.unipv.ingsfw.bitebyte.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ProfiloClienteController {

    // Collegamento ai campi della UI
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
    private Button btnConnetti;
    
    /**
     * Metodo per inizializzare la view con i dati del cliente.
     */
    @FXML
    public void initialize() {
        if (Sessione.getInstance().getClienteConnesso() != null) {
            caricaDatiProfilo();
        } else {
            System.out.println("Errore: Nessun cliente connesso.");
            return;
        }
        btnConnetti.setOnAction(event -> connettiDistributore(event)); // Set the button action
    }

    /**
     * Popola i campi della UI con i dati di clienteConnesso.
     */
    private void caricaDatiProfilo() {
        // Imposta i valori nei campi della UI
        txtNome.setText(Sessione.getInstance().getClienteConnesso().getNome());
        txtCognome.setText(Sessione.getInstance().getClienteConnesso().getCognome());
        txtUsername.setText(Sessione.getInstance().getClienteConnesso().getUsername());
        txtPassword.setText(Sessione.getInstance().getClienteConnesso().getPassword());
        txtCf.setText(Sessione.getInstance().getClienteConnesso().getCf());
        txtEmail.setText(Sessione.getInstance().getClienteConnesso().getEmail());

        // Converte la data di nascita in formato leggibile
        LocalDate dataNascita = Sessione.getInstance().getClienteConnesso().getDataN();
        txtDataNascita
                .setText(dataNascita != null ? dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A");
    }

    /**
     * Metodo per visualizzare il profilo del cliente.
     * 
     * @param cf il codice fiscale del cliente di cui si vogliono visualizzare i
     *           dettagli.
     */
    public void visualizzaProfilo() {
        // Verifica che c'è un cliente connesso
        if (Sessione.getInstance().getClienteConnesso() != null) {
            // Visualizza i dettagli del cliente
            System.out.println("Profilo Cliente:");
            System.out.println("Nome Utente: " + Sessione.getInstance().getClienteConnesso().getUsername());
            System.out.println("Nome: " + Sessione.getInstance().getClienteConnesso().getNome());
            System.out.println("Cognome: " + Sessione.getInstance().getClienteConnesso().getCognome());
            System.out.println("Password: " + Sessione.getInstance().getClienteConnesso().getPassword());
        } else {
            System.out.println("Cliente non trovato.");
        }
    }

    // Metodo per verificare se nome o cognome sono validi
    public boolean isNomeOCognomeValido(String nomeOCognome) {
        if (nomeOCognome == null || nomeOCognome.trim().isEmpty()) {
            return false; // Il nome/cognome non può essere nullo o vuoto
        }

        // controlla che nome/cognome abbiano prima lettera maiuscola, seguite solo da
        // minuscole e lettere accentate
        String regex = "^[A-ZÀÈÉÌÒÓÙ][a-zàèéìòóù]+$";
        return nomeOCognome.matches(regex);
    }

    // Metodo per verificare se password valida
    public boolean isPasswordValida(String password) {
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

    // Metodo per modificare il profilo del cliente.
    public void modificaProfilo(String nuovoNome, String nuovoCognome, String nuovoUsername, String nuovaPassword) {
        ClienteDAO clienteDAO = new ClienteDAO();

        // Verifica validità del nome
        if (!isNomeOCognomeValido(nuovoNome)) {
            System.out.println("Errore: Il nome non è valido.");
            return;
        }

        // Verifica validità del cognome
        if (!isNomeOCognomeValido(nuovoCognome)) {
            System.out.println("Errore: Il cognome non è valido.");
            return;
        }

        // Verifica validità della password
        if (!isPasswordValida(nuovaPassword)) {
            System.out.println("Errore: La password non è valida. Deve avere esattamente 8 caratteri, almeno una maiuscola e un numero.");
            return;
        }

        // Verifica se ho messo uno username uguale al precedente e se il nuovo username
        // esiste già nel database
        if (!nuovoUsername.equals(Sessione.getInstance().getClienteConnesso().getUsername())
                && clienteDAO.esisteUsername(nuovoUsername)) {
            System.out.println("Errore: Username già in uso. Scegliere un altro username.");
            return;
        }

        // Creazione di un oggetto Cliente con i nuovi dati
        Cliente clienteModificato = new Cliente(Sessione.getInstance().getClienteConnesso().getCf(), nuovoNome,
                nuovoCognome, nuovoUsername, nuovaPassword);

        // Salvataggio delle modifiche nel database
        boolean aggiornamentoRiuscito = clienteDAO.modificaProfilo(clienteModificato);

        if (aggiornamentoRiuscito) {
            // Se il database è stato aggiornato con successo, aggiorno anche
            // clienteConnesso
            Sessione.getInstance().getClienteConnesso().setNome(nuovoNome);
            Sessione.getInstance().getClienteConnesso().setCognome(nuovoCognome);
            Sessione.getInstance().getClienteConnesso().setUsername(nuovoUsername);
            Sessione.getInstance().getClienteConnesso().setPassword(nuovaPassword);
            System.out.println("Profilo aggiornato con successo.");
        } else {
            System.out.println("Errore nell'aggiornamento del profilo.");
        }
    }

    // Metodo chiamato quando l'utente fa clic sul pulsante "Logout"
    public void logout() {
        Sessione.getInstance().logout();
        mostraLogin();
    }

    // Metodo per mostrare la schermata di login
    private void mostraLogin() {
        System.out.println("Sei stato disconnesso. Torna alla schermata di login.");
    }

    @FXML
    public void connettiDistributore(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        Stage stage = (Stage) btnConnetti.getScene().getWindow();
        switchScene(stage, "collegamentoDistributore.fxml", "Profilo");
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
