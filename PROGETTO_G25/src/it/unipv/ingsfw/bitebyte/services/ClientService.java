package it.unipv.ingsfw.bitebyte.services;

import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public class ClientService {

    private ClienteDAO clienteDAO;
    private PortafoglioVirtualeDAO portafoglioDAO;

    public ClientService() {
        this.clienteDAO = new ClienteDAO();
        this.portafoglioDAO = new PortafoglioVirtualeDAO();
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
