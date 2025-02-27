package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.filtri.CompositeFilter;
import it.unipv.ingsfw.bitebyte.filtri.FilterByDisponibilità;
import it.unipv.ingsfw.bitebyte.filtri.FilterByNome;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DistributoreCompletoService {
    
    private DistributoreDAO distributoreDAO = new DistributoreDAO();
    private StockDAO stockDAO = new StockDAO();
    
 
    /**
     * Recupera il distributore per ID e associa la lista degli stock.
     * @param idDistributore L'ID del distributore da recuperare.
     * @return Un oggetto Distributore completo, con la lista degli stock associata.
     */
    public Distributore getDistributoreById(int idDistributore) {
        Distributore distributore = distributoreDAO.getDistributoreById(idDistributore);
        if (distributore != null) {
            List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
            distributore.setStockList(stockList);
        }
        return distributore;
    }

    /**
     * Recupera tutti i distributori e per ognuno associa la lista degli stock.
     * @return Una lista di distributori completi.
     */
    public List<Distributore> getAllDistributori() {
        List<Distributore> distributori = distributoreDAO.getAllDistributori();
        for (Distributore d : distributori) {
            List<Stock> stockList = stockDAO.getStockByInventario(d.getIdInventario());
            d.setStockList(stockList);
        }
        return distributori;
    }

    
    /**
     * Restituisce i distributori (della stessa tipologia) che possiedono almeno uno stock
     * in cui il nome del prodotto contiene 'nomeProdotto' e la quantità disponibile è > 0.
     * Viene escluso il distributore corrente.
     * La lista è ordinata per distanza dal distributore corrente.
     */
    public List<Distributore> getDistributoriConProdottoDisponibileByName(int idDistributoreCorrente, String nomeProdotto) {
        // Recupera il distributore corrente
        Distributore corrente = getDistributoreById(idDistributoreCorrente);
        if (corrente == null) return new ArrayList<>();

        // Recupera tutti i distributori
        List<Distributore> tuttiDistributori = getAllDistributori();
        // Filtra per tipologia ed escludi il distributore corrente utilizzando un ciclo for
        List<Distributore> distributoriTipologia = new ArrayList<>();
        for (Distributore d : tuttiDistributori) {
            if (d.getTipo().equals(corrente.getTipo()) && d.getIdDistr() != corrente.getIdDistr()) {
                distributoriTipologia.add(d);
            }
        }

        // Imposta il filtro composito: per nome e disponibilità
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter(new FilterByNome(nomeProdotto)); // restituirà solo gli stock che contengono la sottostringa specificata nel nome
        compositeFilter.addFilter(new FilterByDisponibilità());

        // Applica il filtro composito a ciascun distributore della lista filtrata per tipologia
        //se dopo il filtraggio il suo stock non è vuoto allora quel distributore può essere inserito nella lista dei dsitributori disponibili
        List<Distributore> distributoriDisponibili = new ArrayList<>();
        for (Distributore d : distributoriTipologia) {
            List<Stock> stockFiltrati = compositeFilter.applyFilter(d.getStockList());
            if (!stockFiltrati.isEmpty()) {     //Se la lista stockFiltrati non è vuota
                d.setStockList(stockFiltrati); // Aggiorna la lista degli stock con quelli filtrati
                distributoriDisponibili.add(d);
            }
        }

        // Ordina i distributori per distanza dal distributore corrente usando Collections.sort
        //Collections.sort() dispone gli elementi in base al risultato del comparatore, 
        //se il comparatore restituisce un valore negativo, l'elemento viene collocato prima
        Collections.sort(distributoriDisponibili, new Comparator<Distributore>() {
            @Override
            public int compare(Distributore d1, Distributore d2) {
                double dist1 = CalcolaDistanza.calcolaDistanza(corrente, d1);
                double dist2 = CalcolaDistanza.calcolaDistanza(corrente, d2);
                return Double.compare(dist1, dist2);
            }
        });

        return distributoriDisponibili;
    }

 
    
    public List<Stock> getStockByDistributore(int idDistributore) {
        Distributore distributore = getDistributoreById(idDistributore);
        return stockDAO.getStockByInventario(distributore.getIdInventario());
    }
    
    
    /**
     * Aggiorna il distributore e, successivamente, aggiorna ciascuno degli stock associati.
     * 
     * @param distributore il distributore da aggiornare
     */
    public void updateDistributore(Distributore distributore) {
        // Aggiorna i dati del distributore tramite il DAO
        distributoreDAO.updateDistributore(distributore);
        
        // Dopo aver aggiornato il distributore, aggiorna ciascun stock associato
        if (distributore.getStockList() != null) {
            for (Stock stock : distributore.getStockList()) {
                stockDAO.updateStock(stock);
            }
        }
    }
    

    
    public void addDistributore(Distributore distributore) {
        distributoreDAO.addDistributore(distributore);
    }
    
  
    
    public void deleteDistributore(int idDistributore) {
        distributoreDAO.deleteDistributore(idDistributore);
    }
    
   
}
