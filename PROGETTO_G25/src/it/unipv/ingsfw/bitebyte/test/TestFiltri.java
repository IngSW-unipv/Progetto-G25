package it.unipv.ingsfw.bitebyte.test;

import it.unipv.ingsfw.bitebyte.models.*;
import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.filtri.FilterByDisponibilità;
import it.unipv.ingsfw.bitebyte.filtri.FilterByNome;
import it.unipv.ingsfw.bitebyte.filtri.FilterByPrezzo;
import it.unipv.ingsfw.bitebyte.filtri.CompositeFilter;
import it.unipv.ingsfw.bitebyte.filtri.FilterByCategoria;
import it.unipv.ingsfw.bitebyte.types.Categoria;

import java.util.ArrayList;
import java.util.List;

public class TestFiltri {
    public static void main(String[] args) {
        // Crea una connessione al database e recupera i dati dal DAO
        StockDAO stockDAO = new StockDAO();
        List<Stock> stocks = stockDAO.getStockByInventario(2); // Cambia l'ID dell'inventario se necessario

        System.out.println("Tutti i prodotti:");
        for (Stock stock : stocks) {
            System.out.println(stock);
        }

        // Applica i filtri

        // Filtro per disponibilità
        FilterByDisponibilità filtroDisponibilità = new FilterByDisponibilità();
        List<Stock> stockDisponibili = filtroDisponibilità.applyFilter(stocks);
        System.out.println("\nProdotti disponibili (quantità > 0):");
        for (Stock stock : stockDisponibili) {
            System.out.println(stock);
        }

        // Filtro per nome
        FilterByNome filtroNome = new FilterByNome("cola");
        List<Stock> stockPerNome = filtroNome.applyFilter(stocks);
        System.out.println("\nProdotti con nome 'cola':");
        for (Stock stock : stockPerNome) {
            System.out.println(stock);
        }

     // Filtro per ordinamento decrescente
        FilterByPrezzo filtroDecrescente = new FilterByPrezzo(false);
        List<Stock> prodottiOrdinatiDecrescente = filtroDecrescente.applyFilter(stocks);

        System.out.println("\nProdotti ordinati in ordine decrescente di prezzo:");
        for (Stock stock : prodottiOrdinatiDecrescente) {
        	System.out.println(stock);
        }

        // Filtro per categoria
        FilterByCategoria filtroCategoria = new FilterByCategoria(Categoria.BEVANDA_FREDDA);
        List<Stock> stockPerCategoria = filtroCategoria.applyFilter(stocks);
        System.out.println("\nProdotti nella categoria 'BEVANDA_FREDDA':");
        for (Stock stock : stockPerCategoria) {
            System.out.println(stock);
        }
       
        
   

                // Creiamo il filtro composito
                CompositeFilter composito = new CompositeFilter();
                composito.addFilter(filtroNome);  // Aggiungi il filtro per nome
                composito.addFilter(filtroCategoria);  // Aggiungi il filtro per categoria

                // Applichiamo il filtro composito
                List<Stock> filteredStocks = composito.applyFilter(stocks);

                // Stampa i risultati
                System.out.println("\nProdotti filtrati con nome 'cola' e categoria 'BEVANDA_FREDDA':");
                for (Stock stock : filteredStocks) {
                    System.out.println(stock);
                }
            
        

    }
}




