package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Spedizione;

/**
 * La classe SpedizioneDAO fornisce metodi per interagire con il database riguardo le spedizioni.
 * Si occupa di salvare nuove spedizioni, verificare se una spedizione esiste e recuperare tutte le spedizioni
 * per prodotti riforniti da parte di un amministratore del sistema.
 */
public class SpedizioneDAO implements ISpedizioneDAO {

    private Connection connection; // Connessione al database
    private String schema; // Schema del database

    /**
     * Costruttore della classe SpedizioneDAO che inizializza lo schema del database.
     */
    public SpedizioneDAO() {
        super();
        this.schema = "progettog25"; 
    }

    /**
     * Salva una spedizione nel database. Se la spedizione non esiste, viene creata una nuova spedizione.
     * 
     * @param idSpedizione L'ID della spedizione.
     * @param idProdotto L'ID del prodotto associato alla spedizione.
     * @param quantita La quantità di prodotto da spedire.
     * @param prezzotot Il prezzo totale della spedizione.
     */
    public void salvaSpedizione(String idSpedizione, int idProdotto, int quantita, BigDecimal prezzoTot) {

        // Verifica se la spedizione esiste
        if (!esisteSpedizione(idSpedizione)) {
            // Se la spedizione non esiste, crea una nuova spedizione
            nuovaSpedizione(idSpedizione);
        }

        // Connessione al database
        connection = DBConnection.startConnection(connection, schema);

        String query = "INSERT INTO sped_dettagli (ID_Sped, ID_Prodotto, q_ord, prezzo_tot) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, idSpedizione);
            stmt.setInt(2, idProdotto);
            stmt.setInt(3, quantita);
            stmt.setBigDecimal(4, prezzoTot);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Crea una nuova spedizione nel database con la data corrente.
     * 
     * @param idSpedizione L'ID della nuova spedizione da creare.
     */
    public void nuovaSpedizione(String idSpedizione) {
        // Connessione al database
        connection = DBConnection.startConnection(connection, schema);

        String query = "INSERT INTO spedizione (ID_Sped, Data_sp) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            LocalDate datasp = LocalDate.now(); // Ottieni la data corrente
            stmt.setString(1, idSpedizione);
            stmt.setDate(2, Date.valueOf(datasp)); // Imposta la data
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Verifica se una spedizione esiste già nel database.
     * 
     * @param idSpedizione L'ID della spedizione da verificare.
     * @return true se la spedizione esiste, false altrimenti.
     */
    public boolean esisteSpedizione(String idSpedizione) {
        // Connessione al database
        connection = DBConnection.startConnection(connection, schema);

        String query = "SELECT 1 FROM spedizione WHERE ID_Sped = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, idSpedizione);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Restituisce true se la spedizione esiste
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Recupera tutte le spedizioni dal database.
     * 
     * @return Un ArrayList contenente tutte le spedizioni.
     */
    public ArrayList<Spedizione> getAllSpedizioni() {
        ArrayList<Spedizione> spedizioni = new ArrayList<>();
        
        // Connessione al database
        connection = DBConnection.startConnection(connection, schema);

        String query = "SELECT sd.*, Data_sp FROM sped_dettagli sd NATURAL JOIN spedizione ORDER BY Data_sp";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Crea un oggetto Spedizione per ogni record
                Spedizione s = new Spedizione(
                        rs.getString("ID_Sped"),
                        rs.getInt("ID_Prodotto"),
                        rs.getInt("q_ord"),
                        rs.getBigDecimal("prezzo_tot"),
                        rs.getDate("Data_sp")
                );
                spedizioni.add(s); // Aggiungi la spedizione alla lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return spedizioni; // Restituisce la lista delle spedizioni
    }
}
