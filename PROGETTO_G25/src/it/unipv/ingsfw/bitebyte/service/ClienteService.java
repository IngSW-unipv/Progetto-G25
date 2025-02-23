package it.unipv.ingsfw.bitebyte.service;

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
}