package it.unipv.ingsfw.bitebyte.models;

import it.unipv.ingsfw.bitebyte.pagamenti.IPaymentAdapter;

// Alice
public class PortafoglioVirtuale {

	private final String idPort;
	private double saldo;

	// costruttore
	public PortafoglioVirtuale(String idPort) {
		this.idPort = idPort;
		this.saldo = 0.0;
	}

	// Getters and Setters
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getIdPort() {
		return idPort;
	}

	/**
	 * Metodo per aggiornare direttamente il saldo.
	 */
	public void aggiornaSaldo(double amount) {
		saldo += amount;
		System.out.println("Saldo aggiornato: " + saldo + "€");
	}

	/**
	 * Metodo per ricaricare il portafoglio utilizzando un metodo di pagamento.
	 * 
	 * @param amount  L'importo da ricaricare.
	 * @param adapter L'adapter del metodo di pagamento.
	 */
	public void ricarica(double amount, IPaymentAdapter adapter) {
		if (adapter.ricarica(amount)) {
			aggiornaSaldo(amount);
			System.out.println("Ricarica completata tramite: " + adapter.getNomeMetodo());
		} else {
			System.out.println("Ricarica fallita.");
		}
	}

}
