package it.unipv.ingsfw.bitebyte.facade;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.DistributoreCompletoService;
import it.unipv.ingsfw.bitebyte.services.StockService;
import it.unipv.ingsfw.bitebyte.filtri.FilterByNome;
import it.unipv.ingsfw.bitebyte.filtri.FilterFactory;
import it.unipv.ingsfw.bitebyte.filtri.IFilterStrategy;

import java.util.List;

public class RicercaFacade {
    private StockService stockService;
    private DistributoreCompletoService distributoreService;

    public RicercaFacade() {
        this.stockService = new StockService();
        this.distributoreService = new DistributoreCompletoService();
    }

    // Metodo per cercare i prodotti tramite nome
    public List<Stock> cercaProdotti(String query, int idInventario) {
        List<Stock> stocks = stockService.getStockByInventario(idInventario);
        return new FilterByNome(query).applyFilter(stocks); //creo oggetto di tipo FilterByNome con la query fornita e poi invoco il metodo applyFilte(stocks)
        
        // questo filtro esamina ogni elemento della lista e restituisce una nuova lista 
        //contenente solo gli stock il cui nome contiene la sottostringa specificata nella query 
        //(il confronto è case-insensitive perché il filtro converte i nomi in minuscolo).
    }
    

    // Metodo  per applicare filtri ai prodotti
    public List<Stock> applicaFiltri(int idInventario, String query, String categoria, boolean disponibilita, boolean sortAsc, boolean sortDesc) {
        List<Stock> stocks = stockService.getStockByInventario(idInventario);  
        //il metodo della factory restituisce un'istanza che implementas l'interfsccia IFilterFactory
        IFilterStrategy filtro = FilterFactory.createFilter(query, categoria, disponibilita, sortAsc, sortDesc);
        return filtro.applyFilter(stocks);  
    }

    // Metodo  per cercare distributori alternativi con il prodotto specificato
    public List<Distributore> cercaDistributoriConProdotto(int idDistributoreCorrente, String nomeProdotto) {
        return distributoreService.getDistributoriConProdottoDisponibileByName(idDistributoreCorrente, nomeProdotto);
    }
}
