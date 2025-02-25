package it.unipv.ingsfw.bitebyte.filtri;

import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public class FilterFactory {

    /**
     * Crea un filtro composito basato sui parametri.
     *
     * @param nomeProdotto   testo di ricerca per il nome del prodotto
     * @param selectedCategory   categoria selezionata (stringa)
     * @param availability   flag per filtrare solo prodotti disponibili
     * @param sortAsc        se true, ordina per prezzo crescente
     * @param sortDesc       se true, ordina per prezzo decrescente
     * @return un filtro composito che implementa IFilterStrategy
     */
    public static IFilterStrategy createFilter(String nomeProdotto, String selectedCategory, boolean availability, boolean sortAsc, boolean sortDesc) {
        CompositeFilter compositeFilter = new CompositeFilter();

        // Applica il filtro per nome se il campo non è vuoto
        if (nomeProdotto != null && !nomeProdotto.trim().isEmpty()) {
            compositeFilter.addFilter(new FilterByNome(nomeProdotto));
        }

        // Applica il filtro per categoria se selezionata
        if (selectedCategory != null && !selectedCategory.trim().isEmpty()) {
            try {
                Categoria categoriaEnum = Categoria.valueOf(selectedCategory.toUpperCase().replace(" ", "_"));
                compositeFilter.addFilter(new FilterByCategoria(categoriaEnum));
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: Categoria non valida -> " + selectedCategory);
            }
        }

        // Applica il filtro per disponibilità
        if (availability) {
            compositeFilter.addFilter(new FilterByDisponibilità());
        }

        /*
         * Per l'ordinamento, è importante che questo filtro venga applicato alla fine,
         * dopo tutti i filtri che potrebbero rimuovere elementi.
         * Nota: se entrambi sortAsc e sortDesc fossero true (caso anomalo), potresti decidere una priorità.
         */
        if (sortAsc) {
            compositeFilter.addFilter(new FilterByPrezzo(true));
        } else if (sortDesc) {
            compositeFilter.addFilter(new FilterByPrezzo(false));
        }

        return compositeFilter;
    }
}
