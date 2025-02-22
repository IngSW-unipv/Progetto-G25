package it.unipv.ingsfw.bitebyte.pagamenti;

import it.unipv.ingsfw.bitebyte.test.PayPalService;

/**
 * Adapter per la gestione del pagamento tramite PayPal.
 */
public class PayPalPaymentAdapter implements IPaymentStrategy {

	private final PayPalService payPalService;

	public PayPalPaymentAdapter(PayPalService payPalService) {
		this.payPalService = payPalService;
	}

	@Override
	public boolean ricarica(double amount) {
		if (amount >= 5 && amount <= 50) {
			System.out.println("Ricarica di " + amount + "€ effettuata tramite PayPal.");
			return payPalService.processaPagamento(amount);
		}

		System.out.println("Importo non valido per la ricarica tramite PayPal.");
		return false;

	}

	@Override
	public String getNomeMetodo() {
		return "PayPal";
	}

}
