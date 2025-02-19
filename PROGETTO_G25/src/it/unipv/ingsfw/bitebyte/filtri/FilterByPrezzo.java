package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilterByPrezzo implements IFilterStrategy {
    private boolean ordinamentoCrescente;

    // Costruttore con parametro per l'ordinamento
    public FilterByPrezzo(boolean ordinamentoCrescente) {
        this.ordinamentoCrescente = ordinamentoCrescente;
    }

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        // Ordinamento dei prodotti in base al prezzo
        if (ordinamentoCrescente) {
            Collections.sort(stocks, new Comparator<Stock>() {
                @Override
                public int compare(Stock stock1, Stock stock2) {
                    return stock1.getProdotto().getPrezzo().compareTo(stock2.getProdotto().getPrezzo());
                }
            });
        } else {
            Collections.sort(stocks, new Comparator<Stock>() {
                @Override
                public int compare(Stock stock1, Stock stock2) {
                    return stock2.getProdotto().getPrezzo().compareTo(stock1.getProdotto().getPrezzo());
                }
            });
        }

        return stocks;
    }
}



