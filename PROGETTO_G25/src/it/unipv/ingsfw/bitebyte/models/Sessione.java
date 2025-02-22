package it.unipv.ingsfw.bitebyte.models;

// Alice
public class Sessione {

	private static Sessione instance; // classe Sessione è Singleton
	private Cliente clienteConnesso;

	// costruttore
	private Sessione() {

	}

	// posso creare/lavorare solo con un oggetto sessione, che è la mia instance
	// chiamo la sessione con il metodo Sessione.getInstance() che restituisce
	// instance
	public static Sessione getInstance() {
		if (instance == null) {
			instance = new Sessione();
		}
		return instance;
	}

	// Getter and Setter
	public Cliente getClienteConnesso() {
		return clienteConnesso;
	}

	public void setClienteConnesso(Cliente clienteConnesso) {
		this.clienteConnesso = clienteConnesso;
	}

	// Metodo per fare logout e resettare l'intera sessione
	public void logout() {
		// Resetta l'istanza del singleton, quindi anche clienteConnesso sarà null
		instance = null;
	}

}
