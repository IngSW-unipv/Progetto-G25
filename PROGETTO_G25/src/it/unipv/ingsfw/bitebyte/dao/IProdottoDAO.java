package it.unipv.ingsfw.bitebyte.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;

/**
 * Interfaccia per la gestione delle operazioni riguardanti i prodotti nel sistema.
 * Definisce i metodi per recuperare i prodotti in base alla categoria e all'inventario.
 */
public interface IProdottoDAO {
    
    /**
     * Recupera una lista di prodotti che appartengono alla categoria specificata e che non sono presenti
     * nell'inventario corrente.
     * 
     * @param stock Lo stock che rappresenta l'inventario corrente.
     * @param categoria La categoria del prodotto da filtrare.
     * @return Una lista di prodotti che soddisfano i criteri di categoria e non presenti nell'inventario.
     * @throws SQLException Se si verifica un errore durante il recupero dei dati dal database.
     */
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) throws SQLException;
}
