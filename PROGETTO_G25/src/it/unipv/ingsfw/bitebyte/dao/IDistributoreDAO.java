package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import java.util.List;

public interface IDistributoreDAO {
    Distributore getDistributoreById(int idDistributore);  // Recupera il distributore per ID
    List<Distributore> getAllDistributori();  // Recupera tutti i distributori
    void addDistributore(Distributore distributore);  // Aggiungi un nuovo distributore
    void updateDistributore(Distributore distributore);  // Modifica un distributore
    void deleteDistributore(int idDistributore);  // Elimina un distributore
   
}
