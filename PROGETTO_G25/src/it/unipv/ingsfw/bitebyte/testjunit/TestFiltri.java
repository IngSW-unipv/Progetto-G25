package it.unipv.ingsfw.bitebyte.testjunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.unipv.ingsfw.bitebyte.facade.RicercaFacade;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.StockService;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public class TestFiltri {

    private RicercaFacade facade;

    /**
     * DummyStockService restituisce una lista fissa di Stock.
     * Si assume che:
     * - lo stato "Disponibile" indichi che il prodotto è disponibile;
     * - il metodo getStockByInventario restituisce tutti i prodotti dell'inventario.
     */
    private class DummyStockService extends StockService {
        @Override
        public ArrayList<Stock> getStockByInventario(int idInventario) {
            ArrayList<Stock> stocks = new ArrayList<>();
            
            // Prodotto 1: Coca Cola, Bevanda Fredda, prezzo 1.50, disponibile ("Disponibile")
            Prodotto prod1 = new Prodotto(1, "Coca Cola", BigDecimal.valueOf(1.50), Categoria.BEVANDA_FREDDA);
            Stock stock1 = new Stock(idInventario, 10, 20, "Disponibile", prod1);
            
            // Prodotto 2: Pepsi, Bevanda Fredda, prezzo 1.40, NON disponibile ("Non Disponibile")
            Prodotto prod2 = new Prodotto(2, "Pepsi", BigDecimal.valueOf(1.40), Categoria.BEVANDA_FREDDA);
            Stock stock2 = new Stock(idInventario, 10, 20, "Non Disponibile", prod2);
            
            // Prodotto 3: Espresso, Bevanda Calda, prezzo 2.00, disponibile ("Disponibile")
            Prodotto prod3 = new Prodotto(3, "Espresso", BigDecimal.valueOf(2.00), Categoria.BEVANDA_CALDA);
            Stock stock3 = new Stock(idInventario, 10, 20, "Disponibile", prod3);
            
            // Prodotto 4: Muffin, Snack Dolce, prezzo 2.50, disponibile ("Disponibile")
            Prodotto prod4 = new Prodotto(4, "Muffin", BigDecimal.valueOf(2.50), Categoria.SNACK_DOLCE);
            Stock stock4 = new Stock(idInventario, 10, 20, "Disponibile", prod4);
            
            // Prodotto 5: Water, Bevanda Fredda, prezzo 1.00, disponibile ("Disponibile")
            Prodotto prod5 = new Prodotto(5, "Water", BigDecimal.valueOf(1.00), Categoria.BEVANDA_FREDDA);
            Stock stock5 = new Stock(idInventario, 10, 20, "Disponibile", prod5);
            
            stocks.add(stock1);
            stocks.add(stock2);
            stocks.add(stock3);
            stocks.add(stock4);
            stocks.add(stock5);
            return stocks;
        }
    }

    @Before
    public void setUp() throws Exception {
        facade = new RicercaFacade();
        // Inietta la DummyStockService nel facade usando reflection
        Field stockServiceField = RicercaFacade.class.getDeclaredField("stockService");
        stockServiceField.setAccessible(true);
        stockServiceField.set(facade, new DummyStockService());
    }

    /**
     * Testa la ricerca per nome: cercando "Coca" deve essere restituito il prodotto "Coca Cola".
     */
    @Test
    public void testCercaProdottiByNome() {
        List<Stock> result = facade.cercaProdotti("Coca", 1);
        assertEquals("Dovrebbe essere restituito 1 prodotto", 1, result.size());
        assertTrue("Il prodotto deve essere 'Coca Cola'", result.get(0).getProdotto().getNome().toLowerCase().contains("coca"));
    }

    /**
     * Testa il filtraggio composito: per la categoria "Bevanda Fredda", disponibilità attiva,
     * ordinamento per prezzo ascendente.
     * Ci aspettiamo di avere solo i prodotti disponibili in "Bevanda Fredda":
     * "Water" (1.00) e "Coca Cola" (1.50), ordinati per prezzo ascendente.
     */
    @Test
    public void testApplicaFiltriCategoriaDisponibilitaPrezzoAsc() {
        // Nota: FilterFactory convertirà "Bevanda Fredda" in Categoria.BEVANDA_FREDDA
        List<Stock> result = facade.applicaFiltri(1, "", "Bevanda Fredda", true, true, false);
        // Tra i prodotti dummy, in "Bevanda Fredda" disponibili (stato "Disponibile") abbiamo:
        // - Coca Cola e Water (Pepsi è escluso perché ha stato "Non Disponibile")
        // Ordinamento per prezzo ascendente: "Water" (1.00) poi "Coca Cola" (1.50)
        assertEquals("Dovrebbero essere restituiti 2 prodotti", 2, result.size());
        assertEquals("Il primo prodotto dovrebbe essere 'Water'", "Water", result.get(0).getProdotto().getNome());
        assertEquals("Il secondo prodotto dovrebbe essere 'Coca Cola'", "Coca Cola", result.get(1).getProdotto().getNome());
    }

    /**
     * Testa il filtraggio per nome e categoria: cercando "Espresso" nella categoria "Bevanda Calda"
     * deve essere restituito il solo prodotto "Espresso".  
     * Nota: il filtro per nome controlla se il nome contiene la sottostringa "espresso" (case-insensitive).
     */
    @Test
    public void testApplicaFiltriNomeECategoria() {
        List<Stock> result = facade.applicaFiltri(1, "Espresso", "Bevanda Calda", true, true, false);
        assertEquals("Dovrebbe essere restituito 1 prodotto", 1, result.size());
        assertTrue("Il prodotto deve contenere 'espresso' nel nome", 
                   result.get(0).getProdotto().getNome().toLowerCase().contains("espresso"));
    }

    /**
     * Testa l'esclusione dei prodotti non disponibili:
     * cercando "Pepsi" in "Bevanda Fredda" con disponibilità attiva non deve restituire alcun prodotto.
     */
    @Test
    public void testApplicaFiltriEscludiNonDisponibili() {
        List<Stock> result = facade.applicaFiltri(1, "Pepsi", "Bevanda Fredda", true, true, false);
        assertEquals("Dovrebbe essere restituito 0 prodotti", 0, result.size());
    }
}

