package it.unipv.ingsfw.bitebyte.models;

import java.time.LocalDate;

/**
 * La classe Cliente rappresenta un utente di tipo cliente nel sistema BiteByte.
 * Estende la classe astratta {@code Utente} e aggiunge l'attributo
 * {@code username}.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 */
public class Cliente extends Utente {

//ATTRIBUTI
	private String username;

	/**
	 * Costruttore parametrizzato che eredita i parametri della classe astratta
	 * Utente.
	 * 
	 * @param cf       Codice fiscale del cliente.
	 * @param nome     Nome del cliente.
	 * @param cognome  Cognome del cliente.
	 * @param email    Email del cliente.
	 * @param password Password dell'account cliente.
	 * @param dataN    Data di nascita del cliente.
	 * @param username Username scelto dal cliente.
	 */

	public Cliente(String cf, String nome, String cognome, String email, String password, LocalDate dataN,
			String username) {
		super(cf, nome, cognome, email, password, dataN);
		this.username = username;

	}

	public Cliente(String cf, String nome, String cognome, String username, String password) {
		super(cf, nome, cognome, password);
		this.username = username;
	}

	// GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
