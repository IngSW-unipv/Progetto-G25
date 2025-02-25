package it.unipv.ingsfw.bitebyte.facade;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.service.DistributoreCompletoService;
import it.unipv.ingsfw.bitebyte.service.StockService;
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
        return new FilterByNome(query).applyFilter(stocks);
    }

    // Metodo  per applicare filtri ai prodotti
    public List<Stock> applicaFiltri(int idInventario, String query, String categoria, boolean disponibilita, boolean sortAsc, boolean sortDesc) {
        List<Stock> stocks = stockService.getStockByInventario(idInventario);
        IFilterStrategy filtro = FilterFactory.createFilter(query, categoria, disponibilita, sortAsc, sortDesc);
        return filtro.applyFilter(stocks);
    }

    // Metodo  per cercare distributori alternativi con il prodotto specificato
    public List<Distributore> cercaDistributoriConProdotto(int idDistributoreCorrente, String nomeProdotto) {
        return distributoreService.getDistributoriConProdottoDisponibileByName(idDistributoreCorrente, nomeProdotto);
    }
}
