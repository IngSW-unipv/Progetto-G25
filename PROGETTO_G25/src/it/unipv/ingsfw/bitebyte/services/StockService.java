/**
 * Classe di servizio per la gestione dello stock.
 * Fornisce metodi per ottenere, aggiornare e sostituire gli elementi dello stock.
 */
package it.unipv.ingsfw.bitebyte.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class StockService {
    
    private StockDAO stockDAO;
    
    /**
     * Costruttore della classe StockService.
     * Inizializza un'istanza di StockDAO, che realizzerà effettivamente 
     * le operazioni di aggiornamento degli stock sul database.
     */
    public StockService() {
        this.stockDAO = new StockDAO();
    }
    
    /**
     * Ottiene la lista degli stock associati a un inventario specifico.
     * 
     * @param idInventario L'ID dell'inventario.
     * @return Una lista di oggetti Stock appartenenti all'inventario specificato.
     */
    public ArrayList<Stock> getStockByInventario(int idInventario) {
        return stockDAO.getStockByInventario(idInventario);
    }
    
    /**
     * Sostituisce un elemento dello stock con un altro prodotto.
     * 
     * @param stock L'oggetto Stock da sostituire.
     * @param idProdotto L'ID del nuovo prodotto.
     */
    public void sostituisciStock(Stock stock, int idProdotto) {
        stockDAO.sostituisciStock(stock, idProdotto);
    }
    
    /**
     * Aggiorna il prezzo di un prodotto in un determinato inventario.
     * 
     * @param idProdotto L'ID del prodotto da aggiornare.
     * @param idInventario L'ID dell'inventario.
     * @param nuovoPrezzo Il nuovo prezzo da assegnare al prodotto.
     * @return True se l'aggiornamento è avvenuto con successo, altrimenti false.
     */
    public boolean updatePrice(int idProdotto, int idInventario, BigDecimal nuovoPrezzo) {
        return stockDAO.updatePrice(idProdotto, idInventario, nuovoPrezzo);
    }
    
    /**
     * Ottiene la lista degli stock per un determinato prodotto.
     * 
     * @param idProdotto L'ID del prodotto.
     * @return Una lista di oggetti Stock associati al prodotto specificato.
     */
    public ArrayList<Stock> getStockByProdotto(int idProdotto) {
        return stockDAO.getStockByProdotto(idProdotto);
    }
    
    
    public void aggiornaQuantita(Stock stock) {
        stockDAO.updateStock(stock); // Chiama il DAO per aggiornare il DB
    }

    /**
     * Aggiorna un oggetto Stock nel database.
     * 
     * @param stock L'oggetto Stock aggiornato.
     */
    public void updateStock(Stock stock) {
        stockDAO.updateStock(stock);
    }
    
    public boolean erroreStato(Stock stock) {
        // cro un oggetto Random
        Random random = new Random();      
        // Estrai un numero casuale da 1 a 10
        int numeroCasuale = random.nextInt(10) + 1; // nextInt(10) genera un numero tra 0 e 9, quindi aggiungiamo 1
        // Se il numero estratto è 1, fai una determinata azione
        if (numeroCasuale == 1) {
        	stockDAO.setNonDisponibile(stock.getIdInventario(), stock.getProdotto().getIdProdotto());
        	return false;
        }
        return true;
    }
}
