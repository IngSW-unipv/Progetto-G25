package it.unipv.ingsfw.bitebyte.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String CONFIG_FILE = "properties/dbconfig.properties"; // Percorso del file di configurazione

    // Metodo per ottenere la connessione
    public static Connection startConnection(Connection conn, String schema) {
        Properties props = new Properties();

        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            // Carica le proprietà dal file
            props.load(inputStream);

            String dbUrl = props.getProperty("db.url") + "/" + schema  + "?serverTimezone=Europe/Rome";
            String dbUsername = props.getProperty("db.username");
            String dbPassword = props.getProperty("db.password");
            String dbDriver = props.getProperty("db.driver");

            // Carica il driver JDBC
            Class.forName(dbDriver);

            // Se la connessione non è aperta, la creiamo
            if (isOpen(conn)) {
                closeConnection(conn);
            }

            // Crea una nuova connessione con i parametri letti dal file di configurazione
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("Connessione al database avvenuta con successo!");

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return conn;
    }

    // Metodo per verificare se la connessione è aperta
    public static boolean isOpen(Connection conn) {
        return conn != null;
    }

    // Metodo per chiudere la connessione
    public static Connection closeConnection(Connection conn) {
        if (!isOpen(conn)) {
            return null;
        }
        try {
            conn.close();
            conn = null;
            System.out.println("Connessione chiusa.");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }
}




/*
package it.unipv.ingsfw.bitebyte.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String CONFIG_FILE = "properties/dbconfig.properties"; // Percorso del file di configurazione

    // Metodo per ottenere la connessione
    public static Connection startConnection(Connection conn, String schema) {
        Properties props = new Properties();

        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            // Carica le proprietà dal file
            props.load(inputStream);

            String dbUrl = props.getProperty("db.url") + "/" + schema;
            String dbUsername = props.getProperty("db.username");
            String dbPassword = props.getProperty("db.password");
            String dbDriver = props.getProperty("db.driver");

            // Carica il driver JDBC
            Class.forName(dbDriver);

            // Se la connessione non è aperta, la creiamo
            if (isOpen(conn)) {
                closeConnection(conn);
            }

            // Crea una nuova connessione con i parametri letti dal file di configurazione
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("Connessione al database avvenuta con successo!");
            
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return conn;
    }

    // Metodo per verificare se la connessione è aperta
    public static boolean isOpen(Connection conn) {
        return conn != null;
    }

    // Metodo per chiudere la connessione
    public static Connection closeConnection(Connection conn) {
        if (!isOpen(conn)) {
            return null;
        }
        try {
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }
}

*/
