package it.unipv.ingsfw.bitebyte.services;

import java.sql.Timestamp;

import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;
import it.unipv.ingsfw.bitebyte.dao.TransazioneDAO;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Transazione;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;

public class TransazioneService {


	    private TransazioneDAO transazioneDAO;

	    public TransazioneService() {
	        this.transazioneDAO = new TransazioneDAO();
	    }
	    
	    public boolean creaTransazione(Ordine ordine, int ID_Port) {
	        // Crea una nuova transazione usando i dati dell'ordine
	        int idTransazione = generaIdTransazione(); // Logica per generare un ID unico per la transazione
	        boolean isComplete = true;  // Impostiamo come "completata" la transazione (può dipendere dalla logica)
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Imposta il timestamp attuale

	        // Crea l'oggetto Transazione con il costruttore parametrizzato
	        Transazione transazione = new Transazione(idTransazione, isComplete, timestamp);

	        // Ora chiama il DAO per inserire la transazione
	        boolean transazioneInserita = transazioneDAO.inserisciTransazione(transazione, ordine.getIdOrdine(), ID_Port);
	        

	        return transazioneInserita;
	    }

	    
	    
	    private int generaIdTransazione() {
	        return (int) (Math.random() * 100000); // Genera un ID casuale
	    }
}
	 