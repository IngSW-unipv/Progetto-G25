package it.unipv.ingsfw.bitebyte.dao;

/**
 * Interfaccia per il DAO dell'entità Amministratore.
 * Definisce i metodi per l'accesso ai dati relativi agli amministratori.
 */
public interface IAmministratoreDAO {

    /**
     * Verifica se esiste un amministratore con la password specificata.
     *
     * @param pass La password dell'amministratore da verificare.
     * @return true se esiste un amministratore con la password fornita, altrimenti false.
     */
    boolean esisteAmministratore(String pass);
}
