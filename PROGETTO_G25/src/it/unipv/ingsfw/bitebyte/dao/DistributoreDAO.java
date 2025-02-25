

package it.unipv.ingsfw.bitebyte.dao;


import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DistributoreDAO implements IDistributoreDAO {
    
    private Connection connection;
    private String schema;
    private StockDAO stockDAO;

    public DistributoreDAO() {
        super();
        this.schema = "progettog25";  // Cambia se necessario
        this.stockDAO = new StockDAO();  // Istanza di StockDAO
    }

    @Override
    public Distributore getDistributoreById(int idDistributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "SELECT * FROM distributore WHERE ID_Distributore = ?";
        Distributore distributore = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idDistributore);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                distributore = new Distributore(
                        rs.getInt("ID_Distributore"),
                        rs.getString("Tipo_D"),
                        rs.getString("Citta"),
                        rs.getString("Via"),
                        rs.getString("N_civico"),
                        rs.getInt("ID_Inventario"),
                        rs.getDouble("LAT"),
                        rs.getDouble("LON")
                );

                // Recupera e associa gli stock tramite l'ID_Inventario
                List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
                distributore.setStockList(stockList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return distributore;
    }

    
    @Override
    public List<Distributore> getAllDistributori() {
        connection = DBConnection.startConnection(connection, schema);
        String query = "SELECT * FROM distributore";
        List<Distributore> distributori = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Distributore distributore = new Distributore(
                        rs.getInt("ID_Distributore"),
                        rs.getString("Tipo_D"),
                        rs.getString("Citta"),
                        rs.getString("Via"),
                        rs.getString("N_civico"),
                        rs.getInt("ID_Inventario"),
                        rs.getDouble("LAT"),
                        rs.getDouble("LON")
                );
                List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
                distributore.setStockList(stockList);
                distributori.add(distributore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return distributori;
    }

  
    
    @Override
    public void addDistributore(Distributore distributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "INSERT INTO distributore (ID_Distributore, Tipo_D , Citta, Via,  N_civico, ID_Inventario, LAT, LON) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	
        	stmt.setInt(1, distributore.getIdDistr());
        	stmt.setString(2, distributore.getTipo());
            stmt.setString(3, distributore.getCitta());
            stmt.setString(4, distributore.getVia());
            stmt.setString(5, distributore.getNCivico());
            stmt.setInt(6, distributore.getIdInventario());
            stmt.setDouble(7, distributore.getLat());
            stmt.setDouble(8, distributore.getLon());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public void updateDistributore(Distributore distributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "UPDATE distributore SET ID_Distributore = ?, Tipo_D = ?, Citta = ?, Via = ?, N_civico = ?, ID_Inventario = ?, LAT = ?, LON = ? WHERE ID_Distributore = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setInt(1, distributore.getIdDistr());
        	stmt.setString(2, distributore.getTipo().toString());
            stmt.setString(3, distributore.getCitta());
            stmt.setString(4, distributore.getVia());
            stmt.setString(5, distributore.getNCivico());
            stmt.setInt(6, distributore.getIdInventario());
            stmt.setDouble(7, distributore.getLat());
            stmt.setDouble(8, distributore.getLon());
            stmt.executeUpdate();

            // Dopo aver aggiornato, aggiorna ogni singolo stock
            for (Stock stock : distributore.getStockList()) {
                stockDAO.updateStock(stock); // Utilizza il metodo per aggiornare lo stock
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }


    @Override
    public void deleteDistributore(int idDistributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "DELETE FROM distributore WHERE ID_Distributore = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idDistributore);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

 
  
}

