package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import java.util.List;

public interface IOrdineDAO {
    boolean inserisciOrdine(Ordine ordine);
    List<Ordine> getOrdiniByCliente(Cliente cliente);
}
