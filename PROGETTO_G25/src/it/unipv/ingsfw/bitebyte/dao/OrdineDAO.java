package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Ordine> getOrdiniByCliente(Cliente cliente) {
        List<Ordine> ordini = new ArrayList<>();
        if (cliente == null) {
            System.out.println("Errore: cliente nullo!");
            return ordini;
        }

        connection = DBConnection.startConnection(connection, schema);

        String query = "SELECT o.ID_Ordine, o.data_ord, o.Totale, o.Stato, o.ID_Prodotto, " +
                       "p.Nome_p, p.Prezzo, p.Categoria_P " +
                       "FROM " + schema + ".Ordine o " +
                       "JOIN " + schema + ".Prodotto p ON o.ID_Prodotto = p.ID_Prodotto " +
                       "WHERE o.Cf = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, cliente.getCf());

            rs = stmt.executeQuery();

            while (rs.next()) {
                // Crea l'oggetto Ordine
                Ordine ordine = new Ordine();
                ordine.setIdOrdine(rs.getString("ID_Ordine"));
                ordine.setDataOrd(rs.getTimestamp("data_ord").toLocalDateTime());
                ordine.setTotale(rs.getBigDecimal("Totale"));
                String statoString = rs.getString("Stato").toUpperCase(); // Converto la stringa in maiuscolo per rispettare i vincoli dell'enum
                ordine.setStatoOrd(StatoOrd.valueOf(statoString));
                
                
                // Crea l'oggetto Prodotto
                int idProdotto = rs.getInt("ID_Prodotto");
                String nome = rs.getString("Nome_p");
                BigDecimal prezzo = rs.getBigDecimal("Prezzo");
                String categoriaString = rs.getString("Categoria_P");
                Categoria categoria = Categoria.fromDatabaseValue(categoriaString);

                Prodotto prodotto = new Prodotto(idProdotto, nome, prezzo, categoria);

                // Associa il prodotto all'ordine
                ordine.setProdotto(prodotto);
                
                // Associa il cliente all'ordine
                ordine.setCliente(cliente);

                ordini.add(ordine);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione o dello statement: " + e.getMessage());
            }
        }

        return ordini;
    }
    public Categoria convertStringToCategoria(String categoriaDb) {
        switch (categoriaDb.toLowerCase()) {
            case "bevanda calda":
                return Categoria.BEVANDA_CALDA;
            case "bevanda fredda":
                return Categoria.BEVANDA_FREDDA;
            case "snack dolce":
                return Categoria.SNACK_DOLCE;
            case "snack salato":
                return Categoria.SNACK_SALATO;
            default:
                throw new IllegalArgumentException("Categoria non valida: " + categoriaDb);
        }
    }
}