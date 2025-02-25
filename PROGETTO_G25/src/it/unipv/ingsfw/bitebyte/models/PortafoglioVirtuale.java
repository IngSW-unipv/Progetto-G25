package it.unipv.ingsfw.bitebyte.models;
import java.math.BigDecimal;

/**
 * La classe PortafoglioVirtuale rappresenta un portafoglio virtuale 
 * che tiene traccia del saldo del cliente. Permette operazioni come 
 * ricaricare il saldo con importi specifici e aggiornare il saldo in seguito
 * ad un'operazione di pagamento.
 * 
 * Il saldo è gestito tramite la classe {@link BigDecimal} per garantire alta 
 * precisione nei calcoli monetari.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 * @see BigDecimal
 */
public class PortafoglioVirtuale {
	
    // Definizione delle costanti per i limiti di ricarica
	
    private static final BigDecimal MIN_IMPORTO = new BigDecimal("5");
    private static final BigDecimal MAX_IMPORTO = new BigDecimal("50");

    // ATTRIBUTI
    private int idPort;
    private BigDecimal saldo;

    //COSTRUTTORE PARAMETRIZZATO
    
    //Includere il saldo nel costruttore non è necessario 
    //in quanto la logica di gestione del saldo è delegata a metodi separati, come ricarica,
    //che controllano la validità dell'importo e aggiornano il saldo in modo controllato
    
    /**
     * Costruttore della classe {@code PortafoglioVirtuale}.
     * 
     * @param idPort L'identificativo del portafoglio virtuale.
     * Inizializza il saldo a {@link BigDecimal#ZERO}.
     */
    public PortafoglioVirtuale(int idPort) {
        this.idPort = idPort;
        this.saldo = BigDecimal.ZERO;
    }

     //GETTERS AND SETTERS
    /**
     * Restituisce l'identificativo del portafoglio.
     * 
     * @return L'identificativo del portafoglio.
     */
    public int getIdPort() {
        return idPort;
    }

    /**
     * Imposta l'identificativo del portafoglio.
     * 
     * @param idPort L'identificativo del portafoglio.
     */
    public void setIdPort(int idPort) {
        this.idPort = idPort;
    }

    /**
     * Imposta un nuovo saldo per il portafoglio.
     * 
     * @param saldo Il saldo da impostare nel portafoglio.
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    
    //METODI
    /**
     * Ricarica il saldo del portafoglio virtuale con un importo specificato.
     * L'importo deve essere compreso tra 5 e 50.
     * Se l'importo non è valido, viene generata un'eccezione {@link IllegalArgumentException}.
     * 
     * @param importo L'importo da ricaricare nel portafoglio.
     * @throws IllegalArgumentException Se l'importo non è compreso tra 5 e 50.
     */
    public void ricarica(BigDecimal importo) {
        // Controlla che l'importo sia tra i limiti definiti
        if (importo.compareTo(MIN_IMPORTO) >= 0 && importo.compareTo(MAX_IMPORTO) <= 0) {
            // Aggiungi l'importo al saldo
            this.saldo = this.saldo.add(importo);
        } else {
            // Lancia l'eccezione se l'importo non è valido
            throw new IllegalArgumentException("Importo non valido, deve essere compreso tra " + MIN_IMPORTO + " e " + MAX_IMPORTO);
        }
    }

    /**
     * Aggiorna il saldo sottraendo un importo specificato. 
     * La sottrazione avviene solo se il saldo è maggiore o uguale all'importo da sottrarre.
     * 
     * @param importo L'importo da sottrarre dal saldo.
     * @return {@code true} se l'operazione è riuscita, {@code false} altrimenti.
     */
    public boolean aggiornaSaldo(BigDecimal importo) {
        if (saldo.compareTo(importo) >= 0) {  // Confronta saldo e importo
            saldo = saldo.subtract(importo);  // Sottrai importo da saldo
            return true;
        }
        return false;
    }
}


