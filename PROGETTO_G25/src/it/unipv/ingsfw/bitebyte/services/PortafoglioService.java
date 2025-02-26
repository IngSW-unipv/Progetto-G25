package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;

public class PortafoglioService {

	// Alice
	// per generare l'id portafoglio
	public static String generaIdCasuale() {
		int numero = (int) (Math.random() * 9999) + 1000;
		return String.valueOf(numero);
	}

	public static void creaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO();
		portafoglioDAO.creaPortafoglio(portafoglio, cliente);

	}

	public static void aggiornaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO();
		portafoglioDAO.aggiornaPortafoglio(portafoglio, cliente);

	}

	// Davide

	private PortafoglioVirtualeDAO portafoglioDAO;

	public PortafoglioService() {
		this.portafoglioDAO = new PortafoglioVirtualeDAO(); // Istanza di PortafoglioDAO
	}

	public double getSaldo(String codiceFiscale) {
		// Recupera il saldo dal database tramite PortafoglioDAO
		return portafoglioDAO.getSaldo(codiceFiscale);
	}

	public void aggiornaSaldo(String codiceFiscale, double nuovoSaldo) {
        // Aggiorna il saldo nel database tramite PortafoglioDAO
		portafoglioDAO.aggiornaSaldo(codiceFiscale, nuovoSaldo);
	}
	
	public int getIdPort(String codiceFiscale) {
		return portafoglioDAO.getIdPortByCliente(codiceFiscale);	
	}

}
