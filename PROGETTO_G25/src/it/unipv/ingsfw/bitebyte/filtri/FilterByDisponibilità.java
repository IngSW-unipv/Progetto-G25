package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.ArrayList;
import java.util.List;

public class FilterByDisponibilità implements IFilterStrategy {

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        List<Stock> stockFiltrati = new ArrayList<>();

        // Filtro dei prodotti con almeno 1 unità disponibile
        for (Stock stock : stocks) {
            if (stock.getQuantitaDisp() > 0) {  // Verifica se la quantità disponibile è maggiore di zero
                stockFiltrati.add(stock);
            }
        }

        return stockFiltrati;
    }
}
