package it.unipv.ingsfw.bitebyte.models;

import it.unipv.ingsfw.bitebyte.pagamenti.IPaymentAdapter;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

public class PortafoglioVirtuale {

	private String idPort;
	private double saldo;
	private TipologiaPagamento tipologiaPagamento;

	// costruttore
	public PortafoglioVirtuale(String idPort, double saldo, TipologiaPagamento tipologiaPagamento) {
		this.idPort = idPort;
		this.saldo = saldo;
		this.tipologiaPagamento = tipologiaPagamento;
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

	public TipologiaPagamento getTipologiaPagamento() {
		return tipologiaPagamento;
	}

	public void setTipologiaPagamento(TipologiaPagamento tipologiaPagamento) {
		this.tipologiaPagamento = tipologiaPagamento;
	}

	/**
	 * Metodo per aggiornare direttamente il saldo.
	 */
	public void aggiornaSaldo(double amount) {
		this.saldo += amount;
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
