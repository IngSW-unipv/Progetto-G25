package it.unipv.ingsfw.bitebyte.services;

import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;

//Alice
public class ClientService {

	private ClienteDAO clienteDAO;

	// Validazione completa del cliente
	public boolean isClienteValido(Cliente cliente) {
		return cliente != null && ValidationService.isNomeValido(cliente.getNome())
				&& ValidationService.isNomeValido(cliente.getNome())
				&& ValidationService.passwordValida(cliente.getPassword())
				&& isUsernameDisponibile(cliente.getUsername());
	}

	// Controlla se lo username è disponibile
	public boolean isUsernameDisponibile(String username) {
		if (username == null || username.isEmpty()) {
			return false;
		}
		return !clienteDAO.esisteUsername(username); // se non esiste lo username nel DB restituisce true
	}

	// Aggiorna il profilo del cliente
	public boolean aggiornaProfilo(Cliente cliente) {
		if (!isClienteValido(cliente)) {
			return false; // Profilo non valido
		}
		return clienteDAO.modificaProfilo(cliente); // Salva nel database
	}

	// Davide
	private PortafoglioVirtualeDAO portafoglioDAO;

	public ClientService() {
		this.clienteDAO = new ClienteDAO();
		this.portafoglioDAO = new PortafoglioVirtualeDAO();
	}

	public double getSaldo(Cliente cliente) {
		// Recupera il saldo utilizzando il codice fiscale
		return portafoglioDAO.getSaldo(cliente.getCf()); // Usa l'istanza portafoglioDAO
	}

	public boolean saldoSufficiente(Cliente cliente, BigDecimal prezzoProdotto) {
		double saldoCliente = getSaldo(cliente); // saldo come double
		BigDecimal saldoClienteBigDecimal = BigDecimal.valueOf(saldoCliente); // converto in BigDecimal

		// Confronto i due valori
		return saldoClienteBigDecimal.compareTo(prezzoProdotto) >= 0; // ritorna true se saldo è sufficiente
	}

}
