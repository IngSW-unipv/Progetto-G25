package it.unipv.ingsfw.bitebyte.models;

import java.time.LocalDate;

/**
 * Classe astratta che rappresenta un utente del sistema BiteByte.
 * L'utente può essere di diversi tipi, come cliente o amministratore.
 * Contiene le informazioni personali di base, come il codice fiscale, il nome, 
 * il cognome, l'email, la password e la data di nascita.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 * @see java.time.LocalDate
 * @see Cliente 
 * @see Amministratore 
 */
//Prova branch
public abstract class Utente {

    // Attributi
    private String cf;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private LocalDate dataN;

    /**
     * Costruttore parametrizzato per inizializzare i dati dell'utente.
     *
     * @param cf Il codice fiscale dell'utente.
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param email L'indirizzo email dell'utente.
     * @param password La password dell'utente.
     * @param dataN La data di nascita dell'utente.
     */
    public Utente(String cf, String nome, String cognome, String email, String password, LocalDate dataN) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataN = dataN;
    }

    /**
     * Restituisce il codice fiscale dell'utente.
     * 
     * @return Il codice fiscale dell'utente.
     */
    public String getCf() {
        return cf;
    }

    /**
     * Imposta il codice fiscale dell'utente.
     * 
     * @param cf Il nuovo codice fiscale dell'utente.
     */
    public void setCf(String cf) {
        this.cf = cf;
    }

    /**
     * Restituisce il nome dell'utente.
     * 
     * @return Il nome dell'utente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente.
     * 
     * @param nome Il nuovo nome dell'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     * 
     * @return Il cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente.
     * 
     * @param cognome Il nuovo cognome dell'utente.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'email dell'utente.
     * 
     * @return L'email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente.
     * 
     * @param email Il nuovo indirizzo email dell'utente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce la password dell'utente.
     * 
     * @return La password dell'utente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     * 
     * @param password La nuova password dell'utente.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce la data di nascita dell'utente.
     * 
     * @return La data di nascita dell'utente.
     */
    public LocalDate getDataN() {
        return dataN;
    }

    /**
     * Imposta la data di nascita dell'utente.
     * 
     * @param dataN La nuova data di nascita dell'utente.
     */
    public void setDataN(LocalDate dataN) {
        this.dataN = dataN;
    }
}

