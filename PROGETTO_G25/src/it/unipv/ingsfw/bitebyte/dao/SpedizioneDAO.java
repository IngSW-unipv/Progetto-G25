package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Spedizione;

public class SpedizioneDAO implements ISpedizioneDAO {
	
	 private Connection connection;
	 private String schema; 
	    
	 public SpedizioneDAO() {
       super();
       this.schema = "progettog25";
	 }
	 
	 public void salvaSpedizione(String idSpedizione, int idProdotto, int quantita, BigDecimal prezzotot) {
		 
		 if (!esisteSpedizione(idSpedizione)) {
	            nuovaSpedizione(idSpedizione);
	        }
		 
		 connection = DBConnection.startConnection(connection, schema);
		 String query = "INSERT INTO sped_dettagli (ID_Sped, ID_Prodotto, q_ord, prezzo_tot) VALUES (?, ?, ?, ?)";
		 try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, idSpedizione);
	            stmt.setInt(2, idProdotto);
	            stmt.setInt(3, quantita);
	            stmt.setBigDecimal(4, prezzotot);
	            stmt.executeUpdate();
		 } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            DBConnection.closeConnection(connection);
	        }
	 }	 
     
	 private void nuovaSpedizione(String idSpedizione) {
		 connection = DBConnection.startConnection(connection, schema);
		 String query = "INSERT INTO spedizione (ID_Sped, Data_sp) VALUES (?, ?)";
		 try (PreparedStatement stmt = connection.prepareStatement(query)) {
			 LocalDate datasp = LocalDate.now();
			 stmt.setString(1, idSpedizione);
	         stmt.setDate(2, Date.valueOf(datasp));
	         stmt.executeUpdate();
		 } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            DBConnection.closeConnection(connection);
	        }
	 }
	 
	 private boolean esisteSpedizione(String idSpedizione) {
		 connection = DBConnection.startConnection(connection, schema);
		 String query = "SELECT 1 FROM spedizione WHERE ID_Sped = ?";
		 try (PreparedStatement stmt = connection.prepareStatement(query)) {
			 stmt.setString(1, idSpedizione);
			 try (ResultSet rs = stmt.executeQuery()) {
	                return rs.next();
	            }
		 } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            DBConnection.closeConnection(connection);
	        } 
	 }
	 
	public ArrayList<Spedizione> getAllSpedizioni() {
		ArrayList<Spedizione> spedizioni = new ArrayList<>();
		connection = DBConnection.startConnection(connection, schema);
		String query = "SELECT sd.*, Data_sp FROM sped_dettagli sd NATURAL JOIN spedizione ORDER BY Data_sp";
		try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	Spedizione s = new Spedizione(
                  rs.getString("ID_Sped"),
                  rs.getInt("ID_Prodotto"),
                  rs.getInt("q_ord"),
                  rs.getBigDecimal("prezzo_tot"),
                  rs.getDate("Data_sp")
                  );
                spedizioni.add(s);	
            }
	   } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           DBConnection.closeConnection(connection);
       }
	  return spedizioni;
	}
	
	
}
