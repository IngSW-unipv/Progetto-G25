package it.unipv.ingsfw.bitebyte.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public interface IProdottoDAO {
	
	//Ottengo i prodotti non presenti nell'inventario corrente filtrando la categoria del prodotto corrente
	public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) throws SQLException;

}
