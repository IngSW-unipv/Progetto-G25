
package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.ArrayList;
import java.util.List;

public class FilterByDisponibilità implements IFilterStrategy {

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        List<Stock> stockFiltrati = new ArrayList<>();

        // Filtro i prodotti che sono disponibili sia in termini di quantità che di stato
        for (Stock stock : stocks) {
            if (stock.getQuantitaDisp() > 0 && "Disponibile".equals(stock.getStato())) {
                stockFiltrati.add(stock);
            }
        }

        return stockFiltrati;
    }
}
