package it.unipv.ingsfw.bitebyte.models;
import java.time.LocalDate;

/**
 * Classe che rappresenta un amministratore nel sistema BiteByte.
 * Estende la classe astratta {@link} Utente} e fornisce l'implementazione
 * dei metodi per accedere e modificare i dati dell'amministratore.
 * Gli amministratori hanno accesso alle funzionalità di gestione del sistema
 * come la gestione dei prodotti e degli utenti.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 *
 */
public class Amministratore extends Utente {

    /**
     * Costruttore che inizializza i dati dell'amministratore.
     *
     * @param cf Il codice fiscale dell'amministratore.
     * @param nome Il nome dell'amministratore.
     * @param cognome Il cognome dell'amministratore.
     * @param email L'indirizzo email dell'amministratore.
     * @param password La password dell'amministratore.
     * @param dataN La data di nascita dell'amministratore.
     */
    public Amministratore(String cf, String nome, String cognome, String email, String password, LocalDate dataN) {
        super(cf, nome, cognome, email, password, dataN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCf() {
        return super.getCf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCf(String cf) {
        super.setCf(cf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNome() {
        return super.getNome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCognome() {
        return super.getCognome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getDataN() {
        return super.getDataN();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataN(LocalDate dataN) {
        super.setDataN(dataN);
    }
}
