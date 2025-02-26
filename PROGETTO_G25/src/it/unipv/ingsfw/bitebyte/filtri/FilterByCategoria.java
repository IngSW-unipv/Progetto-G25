
package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import java.util.ArrayList;
import java.util.List;

public class FilterByCategoria implements IFilterStrategy {
    private Categoria categoria;

    // Costruttore che prende la categoria come parametro
    public FilterByCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        List<Stock> filteredStocks = new ArrayList<>();
        
        // Scorriamo tutti gli elementi della lista originale
        for (Stock stock : stocks) {
            // Se la categoria del prodotto corrisponde, aggiungiamo lo stock alla lista filtrata
            if (stock.getProdotto().getCategoria() == categoria) {
                filteredStocks.add(stock);
            }
        }

        return filteredStocks; // Restituiamo la lista filtrata
    }
}

