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
		
		// Verifica che il cliente esista
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
	
	/**
	 * Metodo per modificare il profilo del cliente.
	*/

	public void modificaProfilo( String nuovoNome, String nuovoCognome, String nuovoUsername, String nuovaPassword) {
		
		ClienteDAO clienteDAO = new ClienteDAO();

		// Salvataggio delle modifiche nel database
		boolean aggiornamentoRiuscito = clienteDAO.modificaProfilo(clienteConnesso);
		if (aggiornamentoRiuscito) {
			System.out.println("Profilo aggiornato con successo.");
			// Aggiornamento dei dati
			clienteConnesso.setNome(nuovoNome);
			clienteConnesso.setCognome(nuovoCognome);
			clienteConnesso.setUsername(nuovoUsername);
			clienteConnesso.setPassword(nuovaPassword);
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
