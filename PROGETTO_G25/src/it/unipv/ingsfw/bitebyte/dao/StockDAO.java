package it.unipv.ingsfw.bitebyte.dao;

import java.sql.*;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class StockDAO implements IStockDAO {
	
	    private Connection connection;
	    private String schema; 

	    // Costruttore
	    public StockDAO() {
	        super();
	        this.schema = "progettog25";
	    }

	    @Override
	    public ArrayList<Stock> getStockByInventario(int inventarioId) {
	        ArrayList<Stock> stocks = new ArrayList<>();
	        connection = DBConnection.startConnection(connection,schema);
	        String query = "SELECT * FROM stock_dettagli natural join prodotto WHERE ID_Inventario = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, inventarioId);        // Imposta l'ID del distributore
	            ResultSet rs = stmt.executeQuery();  // Esegue la query
	            while (rs.next()) {
	                Stock stock = new Stock(
	                    rs.getInt("idInventario"),
	                    rs.getInt("quantitàDisp"),
	                    rs.getInt("qMaxInseribile"),
	                    rs.getInt("prodottoId")  // Aggiunge l'ID del prodotto
	                    
	                );
	                stocks.add(stock);
	            }
	        } catch (SQLException e) {
	            System.err.println("Errore durante il recupero dello stock: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	DBConnection.closeConnection(connection);
	        }
	        return stocks;
	    }

	    @Override
	    public void addStock(Stock stock) {
	        connection = DBConnection.startConnection(connection,schema);
	        String query = "INSERT INTO stock_dettagli (idInventario, quantitàDisp, qMaxInseribile, prodottoId) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, stock.getIdInventario());
	            stmt.setInt(2, stock.getQuantitàDisp());
	            stmt.setInt(3, stock.getqMaxInseribile());
	            stmt.setInt(4, stock.getProdottoId());
	            stmt.executeUpdate();  // Esegui l'operazione di insert
	        } catch (SQLException e) {
	            System.err.println("Errore durante l'inserimento dello stock: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	DBConnection.closeConnection(connection);
	        }
	  
	    }

	    @Override
	    public void updateStock(Stock stock) {
	    	connection = DBConnection.startConnection(connection,schema);
	        String query = "UPDATE stock_dettagli SET quantitàDisp = ?, qMaxInseribile = ? WHERE idInventario = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, stock.getQuantitàDisp());
	            stmt.setInt(2, stock.getqMaxInseribile());
	            stmt.setInt(3, stock.getIdInventario());
	            stmt.executeUpdate();  // Esegui l'operazione di update
	        } catch (SQLException e) {
	            System.err.println("Errore durante l'aggiornamento dello stock: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	DBConnection.closeConnection(connection);
	        }
	   
	    }

	    @Override
	    public void deleteStock(int idInventario) {
	    	connection = DBConnection.startConnection(connection,schema);
	        String query = "DELETE FROM stock_dettagli WHERE idInventario = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, idInventario);
	            stmt.executeUpdate();  // Esegui l'operazione di delete
	        } catch (SQLException e) {
	            System.err.println("Errore durante l'eliminazione dello stock: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	DBConnection.closeConnection(connection);
	        }
	      
	    }

	
	}


