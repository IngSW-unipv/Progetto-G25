package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdineDAO {

    private Connection connection;
    private String schema;

    // Costruttore per usare una connessione esistente e specificare il database
    public OrdineDAO() {
		super();
		this.schema = "progettog25";
	}

    public boolean inserisciOrdine(Ordine ordine) {
        String query = "INSERT INTO " + schema + ".Ordine (ID_Ordine, data_ord,Totale,Stato, ID_Prodotto, Cf) " +
                       "VALUES (?, ?, ?, ?, ?,?)";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);

            stmt.setString(1, ordine.getIdOrdine());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(ordine.getDataOrd()));
            stmt.setString(3, ordine.getStatoOrd().name());
            stmt.setBigDecimal(4, ordine.getTotale());
            stmt.setString(5, ordine.getCliente().getCf());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'ordine: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Chiude il PreparedStatement (la connessione viene gestita esternamente)
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
    }
}
