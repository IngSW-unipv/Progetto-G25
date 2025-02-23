package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;

import java.util.List;

public class DistributoreService {
	
	private DistributoreDAO distributoreDAO;
	
	public DistributoreService() {
		this.distributoreDAO  = new DistributoreDAO();
	}
	
	public List<Distributore> getAllDistributori() {
		return distributoreDAO.getAllDistributori();
	}

}
