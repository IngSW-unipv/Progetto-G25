package it.unipv.ingsfw.bitebyte.dao;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public interface IClienteDAO {
	void registraCliente(Cliente cliente); 	// crea cliente
    
    public boolean verificaLogin (String username, String password);	// leggi cliente
    
    public boolean esisteUsername(String username);
    
    public boolean esisteCliente(String email);
    
    // Alice    
    public Cliente getCliente(String username, String password);	// leggi cliente
 
    public boolean modificaProfilo(Cliente clienteModificato);		// modifica cliente
    

}

