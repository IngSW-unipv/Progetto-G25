package it.unipv.ingsfw.bitebyte.dao;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.types.Categoria;
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
	                    Categoria.fromDatabaseValue(rs.getString("Categoria_P"))
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
	        String query = "UPDATE stock_dettagli SET Q_disp = ?  WHERE ID_Inventario = ? AND ID_Prodotto = ?";
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
	    
	    
	    

	
	}


