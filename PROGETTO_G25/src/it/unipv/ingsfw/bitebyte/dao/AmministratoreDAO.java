package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoreDAO implements IAmministratoreDAO {

	private Connection connection;
	private String schema;
	
	
	public AmministratoreDAO() {
		super();
		this.schema="progettog25";
	}
	
	public boolean esisteAmministratore(String pass) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "SELECT * FROM " + schema + ".amministratore WHERE Passw  = ? ";
		
		 try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, pass);
	            try (ResultSet rs = stmt.executeQuery()) {
	                return rs.next(); //restituisce True se l'amministratore è presente
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            DBConnection.closeConnection(connection);	        
	        }
		
	}
	
}
