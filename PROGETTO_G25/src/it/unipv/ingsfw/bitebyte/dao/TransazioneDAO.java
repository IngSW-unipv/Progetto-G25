package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.bitebyte.models.Transazione;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public class TransazioneDAO {
    private Connection connection;
    private String schema;

    public TransazioneDAO() {
        this.schema = "progettog25"; 
    }

    public boolean inserisciTransazione(Transazione transazione, String idOrd, int idPort) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "INSERT INTO transazioni (ID_Trans, Esito, T_stamp, ID_Ord, ID_Port) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, transazione.getIdTransazione());
            stmt.setInt(2, transazione.isComplete() ? 1 : 0); // Conversione boolean -> int
            stmt.setTimestamp(3, transazione.getTimestamp());
            stmt.setString(4, idOrd); // ID_Ord dovrebbe essere passato come parametro
            stmt.setInt(5, idPort); // ID_Port dovrebbe essere passato come parametro

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return false;
    }
}
