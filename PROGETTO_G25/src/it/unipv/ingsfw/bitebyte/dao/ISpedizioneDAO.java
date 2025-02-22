package it.unipv.ingsfw.bitebyte.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface ISpedizioneDAO {
	public void salvaSpedizione(String idSpedizione, int idProdotto, int quantita, BigDecimal prezzotot) throws SQLException;

}
