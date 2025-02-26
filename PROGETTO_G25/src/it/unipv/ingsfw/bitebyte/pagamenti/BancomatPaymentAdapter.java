package it.unipv.ingsfw.bitebyte.pagamenti;

import it.unipv.ingsfw.bitebyte.test.BancomatService;

/**
 * Adapter per la gestione del pagamento tramite Bancomat.
 */
public class BancomatPaymentAdapter implements IPaymentAdapter {

	private final BancomatService bancomatService;

	public BancomatPaymentAdapter(BancomatService bancomatService) {
		this.bancomatService = bancomatService;
	}

	@Override
	public boolean ricarica(double amount) {
		return bancomatService.effettuaPagamento(amount);
	}

	@Override
	public String getNomeMetodo() {
		return "Bancomat";
	}

}
