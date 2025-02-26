package it.unipv.ingsfw.bitebyte.testjunit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Fornitore;
import it.unipv.ingsfw.bitebyte.services.GestioneInventarioService;
import it.unipv.ingsfw.bitebyte.strategyforn.IDiscountStrategy;
import it.unipv.ingsfw.bitebyte.strategyforn.DiscountFactory;
import it.unipv.ingsfw.bitebyte.services.SupplyContext;

public class TestPrezzoScontato {

    private GestioneInventarioService gestioneInventarioService;
    private Fornitura fornitura;
    private Stock stock;
    private Prodotto prodotto;
    private Fornitore fornitore;
    
    @Before
    public void setUp() {
        // Inizializza il prodotto
        prodotto = new Prodotto(1, "Patatine S.Carlo", new BigDecimal("1.00"), Categoria.SNACK_SALATO);
        // Inizializza il fornitore
        fornitore = new Fornitore(43, "Salati&Salati", "Milano", "Via Longobardi", "16A");
        
        // Inizializza una fornitura con il prodotto, fornitore e un prezzo per unità
        fornitura = new Fornitura(prodotto, fornitore, new BigDecimal("0.40"));
        fornitura.setProdotto(prodotto);
        fornitura.setFornitore(fornitore);
        fornitura.setPpu(new BigDecimal("10.00"));
        
        // Inizializza uno stock con quantità disponibili e massime
        stock = new Stock(1001, 0, 10, "Esaurito", prodotto);    
        // Inizializza il servizio senza dipendenze reali
        gestioneInventarioService = new GestioneInventarioService(null, null, null, null, null);
    }

    @Test
    public void testCalcolaPrezzoScontato() {
        // Quantità per il test, minore di quella massima inseribile
    	int quantita = 8;
        // Ottengo la strategia di sconto da DiscountFactory
        IDiscountStrategy discountStrategy = DiscountFactory.getDiscountStrategy("quantity.strategy");
        // Verifica che la strategia sia stata trovata (evita NullPointerException)
        assertNotNull("La strategia di sconto non dovrebbe essere null", discountStrategy);
        // Crea un SupplyContext per il calcolo del prezzo scontato
        SupplyContext supplyContext = new SupplyContext(discountStrategy, fornitura.getPpu());
        BigDecimal expectedPrice = supplyContext.calculateFinalPrice(quantita, stock);
        // Calcola il prezzo scontato con il metodo da testare
        BigDecimal actualPrice = gestioneInventarioService.calcolaPrezzoScontato(fornitura, quantita, stock);
        // Confronta il prezzo calcolato con quello atteso
        assertEquals("Il prezzo calcolato non corrisponde al valore atteso", expectedPrice, actualPrice);
    }
    
    @Test
    public void testCalcolaPrezzoScontatoMax() {
        // Quantità per il test, pari al massimo inseribile
        int quantita = 10;
        // Ottengo la strategia di sconto "maxquantity"
        IDiscountStrategy discountStrategy = DiscountFactory.getDiscountStrategy("maxquantity.strategy");
        // Verifica che la strategia sia stata trovata
        assertNotNull("La strategia di sconto non dovrebbe essere null", discountStrategy);
        // SupplyContext per il calcolo del prezzo scontato
        SupplyContext supplyContext = new SupplyContext(discountStrategy, fornitura.getPpu());
        BigDecimal expectedPrice = supplyContext.calculateFinalPrice(quantita, stock);
        // Calcola il prezzo scontato con il metodo da testare
        BigDecimal actualPrice = gestioneInventarioService.calcolaPrezzoScontato(fornitura, quantita, stock);
        // Confronta il prezzo calcolato con quello atteso
        assertEquals("Il prezzo calcolato con strategia fissa non corrisponde al valore atteso", expectedPrice, actualPrice);
    }

}
