package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.payment.IPaymentAdapter;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

public class PortafoglioService {

	// Alice
	// per generare l'id portafoglio
	public static String generaIdCasuale() {
		int numero;
		PortafoglioVirtualeDAO pvd = new PortafoglioVirtualeDAO();
		do {
			numero = (int) (Math.random() * 9999) + 1000;
		} while (!pvd.isIdPortPresente(numero)); // Continua a rigenerare fino a trovare un ID unico

		return String.valueOf(numero);
	}

	int numero;
	PortafoglioVirtualeDAO pvd = new PortafoglioVirtualeDAO();

	// Rigenera l'ID finché non è unico

	public static void creaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO();
		portafoglioDAO.creaPortafoglio(portafoglio, cliente);

	}

	public static void aggiornaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		PortafoglioVirtualeDAO portafoglioDAO = new PortafoglioVirtualeDAO();
		System.out.println("Prova portafoglio");
		portafoglioDAO.aggiornaPortafoglio(portafoglio, cliente);

	}

	public void ricaricaPortafoglio(double importo, IPaymentAdapter paymentAdapter) {
		PortafoglioVirtuale portafoglio = getPortafoglioCliente();
		portafoglio.ricarica(importo, paymentAdapter);
		aggiornaPortafoglio(portafoglio, getClienteConnesso());

	}

	public Cliente getClienteConnesso() {
		return Sessione.getInstance().getClienteConnesso();
	}

	public PortafoglioVirtuale getPortafoglioCliente() {
		return Sessione.getInstance().getPortafoglioCliente();
	}

	public TipologiaPagamento getTipologiaPagamento() {
		return Sessione.getInstance().getPortafoglioCliente().getTipologiaPagamento();
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
