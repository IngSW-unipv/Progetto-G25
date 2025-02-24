package it.unipv.ingsfw.bitebyte.service;

import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public class ClienteService {

    private ClienteDAO clienteDAO;
    private PortafoglioDAO portafoglioDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
        this.portafoglioDAO = new PortafoglioDAO();
    }

    public double getSaldo(Cliente cliente) {
        // Recupera il saldo utilizzando il codice fiscale
        return portafoglioDAO.getSaldo(cliente.getCf());  // Usa l'istanza portafoglioDAO
    }
    
    public boolean saldoSufficiente(Cliente cliente, BigDecimal prezzoProdotto) {
        double saldoCliente = getSaldo(cliente); // saldo come double
        BigDecimal saldoClienteBigDecimal = BigDecimal.valueOf(saldoCliente); // converto in BigDecimal

        // Confronto i due valori
        return saldoClienteBigDecimal.compareTo(prezzoProdotto) >= 0; // ritorna true se saldo è sufficiente
    }
}
