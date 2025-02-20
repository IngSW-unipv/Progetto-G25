package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Fornitore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.models.Prodotto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class FornituraDAO implements IFornituraDAO {
	
	    private Connection connection;
	    private String schema; 

	    // Costruttore
	    public FornituraDAO() {
	        super();
	        this.schema = "progettog25";
	    }
	    
	   public ArrayList<Fornitura> getFornitoriInfo(Stock stock) {
	    	ArrayList<Fornitura> fornitura = new ArrayList<>();
	    	connection = DBConnection.startConnection(connection, schema);
	    	String query = "SELECT ID_Prodotto, Nome_p, Prezzo, Categoria_P, " +
                    "ID_Fornitore, Nome_F, Citta, Via, N_Civico, ppu " +
                    "FROM (fornitore NATURAL JOIN fornitura) " +
                    "NATURAL JOIN prodotto " +
                    "WHERE ID_Prodotto = ?";
	    	 
	    	try (PreparedStatement stmt = connection.prepareStatement(query)) {
		            stmt.setInt(1, stock.getProdotto().getIdProdotto());
		            
		            ResultSet rs = stmt.executeQuery();
		            
		            while (rs.next()) {
		                Prodotto prodotto = new Prodotto(
		                    rs.getInt("ID_Prodotto"),
		                    rs.getString("Nome_p"),
		                    rs.getBigDecimal("Prezzo"),
		                    Categoria.valueOf(rs.getString("Categoria_P").replace(" ", "_").toUpperCase())
		                );
		                Fornitore fornitore = new Fornitore(
		                	rs.getInt("ID_Fornitore"),
		                	rs.getString("Nome_f"),
		                	rs.getString("Citta"),
		                	rs.getString("Via"),
		                	rs.getString("N_Civico")
		                	);
		                BigDecimal prezzo = rs.getBigDecimal("ppu");
		             
		             Fornitura f = new Fornitura(prodotto, fornitore, prezzo);
		             fornitura.add(f);
		            }
		              } catch (SQLException e) {
			              e.printStackTrace();
			              } finally {
			                DBConnection.closeConnection(connection);
			                }
			   return fornitura;	
	        }
	    
	    
	    
}
