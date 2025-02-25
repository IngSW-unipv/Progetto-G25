package it.unipv.ingsfw.bitebyte.services;

import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.dao.FornituraDAO;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;

/**
 * Classe di Servizio per gestire le operazioni relative alle forniture,
 * specie in termini di interazioni col database.
 */
public class FornituraService {
    
    private FornituraDAO fornituraDAO;
    
    /**
     * Costruttore che inizializza il DAO delle forniture che realizza
     * le operazioni sul DB.
     */
    public FornituraService() {
        this.fornituraDAO = new FornituraDAO();
    }
    
    /**
     * Ottiene le informazioni sui fornitori per un determinato stock mediante
     * chiamata al DAO che realizza l'operazione sul database.
     * 
     * @param stock L'oggetto Stock per cui recuperare i fornitori.
     * @return Una lista di oggetti 'Fornitura' contenenti le informazioni sui fornitori.
     */
    public ArrayList<Fornitura> getFornitoriInfo(Stock stock) {
        return fornituraDAO.getFornitoriInfo(stock);
    }
}
