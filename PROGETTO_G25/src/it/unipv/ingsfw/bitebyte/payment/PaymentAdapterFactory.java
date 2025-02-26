package it.unipv.ingsfw.bitebyte.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

//Alice
public class PaymentAdapterFactory {

	private static final Map<String, Supplier<IPaymentAdapter>> adapterRegistry = new HashMap<>();

	// Registriamo i metodi di pagamento disponibili
	static {
		register("bancomat", () -> new BancomatPaymentAdapter(new BancomatService()));
		register("paypal", () -> new PayPalPaymentAdapter(new PayPalService()));
	}

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
	public static IPaymentAdapter getPaymentAdapter(TipologiaPagamento tipologia) {
		String tipologiaScelta = tipologia.name().toLowerCase();
		Supplier<IPaymentAdapter> supplier = adapterRegistry.get(tipologiaScelta);
		if (supplier == null) {
			throw new IllegalArgumentException("Metodo di pagamento non supportato: " + tipologiaScelta);
		}
		return supplier.get();
	}

}
