package it.unipv.ingsfw.bitebyte.dao;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.types.Categoria;

import java.math.BigDecimal;
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
	        connection = DBConnection.startConnection(connection, schema);

	        String query = "SELECT s.ID_Inventario, s.Q_disp, s.Qmax_inseribile, s.Stato, " + 
	                "p.ID_Prodotto, p.Nome_p, p.Prezzo, p.Categoria_P " +  
	                "FROM stock_dettagli s " +
	                "JOIN prodotto p ON s.ID_Prodotto = p.ID_Prodotto " +
	                "WHERE s.ID_Inventario = ?";


	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, inventarioId);
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                Prodotto prodotto = new Prodotto(
	                    rs.getInt("ID_Prodotto"),
	                    rs.getString("Nome_p"),
	                    rs.getBigDecimal("Prezzo"),
	                    Categoria.valueOf(rs.getString("Categoria_P").replace(" ", "_").toUpperCase())
	                );

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
	    
	    
	    
	    public ArrayList<Stock> getStockByProdotto(int idProdotto) {
	        connection = DBConnection.startConnection(connection, schema);

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
	                Prodotto prodotto = new Prodotto(
	                    rs.getInt("ID_Prodotto"),
	                    rs.getString("Nome_p"),
	                    rs.getBigDecimal("Prezzo"),
	                    Categoria.valueOf(rs.getString("Categoria_P").replace(" ", "_").toUpperCase())
	                );

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

	   

	    @Override
	    public void updateStock(Stock stock) {
	        connection = DBConnection.startConnection(connection, schema);
	        String query = "UPDATE stock_dettagli SET Q_disp = ? WHERE ID_Inventario = ? AND ID_Prodotto = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setInt(1, stock.getQuantitaDisp());  // Aggiorniamo la quantità disponibile
	            stmt.setInt(2, stock.getIdInventario());
	            stmt.setInt(3, stock.getProdotto().getIdProdotto());  // Aggiunto ID del prodotto
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            DBConnection.closeConnection(connection);
	        }
	    }
	    
	    
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

	
	}


