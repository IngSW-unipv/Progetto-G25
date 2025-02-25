

package it.unipv.ingsfw.bitebyte.dao;


import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DistributoreDAO implements IDistributoreDAO {
    
    private Connection connection;
    private String schema;
    
    public DistributoreDAO() {
        super();
        this.schema = "progettog25";  // Cambia se necessario
       
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
        String query = "UPDATE distributore SET Tipo_D = ?, Citta = ?, Via = ?, N_civico = ?, ID_Inventario = ?, LAT = ?, LON = ? WHERE ID_Distributore = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, distributore.getTipo());
            stmt.setString(2, distributore.getCitta());
            stmt.setString(3, distributore.getVia());
            stmt.setString(4, distributore.getNCivico());
            stmt.setInt(5, distributore.getIdInventario());
            stmt.setDouble(6, distributore.getLat());
            stmt.setDouble(7, distributore.getLon());
            stmt.setInt(8, distributore.getIdDistr());
            
            stmt.executeUpdate();
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

