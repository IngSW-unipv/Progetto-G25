package it.unipv.ingsfw.bitebyte.pagamenti;

import it.unipv.ingsfw.bitebyte.test.BancomatService;

/**
 * Adapter per la gestione del pagamento tramite Bancomat.
 */
public class BancomatPaymentAdapter implements IPaymentStrategy {
	private final BancomatService bancomatService;

	public BancomatPaymentAdapter(BancomatService bancomatService) {
		this.bancomatService = bancomatService;
	}

	@Override
	public boolean ricarica(double amount) {
		if (amount >= 5 && amount <= 50) {

			System.out.println("Ricarica di " + amount + "€ effettuata tramite Bancomat.");
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
