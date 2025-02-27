package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe DAO per la gestione dell'entità Amministratore.
 * Fornisce metodi per l'accesso ai dati relativi agli amministratori nel database.
 */
public class AmministratoreDAO implements IAmministratoreDAO {

    private Connection connection;
    private String schema;
    
    /**
     * Costruttore di default.
     * Imposta lo schema predefinito del database.
     */
    public AmministratoreDAO() {
        super();
        this.schema = "progettog25";
    }
    
    /**
     * Verifica se esiste un amministratore con la password specificata.
     *
     * @param pass La password dell'amministratore da verificare.
     * @return true se esiste un amministratore con la password fornita, altrimenti false.
     */
    public boolean esisteAmministratore(String pass) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "SELECT * FROM " + schema + ".amministratore WHERE Passw = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Restituisce true se l'amministratore è presente
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(connection);
        }
    }
}
