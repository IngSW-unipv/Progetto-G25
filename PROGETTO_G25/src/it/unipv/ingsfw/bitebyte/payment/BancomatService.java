package it.unipv.ingsfw.bitebyte.payment;

import java.time.YearMonth;

import it.unipv.ingsfw.bitebyte.dao.BancomatDAO;
import it.unipv.ingsfw.bitebyte.models.Bancomat;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;

public class BancomatService {

	private double saldo = 0.0; // saldo locale del bancomat, tiene traccia delle ricariche effettuate tramite
								// questo metodo di pagamento

	private final BancomatDAO bancomatDAO;

	public BancomatService() {
		this.bancomatDAO = new BancomatDAO();
		this.saldo = 0.0;
	}

	public boolean effettuaPagamento(double amount) {
		saldo += amount;
		System.out.println("Ricarica di " + amount + "€ effettuata tramite Bancomat. Saldo attuale: " + saldo + "€");
		return true;
	}

	public Bancomat creaBancomat(String numero, String titolare, String circuito, String cvv, String dataScadenza,
			Cliente cliente) {
		if (numero.isEmpty() || titolare.isEmpty() || circuito.isEmpty() || cvv.isEmpty() || dataScadenza.isEmpty()) {
			throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
		}

		try {
			int codice = Integer.parseInt(cvv);
			YearMonth scadenza = YearMonth.parse(dataScadenza);
			return new Bancomat(numero, titolare, scadenza, circuito, codice);
		} catch (Exception e) {
			throw new IllegalArgumentException("Dati non validi. Controlla il CVV e la data di scadenza (YYYY-MM).", e);
		}
	}

	public void salvaBancomat(Bancomat bancomat) {
		bancomatDAO.creaBancomat(bancomat, Sessione.getInstance().getClienteConnesso());
	}

}
