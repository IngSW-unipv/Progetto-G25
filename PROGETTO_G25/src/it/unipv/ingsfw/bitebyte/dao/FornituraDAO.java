package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Fornitore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.models.Prodotto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

/**
 * La classe FornituraDAO fornisce metodi per interagire con il database riguardo le forniture.
 * In particolare, permette di ottenere le informazioni relative ai fornitori e ai prodotti associati a un dato stock.
 */
public class FornituraDAO implements IFornituraDAO {

    private Connection connection; // Connessione al database
    private String schema; // Schema del database

    /**
     * Costruttore della classe FornituraDAO che inizializza lo schema del database.
     */
    public FornituraDAO() {
        super();
        this.schema = "progettog25"; // Imposta lo schema del database
    }

    /**
     * Recupera le informazioni relative ai fornitori e ai prodotti associati a un determinato stock.
     * 
     * @param stock L'oggetto Stock per cui recuperare le informazioni dei fornitori e dei prodotti.
     * @return Un ArrayList di oggetti Fornitura contenenti le informazioni sui fornitori e i prodotti associati.
     */
    public ArrayList<Fornitura> getFornitoriInfo(Stock stock) {
        ArrayList<Fornitura> fornitura = new ArrayList<>();
        connection = DBConnection.startConnection(connection, schema);

        // Query SQL per recuperare le informazioni su fornitori e prodotti
        String query = "SELECT ID_Prodotto, Nome_p, Prezzo, Categoria_P, " +
                "ID_Fornitore, Nome_F, Citta, Via, N_Civico, ppu " +
                "FROM (fornitore NATURAL JOIN fornitura) " +
                "NATURAL JOIN prodotto " +
                "WHERE ID_Prodotto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, stock.getProdotto().getIdProdotto());
            ResultSet rs = stmt.executeQuery();
            // Elaborazione dei risultati
            while (rs.next()) {
                // Creazione dell'oggetto Prodotto
                Prodotto prodotto = new Prodotto(
                        rs.getInt("ID_Prodotto"),
                        rs.getString("Nome_p"),
                        rs.getBigDecimal("Prezzo"),
                        Categoria.fromDatabaseValue(rs.getString("Categoria_P"))
                );
                // Creazione dell'oggetto Fornitore
                Fornitore fornitore = new Fornitore(
                        rs.getInt("ID_Fornitore"),
                        rs.getString("Nome_f"),
                        rs.getString("Citta"),
                        rs.getString("Via"),
                        rs.getString("N_Civico")
                );
                // Recupero del prezzo unitario
                BigDecimal prezzo = rs.getBigDecimal("ppu");
                // Creazione dell'oggetto Fornitura e aggiunta alla lista
                Fornitura f = new Fornitura(prodotto, fornitore, prezzo);
                fornitura.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return fornitura;
    }
}
