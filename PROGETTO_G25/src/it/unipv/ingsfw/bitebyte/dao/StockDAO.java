package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.models.Stock;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

/**
 * La classe StockDAO fornisce metodi per interagire con il database riguardo la gestione dello stock dei prodotti.
 * I metodi consentono di recuperare le informazioni sullo stock, aggiungere, aggiornare, eliminare stock e 
 * gestire altre operazioni relative.
 */
public class StockDAO implements IStockDAO {

    private Connection connection; // Connessione al database
    private String schema; // Schema del database

    /**
     * Costruttore della classe StockDAO che inizializza lo schema del database.
     */
    public StockDAO() {
        super();
        this.schema = "progettog25"; 
    }

    /**
     * Recupera tutti gli stock associati a un inventario specifico.
     * 
     * @param inventarioId L'ID dell'inventario per cui recuperare lo stock.
     * @return Un ArrayList di oggetti Stock associati all'inventario specificato.
     */
    @Override
    public ArrayList<Stock> getStockByInventario(int inventarioId) {
        ArrayList<Stock> stocks = new ArrayList<>();
        connection = DBConnection.startConnection(connection, schema);

        // Query SQL per recuperare lo stock associato all'inventario
        String query = "SELECT s.ID_Inventario, s.Q_disp, s.Qmax_inseribile, s.Stato, " +
                "p.ID_Prodotto, p.Nome_p, p.Prezzo, p.Categoria_P " +
                "FROM stock_dettagli s " +
                "JOIN prodotto p ON s.ID_Prodotto = p.ID_Prodotto " +
                "WHERE s.ID_Inventario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inventarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Creazione dell'oggetto Prodotto
                Prodotto prodotto = new Prodotto(
                        rs.getInt("ID_Prodotto"),
                        rs.getString("Nome_p"),
                        rs.getBigDecimal("Prezzo"),
                        Categoria.fromDatabaseValue(rs.getString("Categoria_P"))
                );

                // Creazione dell'oggetto Stock
                Stock stock = new Stock(
                        rs.getInt("ID_Inventario"),
                        rs.getInt("Q_disp"),
                        rs.getInt("Qmax_inseribile"),
                        rs.getString("Stato"),
                        prodotto
                );
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return stocks;
    }

    /**
     * Recupera tutti gli stock associati a un prodotto specifico.
     * 
     * @param idProdotto L'ID del prodotto per cui recuperare gli stock.
     * @return Un ArrayList di oggetti Stock associati al prodotto specificato.
     */
    public ArrayList<Stock> getStockByProdotto(int idProdotto) {
        connection = DBConnection.startConnection(connection, schema);

        // Query SQL per recuperare lo stock associato al prodotto
        String query = "SELECT s.ID_Inventario, s.Q_disp, s.Qmax_inseribile, s.Stato, " +
                       "p.ID_Prodotto, p.Nome_p, p.Prezzo, p.Categoria_P " +
                       "FROM stock_dettagli s " +
                       "JOIN prodotto p ON s.ID_Prodotto = p.ID_Prodotto " +
                       "WHERE s.ID_Prodotto = ? " +
                       "ORDER BY s.ID_Inventario";

        ArrayList<Stock> stocks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProdotto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Creazione dell'oggetto Prodotto
                Prodotto prodotto = new Prodotto(
                        rs.getInt("ID_Prodotto"),
                        rs.getString("Nome_p"),
                        rs.getBigDecimal("Prezzo"),
                        Categoria.fromDatabaseValue(rs.getString("Categoria_P"))
                );
                // Creazione dell'oggetto Stock
                Stock stock = new Stock(
                        rs.getInt("ID_Inventario"),
                        rs.getInt("Q_disp"),
                        rs.getInt("Qmax_inseribile"),
                        rs.getString("Stato"),
                        prodotto
                );

                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return stocks;
    }

    /**
     * Aggiunge un nuovo stock nel database.
     * 
     * @param stock L'oggetto Stock da aggiungere al database.
     */
    @Override
    public void addStock(Stock stock) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "INSERT INTO stock_dettagli (ID_Inventario, ID_Prodotto, Q_disp, Qmax_inseribile, Stato) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, stock.getIdInventario());
            stmt.setInt(2, stock.getProdotto().getIdProdotto());
            stmt.setInt(3, stock.getQuantitaDisp());
            stmt.setInt(4, stock.getQMaxInseribile());
            stmt.setString(5, stock.getStato());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Aggiorna le informazioni relative allo stock nel database.
     * 
     * @param stock L'oggetto Stock con le nuove informazioni da aggiornare.
     */
    @Override
    public void updateStock(Stock stock) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "UPDATE stock_dettagli SET Q_disp = ? WHERE ID_Inventario = ? AND ID_Prodotto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, stock.getQuantitaDisp());
            stmt.setInt(2, stock.getIdInventario());
            stmt.setInt(3, stock.getProdotto().getIdProdotto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Aggiorna il prezzo di un prodotto nello stock.
     * 
     * @param idProdotto L'ID del prodotto di cui aggiornare il prezzo.
     * @param idInventario L'ID dell'inventario in cui il prodotto è presente.
     * @param nuovoPrezzo Il nuovo prezzo del prodotto.
     * @return true se l'aggiornamento è riuscito, false altrimenti.
     */
    public boolean updatePrice(int idProdotto, int idInventario, BigDecimal nuovoPrezzo) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "UPDATE prodotto p JOIN stock_dettagli s ON s.ID_Prodotto = p.ID_Prodotto " +
                       "SET p.Prezzo = ? WHERE s.ID_Inventario = ? AND p.ID_Prodotto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBigDecimal(1, nuovoPrezzo);
            stmt.setInt(2, idInventario);
            stmt.setInt(3, idProdotto);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento del prezzo: " + e.getMessage());
            return false;

        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Elimina uno stock dal database.
     * 
     * @param idInventario L'ID dell'inventario dello stock da eliminare.
     */
    @Override
    public void deleteStock(int idInventario) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "DELETE FROM stock_dettagli WHERE ID_Inventario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idInventario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Sostituisce un prodotto all'interno di uno stock
     * Il prodotto che sostituisce presenterà una quantità azzerata,
     * in quanto deve essere ordinato per poter riempire lo stock.
     * 
     * @param stock L'oggetto Stock con le informazioni sullo stock da modificare.
     * @param idProdotto L'ID del nuovo prodotto da associare allo stock.
     */
    public void sostituisciStock(Stock stock, int idProdotto) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "UPDATE stock_dettagli SET ID_Prodotto = ?, Q_disp = ? WHERE ID_Inventario = ? AND ID_Prodotto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProdotto);
            stmt.setInt(2, 0);
            stmt.setInt(3, stock.getIdInventario());
            stmt.setInt(4, stock.getProdotto().getIdProdotto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }
    
    //metodo che implementa "per simulazione" degli errori
    public void setNonDisponibile(int idInventario, int idProdotto) {
        connection = DBConnection.startConnection(connection, schema);
        
        // Query SQL per aggiornare lo stato del prodotto a "Non Disponibile"
        String query = "UPDATE stock_dettagli SET Stato = ? WHERE ID_Inventario = ? AND ID_Prodotto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "Non Disponibile");  // Imposta lo stato a "Non Disponibile"
            stmt.setInt(2, idInventario);
            stmt.setInt(3, idProdotto);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Lo stato del prodotto è stato aggiornato a 'Non Disponibile'.");
            } else {
                System.out.println("Nessun prodotto trovato con l'ID specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }
    
}
