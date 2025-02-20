package it.unipv.ingsfw.bitebyte.dao;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public interface IClienteDAO {
	void registraCliente(Cliente cliente);
    
    public boolean verificaLogin (String username, String password);
    
    // Alice    
    public Cliente getCliente(String username, String password);
 
    public boolean modificaProfilo(Cliente clienteModificato);
    

    
    //public void modificaProfilo (String );
}

