package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;

import java.util.List;

public class OrdineService {

    private OrdineDAO ordineDAO;

    public OrdineService() {
        this.ordineDAO = new OrdineDAO();
    }

    public boolean creaOrdine(Ordine ordine) {
        if (ordine == null) {
            System.out.println("Errore: ordine nullo!");
            return false;
        }

        boolean inserito = ordineDAO.inserisciOrdine(ordine);
        if (inserito) {
            System.out.println("Ordine creato con successo: " + ordine.getIdOrdine());
        } else {
            System.out.println("Errore nell'inserimento dell'ordine.");
        }
        return inserito;
    }

    // Nuovo metodo per ottenere gli ordini di un cliente
    public List<Ordine> getOrdiniPerCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Errore: cliente nullo!");
            return null;
        }

        // Chiamata al DAO per recuperare gli ordini del cliente
        return ordineDAO.getOrdiniByCliente(cliente);
    }
}
