package it.unipv.ingsfw.bitebyte.payment;

/**
 * Interfaccia per il pattern Strategy che definisce il comportamento
 * per le diverse strategie di pagamento (Bancomat e PayPal).
 */

public interface IPaymentAdapter {

	    /**
	     * Metodo per effettuare la ricarica del portafoglio virtuale.
	     * 
	     * @param amount L'importo da ricaricare.
	     * @return true se la ricarica è andata a buon fine, false altrimenti.
	     */
	    boolean ricarica(double amount);

	    /**
	     * Metodo per ottenere il nome della strategia di pagamento.
	     * 
	     * @return Il nome della strategia.
	     */
	    String getNomeMetodo();
	}

