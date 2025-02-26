package it.unipv.ingsfw.bitebyte.payment;

public class PayPalService {

	private double saldo = 0.0; // saldo locale di paypal, tiene traccia delle ricariche effettuate tramite
								// questo metodo di pagamento

	public boolean processaPagamento(double amount) {
		saldo += amount;
		System.out.println("Ricarica di " + amount + "€ effettuata tramite PayPal. Saldo attuale: " + saldo + "€");
		return true;
	}

}
