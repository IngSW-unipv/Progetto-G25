package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import java.util.ArrayList;
import java.util.List;

public class FilterByNome implements IFilterStrategy {
    private String nomeProdotto;  // Nome del prodotto per il filtro

    // Costruttore con nome del prodotto
    public FilterByNome(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto.toLowerCase();  // Convertiamo il nome in minuscolo per la ricerca case-insensitive
    }

    @Override
    public List<Stock> applyFilter(List<Stock> stocks) {
        List<Stock> stockFiltrati = new ArrayList<>();

        // Filtro dei prodotti in base al nome
        for (Stock stock : stocks) {
            String nome = stock.getProdotto().getNome().toLowerCase();  // Otteniamo il nome del prodotto in minuscolo
            if (nome.contains(nomeProdotto)) {  // Verifica se il nome contiene la sottostringa
             stockFiltrati.add(stock);
            }
        }

        return stockFiltrati;
    }
}




