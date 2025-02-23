package it.unipv.ingsfw.bitebyte.dao;

import java.util.List;

import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;

public interface IPortafoglioVirtualeDAO {
    boolean creaPortafoglio(PortafoglioVirtuale portafoglio);
    PortafoglioVirtuale leggiPortafoglio(String cf);
    boolean aggiornaPortafoglio(PortafoglioVirtuale portafoglio);
    boolean eliminaPortafoglio(String cf);
    List<PortafoglioVirtuale> getAllPortafogli();

}
