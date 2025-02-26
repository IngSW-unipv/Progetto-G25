package it.unipv.ingsfw.bitebyte.testjunit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.Fornitore;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.DistributoreService;
import it.unipv.ingsfw.bitebyte.services.FornituraService;
import it.unipv.ingsfw.bitebyte.services.GestioneInventarioService;
import it.unipv.ingsfw.bitebyte.services.ProdottoService;
import it.unipv.ingsfw.bitebyte.services.SpedizioneService;
import it.unipv.ingsfw.bitebyte.services.StockService;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public class TestHandleRestock {

    private GestioneInventarioService gestioneInventarioService;
    private StockService stockService;
    
    private Stock stock;
    private Fornitura fornitura;

    @Before
    public void setUp() {
       
        stockService = new StockService();  // Usa la versione reale
        gestioneInventarioService = new GestioneInventarioService(
                stockService, 
                new FornituraService(),   
                new ProdottoService(),    
                new DistributoreService(),
                new SpedizioneService()  
        );
        
        // Inizializzazione degli oggetti per il test
        Prodotto prodotto = new Prodotto(75, "Pepsi", new BigDecimal("0.90"), Categoria.BEVANDA_FREDDA);
        stock = new Stock(19, 0, 10, "Esaurito", prodotto);  
        Fornitore fornitore = new Fornitore(75, "Bibite Maxi", "Napoli", "Via Napoleone", "17C");
        fornitura = new Fornitura(prodotto, fornitore, new BigDecimal("0.50"));  
    }

    @Test
    public void testHandleRestock() {
        // Imposta una quantità valida per il restock
        int quantitaDaRestock = 9;
        // Calcola il prezzo scontato con il metodo calcolaPrezzoScontato
        BigDecimal prezzoScontato = gestioneInventarioService.calcolaPrezzoScontato(fornitura, quantitaDaRestock, stock);
        // Aggiungi l'elemento al carrello
        Carrello carrello = Carrello.getInstance();
        assertEquals(0, carrello.getItems().size());  // Verifica che il carrello sia vuoto all'inizio
        // Verifica che il metodo handleRestock funzioni correttamente
        gestioneInventarioService.handleRestock(stock, fornitura, quantitaDaRestock);
        // Controlla se l'articolo è stato effettivamente aggiunto al carrello
        assertEquals(1, carrello.getItems().size()); // Verifica che ci sia un articolo nel carrello
        assertEquals(fornitura, carrello.getItems().get(0).getFornitura());  // Verifica che l'articolo nel carrello sia corretto
        assertEquals(quantitaDaRestock, carrello.getItems().get(0).getQuantita());  // Verifica che la quantità sia corretta
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleRestockInvalidQuantity() {
        // Test con quantità non valida (negativa)
        int quantitaDaRestock = -5;
        gestioneInventarioService.handleRestock(stock, fornitura, quantitaDaRestock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleRestockExceedsMaxQuantity() {
        // Test con quantità che supera il massimo inseribile (includendo quantità nel carrello e disponibile)
        int quantitaDaRestock = 200;  
        gestioneInventarioService.handleRestock(stock, fornitura, quantitaDaRestock);
    }
}


