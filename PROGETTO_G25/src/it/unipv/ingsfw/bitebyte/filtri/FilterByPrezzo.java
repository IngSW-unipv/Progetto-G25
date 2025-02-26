package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilterByPrezzo implements IFilterStrategy {
    private boolean ordinamentoCrescente;

    // Costruttore con parametro per l'ordinamento
    //Riceve un valore booleano che indica se l'ordinamento deve essere crescente (true) o decrescente (false).

    public FilterByPrezzo(boolean ordinamentoCrescente) {
        this.ordinamentoCrescente = ordinamentoCrescente;
    }

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        // Ordinamento dei prodotti in base al prezzo
    	//Se ordinamentoCrescente == true
        if (ordinamentoCrescente) {
        	//Collections.sort(lista, criterio)
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



/*   Normalmente, se volessimo creare un comparatore per Stock, dovremmo scrivere una classe separata così:
 * 
    class ComparatoreStock implements Comparator<Stock> {
 
    @Override
    public int compare(Stock stock1, Stock stock2) {
        return stock1.getProdotto().getPrezzo().compareTo(stock2.getProdotto().getPrezzo());
    }
}

   Poi, per usarla: Collections.sort(stocks, new ComparatoreStock());

*/

