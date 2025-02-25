package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;
import it.unipv.ingsfw.bitebyte.models.Ordine;

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
}
