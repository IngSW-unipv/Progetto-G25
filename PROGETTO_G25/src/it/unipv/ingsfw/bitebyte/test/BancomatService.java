package it.unipv.ingsfw.bitebyte.test;

public class BancomatService {

	private double saldo = 0.0;	// saldo locale del bancomat, tiene traccia delle ricariche effettuate tramite questo metodo di pagamento

	public boolean effettuaPagamento(double amount) {
		saldo += amount;
		System.out.println("Ricarica di " + amount + "€ effettuata tramite Bancomat. Saldo attuale: " + saldo + "€");
		return true;
	}

/*	public double getSaldo() {
		return saldo;
	}*/

}
