package it.unipv.ingsfw.bitebyte.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe per la gestione della connessione al database. Contiene metodi per
 * aprire, chiudere e verificare lo stato della connessione al database. La
 * connessione viene configurata tramite un file di proprietà.
 */
public class DBConnection {

	/**
	 * Percorso del file di configurazione che contiene le informazioni per la
	 * connessione al database.
	 */
	private static final String CONFIG_FILE = "properties/dbconfig.properties";

	/**
	 * Metodo per ottenere una connessione al database. Carica le informazioni di
	 * configurazione dal file di proprietà e crea una connessione al database.
	 *
	 * @param conn   La connessione da verificare o creare.
	 * @param schema Il nome dello schema (database) da utilizzare.
	 * @return La connessione al database, o null se si è verificato un errore.
	 */
	public static Connection startConnection(Connection conn, String schema) {
		Properties props = new Properties();

		try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
			// Carica le proprietà dal file di configurazione
			props.load(inputStream);

			String dbUrl = props.getProperty("db.url") + "/" + schema + "?serverTimezone=Europe/Rome";
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

	/**
	 * Metodo per verificare se una connessione al database è aperta.
	 *
	 * @param conn La connessione da verificare.
	 * @return True se la connessione è aperta, false altrimenti.
	 */
	public static boolean isOpen(Connection conn) {
		return conn != null;
	}

	/**
	 * Metodo per chiudere la connessione al database. Se la connessione è aperta,
	 * verrà chiusa.
	 *
	 * @param conn La connessione da chiudere.
	 * @return La connessione chiusa o null se si è verificato un errore.
	 */
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
