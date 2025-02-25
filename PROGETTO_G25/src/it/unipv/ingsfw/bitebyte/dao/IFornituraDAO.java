package it.unipv.ingsfw.bitebyte.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Fornitore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.models.Prodotto;

/**
 * Interfaccia per la gestione delle operazioni riguardanti le forniture nel sistema.
 */
public interface IFornituraDAO {
    
    /**
     * Ottiene le informazioni sulla fornitura di un prodotto specifico.
     * 
     * @param stock L'oggetto Stock che rappresenta il prodotto di cui si vogliono ottenere informazioni sulla fornitura.
     * @return Un ArrayList di oggetti Fornitura che contiene tutte le informazioni associate alla fornitura del prodotto.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    ArrayList<Fornitura> getFornitoriInfo(Stock stock) throws SQLException;
}
