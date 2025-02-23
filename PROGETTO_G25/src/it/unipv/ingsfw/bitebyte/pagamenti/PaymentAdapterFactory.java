package it.unipv.ingsfw.bitebyte.pagamenti;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import it.unipv.ingsfw.bitebyte.test.BancomatService;
import it.unipv.ingsfw.bitebyte.test.PayPalService;

//Alice
/**
 * Factory per la creazione dinamica di IPaymentAdapter.
 */
public class PaymentAdapterFactory {

	private static final Map<String, Supplier<IPaymentAdapter>> adapterRegistry = new HashMap<>();

	// Registriamo i metodi di pagamento disponibili
	static {
		register("bancomat", () -> new BancomatPaymentAdapter(new BancomatService()));
		register("paypal", () -> new PayPalPaymentAdapter(new PayPalService()));
	}

	/**
	 * Metodo per registrare dinamicamente nuovi adapter.
	 * 
	 * @param metodo   Nome del metodo di pagamento (chiave).
	 * @param supplier Funzione che fornisce l'IPaymentAdapter.
	 */
	public static void register(String metodo, Supplier<IPaymentAdapter> supplier) {
		adapterRegistry.put(metodo.toLowerCase(), supplier);
	}

	/**
	 * Ottiene l'adapter corrispondente al metodo di pagamento.
	 * 
	 * @param metodo Nome del metodo di pagamento.
	 * @return Un'istanza di IPaymentAdapter.
	 * @throws IllegalArgumentException se il metodo non è supportato.
	 */
	public static IPaymentAdapter getPaymentAdapter(String metodo) {
		Supplier<IPaymentAdapter> supplier = adapterRegistry.get(metodo.toLowerCase());
		if (supplier == null) {
			throw new IllegalArgumentException("Metodo di pagamento non supportato: " + metodo);
		}
		return supplier.get();
	}

}
