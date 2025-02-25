package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PortafoglioVirtualeDAO {

    private Connection connection;
    private String schema;

    // Costruttore per inizializzare il campo schema
    public PortafoglioVirtualeDAO() {
        super();
        this.schema = "progettog25"; // Nome del database schema
    }

    // Metodo per recuperare il saldo
    public double getSaldo(String codiceFiscale) {
        double saldo = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Connessione al database
            connection = DBConnection.startConnection(connection, schema);

            // Query SQL per recuperare il saldo
            String query = "SELECT p.Saldo " +
                           "FROM progettog25.portafoglio_virtuale p " +
                           "JOIN progettog25.cliente c ON c.Cf = p.Cf " +
                           "WHERE c.Cf = ?";

            // Prepara la query
            ps = connection.prepareStatement(query);
            ps.setString(1, codiceFiscale); // Imposta il codice fiscale come parametro

            // Esecuzione della query
            rs = ps.executeQuery();

            // Verifica se il risultato esiste e ottieni il saldo
            if (rs.next()) {
                saldo = rs.getDouble("Saldo");
            }
        } catch (SQLException e) {
            // Gestione dell'errore: stampa l'eccezione o rilancia
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero del saldo per il codice fiscale: " + codiceFiscale, e);
        } finally {
            // Chiusura delle risorse
            DBConnection.closeConnection(connection);
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return saldo;
    }

    public void aggiornaSaldo(String codiceFiscale, double nuovoSaldo) {
        String query = "UPDATE progettog25.portafoglio_virtuale SET saldo = ? WHERE Cf = ?";

        try (Connection connection = DBConnection.startConnection(null, schema);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, nuovoSaldo); // Imposta il nuovo saldo
            ps.setString(2, codiceFiscale); // Imposta il codice fiscale
            ps.executeUpdate(); // Esegui l'aggiornamento

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nell'aggiornamento del saldo per il codice fiscale: " + codiceFiscale, e);
        }
    }
}
