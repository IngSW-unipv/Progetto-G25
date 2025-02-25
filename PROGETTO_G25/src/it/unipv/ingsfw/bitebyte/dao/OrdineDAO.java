package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Ordine;

import java.sql.*;

public class OrdineDAO {

    private Connection connection;
    private String schema;

    // Costruttore per usare una connessione esistente e specificare il database
    public OrdineDAO() {
        this.schema = "progettog25";
    }

    public boolean inserisciOrdine(Ordine ordine) {
        // Verifica se l'ordine è null
        if (ordine == null) {
            throw new IllegalArgumentException("L'ordine non può essere null");
        }
        
        // Verifica se il cliente dell'ordine è null
        if (ordine.getCliente() == null) {
            throw new IllegalArgumentException("Il cliente dell'ordine non può essere null");
        }

        // Inizializza la connessione utilizzando il metodo di connessione del database
        connection = DBConnection.startConnection(connection, schema);

        String query = "INSERT INTO " + schema + ".Ordine (ID_Ordine, data_ord, Totale, Stato, ID_Prodotto, Cf) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);

            // Debugging: stampa i valori che stai cercando di inserire
            System.out.println("ordine ID: " + ordine.getIdOrdine());
            System.out.println("data: " + java.sql.Timestamp.valueOf(ordine.getDataOrd()));
            System.out.println("stato: " + ordine.getStatoOrd().name());
            System.out.println("totale: " + ordine.getTotale());

            // Imposta i parametri della query
            stmt.setString(1, ordine.getIdOrdine());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(ordine.getDataOrd()));
            stmt.setBigDecimal(3, ordine.getTotale());
            stmt.setString(4, ordine.getStatoOrd().name());  // Stato dell'ordine

            // Assicurati che ordine.getProdotto() ritorni un oggetto Prodotto e che getId() ritorni l'ID
            stmt.setInt(5, ordine.getProdotto().getIdProdotto());  // ID del prodotto
            stmt.setString(6, ordine.getCliente().getCf());   // Codice fiscale del cliente

            // Esegui l'inserimento nel database
            int rowsAffected = stmt.executeUpdate();

            // Restituisci true se l'inserimento ha avuto successo
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Gestione dell'errore
            System.err.println("Errore durante l'inserimento dell'ordine: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Chiude la connessione e lo statement
            try {
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione o dello statement: " + e.getMessage());
            }
        }
    }
}
