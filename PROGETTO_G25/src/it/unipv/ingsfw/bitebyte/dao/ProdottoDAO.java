package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;

/**
 * La classe ProdottoDAO fornisce metodi per interagire con il database riguardo i prodotti.
 * Contiene metodi per recuperare i prodotti in base a categorie specifiche ed escludere quelli 
 * già presenti in un determinato inventario.
 */
public class ProdottoDAO implements IProdottoDAO {
    
    private Connection connection; // Connessione al database
    private String schema; // Schema del database

    /**
     * Costruttore della classe ProdottoDAO che inizializza lo schema del database.
     */
    public ProdottoDAO() {
        super();
        this.schema = "progettog25"; 
    }

    /**
     * Recupera i prodotti appartenenti a una determinata categoria, escludendo quelli già presenti in un dato stock.
     * 
     * @param stock L'oggetto Stock che contiene l'ID dell'inventario da cui escludere i prodotti.
     * @param categoria La categoria dei prodotti da recuperare.
     * @return Un ArrayList di oggetti Prodotto che appartengono alla categoria e non sono già presenti nello stock.
     */
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) {
        ArrayList<Prodotto> prodottiSostituti = new ArrayList<>();

        // Connessione al database
        connection = DBConnection.startConnection(connection, schema);

        // Query per recuperare i prodotti della categoria specificata, escludendo quelli già nel database dello stock
        String query = "SELECT * FROM prodotto WHERE Categoria_P = ? AND ID_Prodotto NOT IN (SELECT ID_Prodotto FROM stock_dettagli WHERE ID_Inventario = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String categoriaString = categoria.name().replace("_", " ").toLowerCase();
            stmt.setString(1, categoriaString);
            stmt.setInt(2, stock.getIdInventario());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Creazione di un oggetto Prodotto per ogni risultato
                Prodotto prodotto = new Prodotto(
                    rs.getInt("ID_Prodotto"),
                    rs.getString("Nome_p"),
                    rs.getBigDecimal("Prezzo"),
                    Categoria.valueOf(rs.getString("Categoria_P").replace(" ", "_").toUpperCase())
                );
                // Aggiunta del prodotto alla lista
                prodottiSostituti.add(prodotto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection); 
        }
        return prodottiSostituti;
    }
}
