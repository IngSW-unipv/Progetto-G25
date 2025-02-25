package it.unipv.ingsfw.bitebyte.models;


import it.unipv.ingsfw.bitebyte.pagamenti.IPaymentAdapter;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

// Alice
public class PortafoglioVirtuale {

	private String idPort;
	private double saldo;
	//private Bancomat cartaCliente;
	private TipologiaPagamento tipologiaPagamento;

	//costruttore
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

/*	public Bancomat getCartaCliente() {
		return cartaCliente;
	}

	public void setCartaCliente(Bancomat cartaCliente) {
		this.cartaCliente = cartaCliente;
	}*/

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
	
	// per generare l'id portafoglio
    public String generaIdCasuale() {
        int numero = (int)(Math.random()*9999)+1000;
        return String.valueOf(numero);
    }

}
