package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class ProdottoDAO implements IProdottoDAO {
	
	private Connection connection;
    private String schema; 

    // Costruttore
    public ProdottoDAO() {
        super();
        this.schema = "progettog25";
    }
    
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) {
    	ArrayList<Prodotto> prodottiSostituti = new ArrayList<>();
    	connection = DBConnection.startConnection(connection, schema);
    	
    	String query = "SELECT * FROM prodotto WHERE Categoria_P = ? AND ID_Prodotto NOT IN (SELECT ID_Prodotto FROM stock_dettagli WHERE ID_Inventario = ?)";
    	try (PreparedStatement stmt = connection.prepareStatement(query)) {
    		String categoriaString = categoria.name().replace("_", " ").toLowerCase();
    		stmt.setString(1, categoriaString);
    		stmt.setInt(2, stock.getIdInventario());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = new Prodotto(
                    rs.getInt("ID_Prodotto"),
                    rs.getString("Nome_p"),
                    rs.getBigDecimal("Prezzo"),
                    Categoria.valueOf(rs.getString("Categoria_P").replace(" ", "_").toUpperCase())
                );
            prodottiSostituti.add(prodotto);
            }
    	} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
     return prodottiSostituti; 	
    }
}
    	
    	

