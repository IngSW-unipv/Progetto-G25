package it.unipv.ingsfw.bitebyte.filtri;
import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.List;

public interface IFilterStrategy {

    List<Stock> applyFilter(List<Stock> stocks);
}
