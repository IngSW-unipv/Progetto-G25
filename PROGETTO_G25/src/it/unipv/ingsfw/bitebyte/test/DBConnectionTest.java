//FACTORY 
/* Singleton → Evita di creare più connessioni inutili.
 Factory Pattern → Centralizza la creazione della connessione.
 Incapsulamento → Il codice che usa il DB non deve preoccuparsi della gestione della connessione.
 Ottimizzazione → Se la connessione è già aperta, la riutilizza invece di crearne una nuova.
*/

package it.unipv.ingsfw.bitebyte.test;

import it.unipv.ingsfw.bitebyte.dao.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionTest{
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Avvia la connessione al database con lo schema 'bitebyte_schema'
            conn = DBConnection.startConnection(conn, "progettog25");

            // Verifica se la connessione è stata ottenuta correttamente
            if (conn != null) {
                System.out.println("Connessione al database avvenuta con successo!");
                
                // Esegui una semplice query per verificare che la connessione funzioni
                String query = "SELECT 1"; // Query semplice per testare la connessione
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                // Stampa il risultato della query
                if (rs.next()) {
                    System.out.println("La query è stata eseguita correttamente. Risultato: " + rs.getInt(1));
                }

                // Chiudi la connessione
                DBConnection.closeConnection(conn);
                System.out.println(" Connessione chiusa con successo!");
            } else {
                System.out.println("Connessione al database fallita.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Errore durante l'esecuzione della query.");
        } finally {
            // Assicurati che la connessione venga chiusa in ogni caso
            if (conn != null) {
                DBConnection.closeConnection(conn);
            }
        }
    }
}

