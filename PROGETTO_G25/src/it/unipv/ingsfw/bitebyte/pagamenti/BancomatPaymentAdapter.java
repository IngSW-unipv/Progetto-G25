package it.unipv.ingsfw.bitebyte.pagamenti;

import it.unipv.ingsfw.bitebyte.test.BancomatService;

/**
 * Adapter per la gestione del pagamento tramite Bancomat.
 */
public class BancomatPaymentAdapter implements IPaymentAdapter {
	private final BancomatService bancomatService;	

	// costruttore
	public BancomatPaymentAdapter(BancomatService bancomatService) {
		this.bancomatService = bancomatService;
	}

	@Override
	public boolean ricarica(double amount) {
		if (amount >= 5 && amount <= 50) {
			return bancomatService.effettuaPagamento(amount);
		}

		System.out.println("Importo non valido per la ricarica tramite Bancomat.");
		return false;
	}

	@Override
	public String getNomeMetodo() {
		return "Bancomat";
	}

}
