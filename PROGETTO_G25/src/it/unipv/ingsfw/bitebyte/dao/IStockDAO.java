package it.unipv.ingsfw.bitebyte.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Stock;


public interface IStockDAO {


	    // Aggiungi uno stock nel database
	    void addStock(Stock stock) throws SQLException;

	    // Aggiorna uno stock nel database
	    void updateStock(Stock stock) throws SQLException;

	    // Elimina uno stock dal database
	    void deleteStock(int idInventario) throws SQLException;

	    // Recupera uno stock specifico tramite ID inventario
	    ArrayList<Stock> getStockByInventario(int idInventario) throws SQLException;

	}


