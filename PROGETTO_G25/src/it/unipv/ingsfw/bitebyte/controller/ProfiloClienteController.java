package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public class ProfiloClienteController {
   
		private Cliente clienteConnesso;

	/**
	 * Metodo per visualizzare il profilo del cliente.
	 * 
	 * @param cf il codice fiscale del cliente di cui si vogliono visualizzare i
	 *           dettagli.
	 */
	public void visualizzaProfilo() {
		
		// Verifica che c'è un cliente connesso
		if (clienteConnesso != null) {
			// Visualizza i dettagli del cliente
			System.out.println("Profilo Cliente:");
			System.out.println("Nome Utente: " + clienteConnesso.getUsername());
			System.out.println("Nome: " + clienteConnesso.getNome());
			System.out.println("Cognome: " + clienteConnesso.getCognome());
			System.out.println("Password: " + clienteConnesso.getPassword());
		} else {
			System.out.println("Cliente non trovato.");
		}
	}
	
	// Metodo per verificare se nome o cognome sono validi
	
	public boolean isNomeOCognomeValido(String nomeOCognome) {
	    if (nomeOCognome == null || nomeOCognome.trim().isEmpty()) {
	        return false; // Il nome/cognome non può essere nullo o vuoto
	    }

	    // controlla che nome/cognome abbiano prima lettera maiuscola, seguite solo da minuscole e lettere accentate
	    String regex = "^[A-ZÀÈÉÌÒÓÙ][a-zàèéìòóù]+$";

	    return nomeOCognome.matches(regex);
	}

	// Metodo per verificare se password valida
	
	public boolean isPasswordValida(String password) {
	    if (password == null) {
	        return false; // La password non può essere nulla
	    }

	    // controlla che la password abbia esattamente 8 caratteri con almeno un numero e almeno una maiuscola
	    String regex = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8}$";

	    return password.matches(regex);
	}
	
	
	// Metodo per modificare il profilo del cliente.

	public void modificaProfilo( String nuovoNome, String nuovoCognome, String nuovoUsername, String nuovaPassword) {
		
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

		    // Verifica se ho messo uno username uguale al precedente e se il nuovo username esiste già nel database
		    if (!nuovoUsername.equals(clienteConnesso.getUsername()) && clienteDAO.esisteUsername(nuovoUsername)) {
		        System.out.println("Errore: Username già in uso. Scegliere un altro username.");
		        return;
		    }

		    // Creazione di un oggetto Cliente con i nuovi dati
		    Cliente clienteModificato = new Cliente(
		        clienteConnesso.getCf(),
		        nuovoNome,
		        nuovoCognome,
		        nuovoUsername,
		        nuovaPassword
		    );

		    // Salvataggio delle modifiche nel database
		    boolean aggiornamentoRiuscito = clienteDAO.modificaProfilo(clienteModificato);

		    if (aggiornamentoRiuscito) {
		        // Se il database è stato aggiornato con successo, aggiorno anche clienteConnesso
		        clienteConnesso.setNome(nuovoNome);
		        clienteConnesso.setCognome(nuovoCognome);
		        clienteConnesso.setUsername(nuovoUsername);
		        clienteConnesso.setPassword(nuovaPassword);
		        
		        System.out.println("Profilo aggiornato con successo.");
		    } else {
		        System.out.println("Errore nell'aggiornamento del profilo.");
		    }
	}
	
	// GETTER AND SETTER
	public Cliente getClienteConnesso() {
		return clienteConnesso;
	}

	public void setClienteConnesso(Cliente clienteConnesso) {
		this.clienteConnesso = clienteConnesso;
	}
	
	
}
