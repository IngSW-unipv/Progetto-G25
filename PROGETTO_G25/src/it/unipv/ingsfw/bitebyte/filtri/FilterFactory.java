package it.unipv.ingsfw.bitebyte.filtri;
import it.unipv.ingsfw.bitebyte.types.Categoria;

public class FilterFactory {

    /**
     * Questa factory permette di generare automaticamente un filtro personalizzato in base ai parametri scelti dall'utente.
     *
     * @param nomeProdotto   testo di ricerca per il nome del prodotto
     * @param selectedCategory   categoria selezionata (stringa)
     * @param availability   flag per filtrare solo prodotti disponibili
     * @param sortAsc        se true, ordina per prezzo crescente
     * @param sortDesc       se true, ordina per prezzo decrescente
     * @return IFilterStrategy  che potrebbe essere un CompositeFilter o un singolo filtro.
     */
    public static IFilterStrategy createFilter(String nomeProdotto, String selectedCategory, boolean availability, boolean sortAsc, boolean sortDesc) {
        CompositeFilter compositeFilter = new CompositeFilter();

        // Applica il filtro per nome se il campo non è vuoto.
        //Se l'utente scrive solo spazi, il filtro non deve essere applicato.
        if (nomeProdotto != null && !nomeProdotto.trim().isEmpty()) {
            compositeFilter.addFilter(new FilterByNome(nomeProdotto));
        }

        // Applica il filtro per categoria se selezionata
        
       //L'utente seleziona una categoria da un'interfaccia grafica (ComboBox), e il valore restituito è una String.
       // L'oggetto Categoria è un enum, quindi bisogna trasformare la stringa in un valore valido dell'enum.
        if (selectedCategory != null && !selectedCategory.trim().isEmpty()) {
            try {
                Categoria categoriaEnum = Categoria.valueOf(selectedCategory.toUpperCase().replace(" ", "_"));
                //oppure si potrebbe utilizzare il metodo implementato da Davide: Categoria categoriaEnum = Categoria.fromDatabaseValue(selectedCategory);
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
         * dopo tutti i filtri che potrebbero rimuovere elementi cosìm da ordinare i prodotti già filtrati
         */
        if (sortAsc) {
            compositeFilter.addFilter(new FilterByPrezzo(true));
        } else if (sortDesc) {
            compositeFilter.addFilter(new FilterByPrezzo(false));
        }

        return compositeFilter;
    }
}
