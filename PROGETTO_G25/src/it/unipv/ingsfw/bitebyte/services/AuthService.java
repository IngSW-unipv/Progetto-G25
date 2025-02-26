package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import it.unipv.ingsfw.bitebyte.dao.PortafoglioVirtualeDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;



public class AuthService {
    private final ClienteDAO clienteDAO;
    private final PortafoglioVirtualeDAO portafoglioDAO;

    public AuthService(ClienteDAO clienteDAO, PortafoglioVirtualeDAO portafoglioDAO) {
        this.clienteDAO = clienteDAO;
        this.portafoglioDAO = portafoglioDAO;
    }

    public boolean verificaLogin(String username, String password) {
        return clienteDAO.verificaLogin(username, password);
    }

    public Cliente login(String username, String password) {
        if (verificaLogin(username, password)) {
            Cliente cliente = clienteDAO.getCliente(username, password);
            PortafoglioVirtuale portafoglio = portafoglioDAO.leggiPortafoglio(cliente.getCf());
            
            // Gestione della sessione
            Sessione sessione = Sessione.getInstance();
            sessione.setClienteConnesso(cliente);
            sessione.setPortafoglioCliente(portafoglio);
            
            return cliente;
        }
        return null;
    }

    public boolean registraCliente(Cliente cliente) {
        if (!clienteDAO.esisteUsername(cliente.getUsername()) && !clienteDAO.esisteCliente(cliente.getEmail())) {
            clienteDAO.registraCliente(cliente);
            return true;
        }
        return false;
    }
}

