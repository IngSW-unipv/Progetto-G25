package it.unipv.ingsfw.bitebyte.service;
import java.util.List;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;

//Il Controller non accede più direttamente al DAO.
public class DistributoreService {

    private DistributoreDAO distributoreDAO;

    public DistributoreService() {
        this.distributoreDAO = new DistributoreDAO();
    }

    public Distributore getDistributoreById(int id) {
        return distributoreDAO.getDistributoreById(id);
    }
    
    public List<Distributore> getDistributoriAlternativiByName(int idDistrCorrente, String nomeProdotto) {
        return distributoreDAO.getDistributoriConProdottoDisponibileByName(idDistrCorrente, nomeProdotto);
    }
    
    public double calcolaDistanza(Distributore da, Distributore a) {
        return CalcolaDistanza.calcolaDistanza(da, a);
    }
}
