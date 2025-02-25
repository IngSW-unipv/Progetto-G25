package it.unipv.ingsfw.bitebyte.pagamenti;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import it.unipv.ingsfw.bitebyte.test.BancomatService;
import it.unipv.ingsfw.bitebyte.test.PayPalService;
//import it.unipv.ingsfw.bitebyte.test.BancomatService;
//import it.unipv.ingsfw.bitebyte.test.PayPalService;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

//Alice
/**
 * Factory per la creazione dinamica di IPaymentAdapter.
 */
public class PaymentAdapterFactory {

	private static final Map<String, Supplier<IPaymentAdapter>> adapterRegistry = new HashMap<>();

	// Registriamo i metodi di pagamento disponibili
	static {
	//	caricaAdapterDaProperties();
		 register("bancomat", () -> new BancomatPaymentAdapter(new BancomatService()));
		 register("paypal", () -> new PayPalPaymentAdapter(new PayPalService()));
	}

	/*private static void caricaAdapterDaProperties() {
		Properties properties = new Properties();
		try (InputStream input = PaymentAdapterFactory.class.getClassLoader()
				.getResourceAsStream("properties.properties")) {

			if (input == null) {
				throw new RuntimeException("File properties.properties non trovato!");
			}

			properties.load(input);

			// Ciclo su ogni metodo di pagamento definito nel file properties
			for (String metodo : properties.stringPropertyNames()) {
				String className = properties.getProperty(metodo);
				register(metodo, () -> creaAdapter(className));
			}

		} catch (IOException e) {
			throw new RuntimeException("Errore nel caricamento dei metodi di pagamento", e);
		}
	}*/

	
	
	/*private static void caricaAdapterDaProperties() {
	    Properties properties = new Properties();
	    // Percorso relativo alla cartella properties (fuori da src)
	    String percorsoFile = "properties/properties.properties";
	    
	    try (InputStream input = new FileInputStream(percorsoFile)) {

	        properties.load(input);

	        // Ciclo su ogni metodo di pagamento definito nel file properties
	        for (String metodo : properties.stringPropertyNames()) {
	            String className = properties.getProperty(metodo);
	            register(metodo, () -> creaAdapter(className));
	        }

	    } catch (IOException e) {
	        throw new RuntimeException("Errore nel caricamento dei metodi di pagamento", e);
	    }
	}*/
	
	
	/**
	 * Registra dinamicamente un metodo di pagamento.
	 * 
	 * @param metodo   Nome del metodo di pagamento (chiave).
	 * @param supplier Funzione che fornisce l'IPaymentAdapter.
	 */
	/*public static void register(String metodo, Supplier<IPaymentAdapter> supplier) {
		adapterRegistry.put(metodo.toLowerCase(), supplier);
	}*/

	
    /**
     * Metodo per registrare dinamicamente nuovi adapter.
     * @param metodo Nome del metodo di pagamento (chiave).
     * @param supplier Funzione che fornisce l'IPaymentAdapter.
     */
    public static void register(String metodo, Supplier<IPaymentAdapter> supplier) {
        adapterRegistry.put(metodo.toLowerCase(), supplier);
    }
	
    /**
     * Ottiene l'adapter corrispondente al metodo di pagamento.
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
	/**
	 * Restituisce un PaymentAdapter corrispondente alla tipologia di pagamento.
	 * 
	 * @param tipologia Enum TipologiaPagamento.
	 * @return Un'istanza di IPaymentAdapter.
	 * @throws IllegalArgumentException se il metodo non è supportato.
	 */
	/*public static IPaymentAdapter getPaymentAdapter(TipologiaPagamento tipologia) {
		Supplier<IPaymentAdapter> supplier = adapterRegistry.get(tipologia.name().toLowerCase());
		if (supplier == null) {
			throw new IllegalArgumentException("Metodo di pagamento non supportato: " + tipologia);
		}
		return supplier.get();
	}*/

	
	/**
	 * Crea dinamicamente un'istanza di IPaymentAdapter da una classe.
	 *
	 * @param className Nome completo della classe (con il package) = una del file properties.
	 * @return Un'istanza di IPaymentAdapter.
	 */
	/*private static IPaymentAdapter creaAdapter(String className) {
	    try {
	        return (IPaymentAdapter) Class.forName(className).newInstance();
	    } catch (Exception e) {
	        throw new RuntimeException("Errore nella creazione dell'adapter: " + className, e);
	    }
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//CODICE RICCARDO
	
	
/*	private static final HashMap<String, Supplier<IPaymentAdapter>> adapterRegistry = new HashMap<String, Supplier<IPaymentAdapter>>();

	// Vengono registrati i tipi di Utente disponibili
	static {
		caricaUtentiDaProperties();
	}

	private static void caricaUtentiDaProperties() {
		Properties properties = new Properties();
		try (InputStream input = PaymentAdapterFactory.class.getClassLoader().getResourceAsStream("properties.properties")) {

			if (input == null) {
				throw new RuntimeException("File properties.properties non trovato!");
			}

			properties.load(input);

			// Ciclo su ogni tipo di pagamento definito nel file properties
			for (String tipo : properties.stringPropertyNames()) {
				String className = properties.getProperty(tipo);
				register(tipo, () -> creaAdapter(className));
			}
			

		} catch (IOException e) {
			throw new RuntimeException("Errore nel caricamento dei tipi di pagamento", e);
		}
	}

	private static IPaymentAdapter creaAdapter(String className) {
		try {
			Class<?> clazz = Class.forName(className);

			// Verifica che la classe implementi IUtenteDAO
			if (!IPaymentAdapter.class.isAssignableFrom(clazz)) {
				throw new IllegalArgumentException("La classe " + className + " non implementa il tipo di pagamento");
			}

			return (IPaymentAdapter) clazz.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Errore nella creazione del tipo di pagamento: " + className, e);
		}
	}

	
	public static void register(String tipo, Supplier<IPaymentAdapter> supplier) {
		if (tipo == null || supplier == null) {
			throw new IllegalArgumentException("Tipo di pagamento non supportato");
		}
		adapterRegistry.put(tipo.toLowerCase(), supplier);
	}

	public static IPaymentAdapter getPaymentAdapter(TipologiaPagamento tipologia) {
		String tipologiaScelta = tipologia.name().toLowerCase();
		Supplier<IPaymentAdapter> supplier = adapterRegistry.get(tipologiaScelta);

		if (supplier == null) {
			throw new IllegalArgumentException("Tipo di pagamento non supportato: " + tipologia);
		}

		return supplier.get(); 
	}*/

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
