package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.PortafoglioDAO;

public class PortafoglioService {

    private PortafoglioDAO portafoglioDAO;

    public PortafoglioService() {
        this.portafoglioDAO = new PortafoglioDAO();  // Istanza di PortafoglioDAO
    }

    public double getSaldo(String codiceFiscale) {
        // Recupera il saldo dal database tramite PortafoglioDAO
        return portafoglioDAO.getSaldo(codiceFiscale);
    }

    public void aggiornaSaldo(String codiceFiscale, double nuovoSaldo) {
        // Aggiorna il saldo nel database tramite PortafoglioDAO
        portafoglioDAO.aggiornaSaldo(codiceFiscale, nuovoSaldo);
    }
}