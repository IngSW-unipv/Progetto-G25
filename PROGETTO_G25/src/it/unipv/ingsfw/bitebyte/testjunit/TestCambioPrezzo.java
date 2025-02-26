package it.unipv.ingsfw.bitebyte.testjunit;

import static org.junit.Assert.*;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.GestioneInventarioService;
import it.unipv.ingsfw.bitebyte.services.StockService;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public class TestCambioPrezzo {

    private GestioneInventarioService gestioneInventarioService;
    private StockService stockService;
    private Stock stock;
    private Prodotto prodotto;

    @Before
    public void setUp() {
        // Creiamo una versione semplificata di StockService, senza logica complessa.
        stockService = new StockService() {
            @Override
            public boolean updatePrice(int idProdotto, int idInventario, BigDecimal nuovoPrezzo) {
                // Implementazione semplificata del metodo che simula il comportamento
                if (nuovoPrezzo.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Prezzo non valido");
                }
                if (nuovoPrezzo.compareTo(new BigDecimal("5.00")) > 0) {
                    throw new IllegalArgumentException("Prezzo troppo alto");
                }
                return true;
            }
        };
        
        // Inizializziamo GestioneInventarioService con la versione semplificata di StockService
        gestioneInventarioService = new GestioneInventarioService(stockService, null, null, null, null);

        // Crea un prodotto e un oggetto Stock per il test
        prodotto = new Prodotto(1, "Bottiglia d'Acqua", new BigDecimal("1.50"), Categoria.BEVANDA_FREDDA);
        stock = new Stock(2, 10, 10, "Disponibile", prodotto);
    }

    @Test
    public void testHandleCambioPrezzo_ValidPrice() {
        BigDecimal nuovoPrezzo = new BigDecimal("2.50");
        
        // Verifica che il metodo aggiorni correttamente il prezzo
        boolean result = gestioneInventarioService.handleCambioPrezzo(stock, nuovoPrezzo);
        
        assertTrue(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleCambioPrezzo_PrezzoTroppoBasso() {
        // Test che il metodo lanci un'eccezione per un prezzo troppo basso
        gestioneInventarioService.handleCambioPrezzo(stock, new BigDecimal("0.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleCambioPrezzo_PrezzoTroppoAlto() {
        // Test che il metodo lanci un'eccezione per un prezzo troppo alto
        gestioneInventarioService.handleCambioPrezzo(stock, new BigDecimal("5.50"));
    }
}
