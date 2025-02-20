package it.unipv.ingsfw.bitebyte.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Fornitore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.models.Prodotto;

public interface IFornituraDAO {
	
	ArrayList<Fornitura> getFornitoriInfo(Stock stock)throws SQLException;

}
