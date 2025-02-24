package it.unipv.ingsfw.bitebyte.service;

import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public double getSaldo(Cliente cliente) {
        // Recupera il saldo utilizzando il codice fiscale
        return clienteDAO.getSaldo(cliente.getCf());
    }
    
    public boolean saldoSufficiente(Cliente cliente, BigDecimal prezzoProdotto) {
        double saldoCliente = getSaldo(cliente); // saldo come double
        BigDecimal saldoClienteBigDecimal = BigDecimal.valueOf(saldoCliente); // converto in BigDecimal

        // Confronto i due valori
        return saldoClienteBigDecimal.compareTo(prezzoProdotto) >= 0; // ritorna true se saldo è sufficiente
    }
}