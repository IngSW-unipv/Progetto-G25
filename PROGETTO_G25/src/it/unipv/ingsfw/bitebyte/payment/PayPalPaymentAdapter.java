package it.unipv.ingsfw.bitebyte.payment;

/**
 * Adapter per la gestione del pagamento tramite PayPal.
 */
public class PayPalPaymentAdapter implements IPaymentAdapter {

	private final PayPalService payPalService;

	public PayPalPaymentAdapter(PayPalService payPalService) {
		this.payPalService = payPalService;
	}

	@Override
	public boolean ricarica(double amount) {
		return payPalService.processaPagamento(amount);
	}

	@Override
	public String getNomeMetodo() {
		return "PayPal";
	}

}
