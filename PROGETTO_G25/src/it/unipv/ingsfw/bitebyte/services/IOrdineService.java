package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import java.util.List;

public interface IOrdineService {
    List<Ordine> getOrdiniPerCliente(Cliente cliente);
    boolean creaOrdine(Ordine ordine);

}
