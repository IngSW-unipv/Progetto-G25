package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;

public class PortafoglioService {

	// per generare l'id portafoglio
	public static String generaIdCasuale() {
		int numero = (int) (Math.random() * 9999) + 1000;
		return String.valueOf(numero);
	}

	public static void creaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente ) {
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO();
		portafoglioDAO.creaPortafoglio(portafoglio, cliente);
		
	}
	

	public static void aggiornaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente ) {
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO();
		portafoglioDAO.aggiornaPortafoglio(portafoglio, cliente);
		
	}
	
	
	
}
