package it.unipv.ingsfw.bitebyte.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

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
	private Label nomeL;
	@FXML
	private Label cognomeL;
	@FXML
	private Label passwordL;
	@FXML
	private Label usernameL;

	@FXML
	public void initialize() {

		// se il cliente connesso esiste carica i dati profilo
		if (Sessione.getInstance().getClienteConnesso() != null) {
			caricaDatiProfilo();
		} else {
			System.out.println("Errore: Nessun cliente connesso.");
			return;
		}

		// assicura che il controllo venga fatto solo quando si perde il focus
		txtNome.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				isNomeOCognomeValido();
			}
		});

		txtCognome.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				isNomeOCognomeValido();
			}
		});

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

	@FXML
	public void isNomeOCognomeValido() {
		controllaCampo(txtNome, nomeL, "Nome");
		controllaCampo(txtCognome, cognomeL, "Cognome");
	}

	private void controllaCampo(TextField campo, Label erroreLabel, String tipoCampo) {
		String testo = campo.getText().trim();

		if (testo.isEmpty()) {
			erroreLabel.setText(tipoCampo + " obbligatorio!");
			erroreLabel.setTextFill(Color.RED);
			return;
		}

		String regex = "^[A-ZÀÈÉÌÒÓÙ][a-zàèéìòóù]+$";
		if (!testo.matches(regex)) {
			erroreLabel.setText(tipoCampo + " non valido!");
			erroreLabel.setTextFill(Color.RED);
		} else {
			erroreLabel.setText(""); // Nasconde l'errore se il campo è valido
		}
	}

	// Controlla la password in tempo reale mentre l'utente digita
	@FXML
	private void controllaPassword(KeyEvent evento) {
		String password = txtPassword.getText();
		if (!isPasswordValida(password)) {
			passwordL.setText("Min. 8 caratteri, un numero,\n una lettera maiuscola e un carattere speciale.");
			passwordL.setTextFill(Color.RED);
		} else {
			passwordL.setText(""); // Nasconde il messaggio d'errore
		}
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

}
