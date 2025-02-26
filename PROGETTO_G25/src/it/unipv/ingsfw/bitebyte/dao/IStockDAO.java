package it.unipv.ingsfw.bitebyte.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Stock;

/**
 * Interfaccia per la gestione delle operazioni riguardanti gli stock nel sistema.
 * Definisce i metodi per aggiungere, aggiornare, eliminare e recuperare gli stock dal database.
 */
public interface IStockDAO {

    /**
     * Aggiunge un nuovo stock nel database.
     * 
     * @param stock Lo stock da aggiungere.
     * @throws SQLException Se si verifica un errore durante l'inserimento nel database.
     */
    void addStock(Stock stock) throws SQLException;

    /**
     * Aggiorna uno stock esistente nel database.
     * 
     * @param stock Lo stock da aggiornare con i nuovi valori.
     * @throws SQLException Se si verifica un errore durante l'aggiornamento nel database.
     */
    void updateStock(Stock stock) throws SQLException;

    /**
     * Elimina uno stock dal database in base all'ID inventario.
     * 
     * @param idInventario L'ID dell'inventario dello stock da eliminare.
     * @throws SQLException Se si verifica un errore durante l'eliminazione nel database.
     */
    void deleteStock(int idInventario) throws SQLException;

    /**
     * Recupera uno stock specifico dal database tramite l'ID dell'inventario.
     * 
     * @param idInventario L'ID dell'inventario dello stock da recuperare.
     * @return Una lista di stock corrispondenti all'ID inventario.
     * @throws SQLException Se si verifica un errore durante il recupero dei dati dal database.
     */
    ArrayList<Stock> getStockByInventario(int idInventario) throws SQLException;
}
