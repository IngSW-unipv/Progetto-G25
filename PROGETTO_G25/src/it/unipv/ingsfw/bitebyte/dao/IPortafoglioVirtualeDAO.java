package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;

public interface IPortafoglioVirtualeDAO {
	//Alice
    boolean creaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente);
    PortafoglioVirtuale leggiPortafoglio(String cf);
    boolean aggiornaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente);
    
    //Davide
    double getSaldo(String codiceFiscale);
    void aggiornaSaldo(String codiceFiscale, double nuovoSaldo);
}
