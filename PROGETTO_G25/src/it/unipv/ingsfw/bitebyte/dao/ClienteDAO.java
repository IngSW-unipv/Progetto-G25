package it.unipv.ingsfw.bitebyte.dao;
import java.sql.*;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public class ClienteDAO implements IClienteDAO{

	    private Connection connection;
	    private String schema;
	    
	    public ClienteDAO() {
	        super();
	        this.schema = "progettog25";
	    }
	        
	    public boolean esisteUsername(String username) {
	    	connection = DBConnection.startConnection(connection, schema);
	    	String query = "SELECT COUNT(*) FROM cliente WHERE username = '?' ";
	    	
	    	try {
	    		PreparedStatement ps = connection.prepareStatement(query);
	    		ps.setString(1, username);
	    		ResultSet rs = ps.executeQuery();
	    		if (rs.next()) {
	    			return rs.getInt(1) > 0;
	    		} 
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} finally {
        	DBConnection.closeConnection(connection);
	    	}
	    	
	    	return false;
	    }
	    
	    public boolean esisteCliente(String email) {
	    	connection = DBConnection.startConnection(connection, schema);
	    	String query = "SELECT COUNT(*) FROM cliente WHERE email = '?' ";
	    	try {
	    		PreparedStatement ps = connection.prepareStatement(query);
	    		ps.setString(1, email);
	    		ResultSet rs = ps.executeQuery();
	    		if (rs.next()) {
	    			return rs.getInt(1) > 0;
	    		} 
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} finally {
        	DBConnection.closeConnection(connection);
	    	}
	    	return false;
	    }  
	    
	    @Override
	    public void registraCliente(Cliente cliente) {
	        connection = DBConnection.startConnection(connection, schema);
	        String query = "INSERT INTO cliente (username, nome, cognome, email, password) VALUES (?, ?, ?, ?, ?)";
	    	
	    	try {
	            PreparedStatement ps = connection.prepareStatement(query);
	            ps.setString(1, cliente.getUsername());
	            ps.setString(2, cliente.getNome());
	            ps.setString(3, cliente.getCognome());
	            ps.setString(4, cliente.getEmail());
	            ps.setString(5, cliente.getPassword());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	        	System.err.println("Errore durante l'inserimento del cliente: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	DBConnection.closeConnection(connection);
	        }
	    	
	    }
	    
	    @Override
	    public boolean verificaLogin(String username, String password) {
	    	connection = DBConnection.startConnection(connection, schema);
	        String query = "SELECT * FROM cliente WHERE username = '?' and password = '?' ";
	        try {
	    		PreparedStatement ps = connection.prepareStatement(query);
	    		ps.setString(1, username);
	    		ps.setString(2, password);
	    		ResultSet rs = ps.executeQuery();
	    		return rs.next();
	    		
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} finally {
        	DBConnection.closeConnection(connection);
	    	}
	    	
	    	return false;
	    }
	    
	}
