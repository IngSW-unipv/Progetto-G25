package it.unipv.ingsfw.bitebyte.filtri;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.ArrayList;
import java.util.List;

public class CompositeFilter implements IFilterStrategy {
    private List<IFilterStrategy> filters = new ArrayList<>();

    public void addFilter(IFilterStrategy filter) {
        filters.add(filter);
    }

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        List<Stock> filteredStocks = stocks;
        for (IFilterStrategy filter : filters) {
            filteredStocks = filter.applyFilter(filteredStocks);
        }
        return filteredStocks;
    }
}
