package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByCategoria implements IFilterStrategy{
    private Categoria categoria;

    // Costruttore che prende la categoria
    public FilterByCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        return stocks.stream()
                .filter(stock -> stock.getProdotto().getCategoria() == categoria)
                .collect(Collectors.toList());
    }
}

