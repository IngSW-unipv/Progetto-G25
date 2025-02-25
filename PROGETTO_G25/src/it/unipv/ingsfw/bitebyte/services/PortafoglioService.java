package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;

public class PortafoglioService {

    private PortafoglioVirtualeDAO portafoglioDAO;

    public PortafoglioService() {
        this.portafoglioDAO = new PortafoglioVirtualeDAO();  // Istanza di PortafoglioDAO
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