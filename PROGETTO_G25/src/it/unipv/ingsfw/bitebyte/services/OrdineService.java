package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.IOrdineDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;

import java.util.List;

public class OrdineService implements IOrdineService {

    private final IOrdineDAO ordineDAO;

    // Dependency Injection tramite costruttore
    public OrdineService(IOrdineDAO ordineDAO) {
        this.ordineDAO = ordineDAO;
    }

    @Override
    public boolean creaOrdine(Ordine ordine) {
        if (ordine == null) return false;
        return ordineDAO.inserisciOrdine(ordine);
    }

    @Override
    public List<Ordine> getOrdiniPerCliente(Cliente cliente) {
        if (cliente == null) return null;
        return ordineDAO.getOrdiniByCliente(cliente);
    }
}
