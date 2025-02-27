package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;

import java.util.List;

public class OrdineService {

    private final OrdineDAO ordineDAO;

    // Dependency Injection tramite costruttore
    public OrdineService(OrdineDAO ordineDAO) {
        this.ordineDAO = ordineDAO;
    }

    public boolean creaOrdine(Ordine ordine) {
        if (ordine == null) return false;
        return ordineDAO.inserisciOrdine(ordine);
    }

    public List<Ordine> getOrdiniPerCliente(Cliente cliente) {
        if (cliente == null) return null;
        return ordineDAO.getOrdiniByCliente(cliente);
    }
}
