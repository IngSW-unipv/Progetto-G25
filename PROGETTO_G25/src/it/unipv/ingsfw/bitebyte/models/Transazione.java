package it.unipv.ingsfw.bitebyte.models;
import java.sql.Timestamp;


public class Transazione {
	private int idTransazione;
	private boolean isComplete;
	private Timestamp timestamp;
	
	
//COSTRUTTORE PARAMETRIZZATO
public Transazione(int idTransazione, boolean isComplete,  Timestamp timestamp) {
		
	this.idTransazione = idTransazione;
	this.isComplete = isComplete;
	this.timestamp = timestamp;
	}


//GETTERS AND SETTERS
public int getIdTransazione() {
	return idTransazione;
}


public void setIdTransazione(int idTransazione) {
	this.idTransazione = idTransazione;
}


public boolean isComplete() {
	return isComplete;
}


public void setComplete(boolean isComplete) {
	this.isComplete = isComplete;
}


public Timestamp getTimestamp() {
	return timestamp;
}


public void setTimestamp(Timestamp timestamp) {
	this.timestamp = timestamp;
}


	
	

}
