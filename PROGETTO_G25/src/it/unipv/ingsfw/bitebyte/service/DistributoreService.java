package it.unipv.ingsfw.bitebyte.service;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.filtri.CompositeFilter;
import it.unipv.ingsfw.bitebyte.filtri.FilterByDisponibilità;
import it.unipv.ingsfw.bitebyte.filtri.FilterByNome;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DistributoreService {
    
    private DistributoreDAO distributoreDAO = new DistributoreDAO();
    private StockDAO stockDAO = new StockDAO();
    
    public Distributore getDistributoreById(int idDistributore) {
        return distributoreDAO.getDistributoreById(idDistributore);
    }
    
    public List<Distributore> getAllDistributori() {
        return distributoreDAO.getAllDistributori();
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

        // Recupera tutti i distributori e filtra per tipologia ed escludi il corrente
        List<Distributore> distributoriTipologia = getAllDistributori().stream()
            .filter(d -> d.getTipo().equals(corrente.getTipo()) && d.getIdDistr() != corrente.getIdDistr())
            .collect(Collectors.toList());

        // Imposta il filtro composito: per nome e disponibilità
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter(new FilterByNome(nomeProdotto));
        compositeFilter.addFilter(new FilterByDisponibilità());

        List<Distributore> distributoriDisponibili = new ArrayList<>();
        for (Distributore d : distributoriTipologia) {
            // Applica il filtro sulla lista degli stock di questo distributore
            List<Stock> stockFiltrati = compositeFilter.applyFilter(d.getStockList());
            if (!stockFiltrati.isEmpty()) {
                d.setStockList(stockFiltrati); // Aggiorna la lista con gli stock filtrati
                distributoriDisponibili.add(d);
            }
        }

        // Ordina i distributori per distanza dal distributore corrente
        distributoriDisponibili.sort(Comparator.comparingDouble(d ->
            CalcolaDistanza.calcolaDistanza(corrente, d)
        ));

        return distributoriDisponibili;
    }
    
    
   
    public List<Stock> getStockByDistributore(int idDistributore) {
        Distributore distributore = getDistributoreById(idDistributore);
        return stockDAO.getStockByInventario(distributore.getIdInventario());
    }
    
    
    
    
    public void addDistributore(Distributore distributore) {
        distributoreDAO.addDistributore(distributore);
    }
    
    public void updateDistributore(Distributore distributore) {
        distributoreDAO.updateDistributore(distributore);
    }
    
    public void deleteDistributore(int idDistributore) {
        distributoreDAO.deleteDistributore(idDistributore);
    }
    
   
}
