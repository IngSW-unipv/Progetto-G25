package it.unipv.ingsfw.bitebyte.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Interfaccia per la gestione delle operazioni riguardanti le spedizioni nel sistema.
 * Definisce i metodi per salvare, verificare ed aggiungere nuove spedizioni.
 */
public interface ISpedizioneDAO {

    /**
     * Salva una nuova spedizione nel database, associando un prodotto e la quantità ad una spedizione.
     * 
     * @param idSpedizione L'identificativo della spedizione da salvare.
     * @param idProdotto L'identificativo del prodotto associato alla spedizione.
     * @param quantita La quantità del prodotto che viene spedita.
     * @param prezzotot Il prezzo totale della spedizione.
     * @throws SQLException Se si verifica un errore durante l'inserimento nel database.
     */
    public void salvaSpedizione(String idSpedizione, int idProdotto, int quantita, BigDecimal prezzotot) throws SQLException;

    /**
     * Verifica se una spedizione esiste già nel database.
     * 
     * @param idSpedizione L'identificativo della spedizione da verificare.
     * @return `true` se la spedizione esiste, `false` altrimenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public boolean esisteSpedizione(String idSpedizione) throws SQLException;

    /**
     * Crea una nuova spedizione nel database.
     * 
     * @param idSpedizione L'identificativo della nuova spedizione da creare.
     * @throws SQLException Se si verifica un errore durante l'inserimento nel database.
     */
    public void nuovaSpedizione(String idSpedizione) throws SQLException;
}
