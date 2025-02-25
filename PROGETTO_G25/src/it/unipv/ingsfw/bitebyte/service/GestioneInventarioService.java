package it.unipv.ingsfw.bitebyte.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.ItemCarrello;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.strategyforn.DiscountFactory;
import it.unipv.ingsfw.bitebyte.strategyforn.IDiscountStrategy;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * Servizio per la gestione dell'inventario e delle operazioni relative ai prodotti,
 * alle forniture, agli stock e alle spedizioni.
 * Gestisce le operazioni su prodotti, distributori, forniture, e spedizioni, interagendo
 * con altri servizi come StockService, FornituraService, ProdottoService, DistributoreService
 * e SpedizioneService.
 */
public class GestioneInventarioService {
    
    private StockService stockService;
    private FornituraService fornituraService;
    private ProdottoService prodottoService;
    private DistributoreService distributoreService;
    private SpedizioneService spedizioneService;

    /**
     * Costruttore del servizio GestioneInventarioService.
     * Inizializza i servizi per la gestione degli stock, delle forniture, dei prodotti,
     * dei distributori e delle spedizioni.
     *
     * @param stockService Il servizio per la gestione degli stock.
     * @param fornituraService Il servizio per la gestione delle forniture.
     * @param prodottoService Il servizio per la gestione dei prodotti.
     * @param distributoreService Il servizio per la gestione dei distributori.
     * @param spedizioneService Il servizio per la gestione delle spedizioni.
     */
    public GestioneInventarioService(StockService stockService, FornituraService fornituraService, 
                                     ProdottoService prodottoService, DistributoreService distributoreService,
                                     SpedizioneService spedizioneService) {
        this.stockService = stockService;
        this.fornituraService = fornituraService;
        this.prodottoService = prodottoService;
        this.distributoreService = distributoreService;
        this.spedizioneService = spedizioneService;
    }

    /**
     * Recupera tutti i distributori.
     *
     * @return Una lista di distributori.
     */
    public List<Distributore> getDistributori() {
        return distributoreService.getAllDistributori();
    }

    /**
     * Recupera gli stock di prodotti per un determinato inventario.
     *
     * @param idInventario L'ID dell'inventario.
     * @return Una lista di oggetti {@link Stock} appartenenti all'inventario specificato.
     */
    public ArrayList<Stock> getProdottiByInventario(int idInventario) {
        return stockService.getStockByInventario(idInventario);
    }

    /**
     * Recupera le informazioni sui fornitori per uno specifico prodotto in stock.
     *
     * @param stock L'oggetto {@link Stock} per cui recuperare i fornitori.
     * @return Una lista di oggetti {@link Fornitura} che rappresentano i fornitori.
     */
    public ArrayList<Fornitura> getFornitoriByStock(Stock stock) {
        return fornituraService.getFornitoriInfo(stock);
    }

    /**
     * Recupera tutte le spedizioni.
     *
     * @return Una lista di oggetti {@link Spedizione}.
     */
    public ArrayList<Spedizione> getAllSpedizioni() {
        return spedizioneService.getAllSpedizioni();
    }

    /**
     * Recupera i prodotti di una determinata categoria per uno specifico stock.
     *
     * @param stock L'oggetto {@link Stock} per cui recuperare i prodotti.
     * @param categoria La categoria dei prodotti da recuperare.
     * @return Una lista di oggetti {@link Prodotto} appartenenti alla categoria specificata.
     */
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) {
        return prodottoService.getProdottiByCategoria(stock, categoria);
    }

    /**
     * Recupera gli stock di un inventario specifico.
     *
     * @param idInventario L'ID dell'inventario.
     * @return Una lista di oggetti {@link Stock} per l'inventario specificato.
     */
    public ArrayList<Stock> getStockByInventario(int idInventario) {
        return stockService.getStockByInventario(idInventario);
    }

    /**
     * Gestisce la sostituzione di un prodotto nello stock.
     *
     * @param stock L'oggetto {@link Stock} in cui sostituire il prodotto.
     * @param prodottoSostitutivo Il prodotto che sostituirà l'attuale prodotto nello stock.
     */
    public void handleSostituzione(Stock stock, Prodotto prodottoSostitutivo) {
        stockService.sostituisciStock(stock, prodottoSostitutivo.getIdProdotto());
    }

    /**
     * Gestisce il cambiamento del prezzo di un prodotto in stock.
     *
     * @param stock L'oggetto {@link Stock} che contiene il prodotto.
     * @param nuovoPrezzo Il nuovo prezzo da assegnare al prodotto.
     * @return True se l'aggiornamento del prezzo è andato a buon fine, altrimenti false.
     * @throws IllegalArgumentException Se il nuovo prezzo è fuori dal range valido.
     */
    public boolean handleCambioPrezzo(Stock stock, BigDecimal nuovoPrezzo) {
        if (nuovoPrezzo.compareTo(BigDecimal.ZERO) <= 0 || nuovoPrezzo.compareTo(new BigDecimal("5.00")) > 0) {
            throw new IllegalArgumentException("Il prezzo deve essere compreso tra €0.01 e €5.00!");
        }

        return stockService.updatePrice(stock.getProdotto().getIdProdotto(), stock.getIdInventario(), nuovoPrezzo);
    }

    /**
     * Gestisce il restock di un prodotto nello stock.
     * Controlla la quantità disponibile e aggiorna il carrello se necessario.
     *
     * @param stock L'oggetto {@link Stock} che rappresenta il prodotto.
     * @param fornitura L'oggetto {@link Fornitura} associato al prodotto.
     * @param quantita La quantità da aggiungere allo stock.
     */
    public void handleRestock(Stock stock, Fornitura fornitura, int quantita) {
        Carrello carrello = Carrello.getInstance();
        int quantitaNelCarrello = 0;
        // Somma la quantità presente nel carrello per lo stesso prodotto
        for (ItemCarrello item : carrello.getItems()) {
            if (item.getFornitura().getProdotto().getIdProdotto() == stock.getProdotto().getIdProdotto()) {
                quantitaNelCarrello += item.getQuantita();
            }
        }
        int maxInseribile = stock.getQMaxInseribile();
        int quantitaDisponibile = stock.getQuantitaDisp();
        // Controlli sulla quantità
        if (quantita <= 0) {
            throw new IllegalArgumentException("Inserisci una quantità valida.");
        }
        if (quantita + quantitaNelCarrello + quantitaDisponibile > maxInseribile) {
            throw new IllegalArgumentException("Quantità non disponibile! Puoi ordinare al massimo " + (maxInseribile - quantitaNelCarrello - quantitaDisponibile) + " unità.");
        }
        // Calcolo del prezzo finale con eventuali sconti
        BigDecimal finalPrice = calcolaPrezzoScontato(fornitura, quantita, stock);
        // Aggiungi l'elemento al carrello
        carrello.aggiungiItem(fornitura, quantita, finalPrice);
    }

    /**
     * Calcola il prezzo scontato di un prodotto in base alla strategia di sconto.
     *
     * @param fornitura L'oggetto {@link Fornitura} che rappresenta la fornitura del prodotto.
     * @param quantita La quantità del prodotto.
     * @param stock L'oggetto {@link Stock} che contiene informazioni sul prodotto.
     * @return Il prezzo finale del prodotto con sconto applicato.
     */
    public BigDecimal calcolaPrezzoScontato(Fornitura fornitura, int quantita, Stock stock) {
        String strategyKey = (quantita == stock.getQMaxInseribile()) ? "maxquantity.strategy" : "quantity.strategy";
        IDiscountStrategy discountStrategy = DiscountFactory.getDiscountStrategy(strategyKey);
        if (discountStrategy == null) {
            throw new RuntimeException("Errore nel caricamento della strategia di sconto.");
        }
        SupplyContext supplyContext = new SupplyContext(discountStrategy, fornitura.getPpu());
        return supplyContext.calculateFinalPrice(quantita, stock);
    }

    /**
     * Sostituisce un prodotto con un altro all'interno dello stock.
     *
     * @param stock L'oggetto {@link Stock} da aggiornare.
     * @param nuovoProdottoId L'ID del nuovo prodotto che sostituirà l'attuale.
     */
    public void sostituisciProdotto(Stock stock, int nuovoProdottoId) {
        stockService.sostituisciStock(stock, nuovoProdottoId);
    }

    /**
     * Aggiorna il prezzo di un prodotto in un inventario specifico.
     *
     * @param prodottoId L'ID del prodotto da aggiornare.
     * @param inventarioId L'ID dell'inventario.
     * @param nuovoPrezzo Il nuovo prezzo da applicare al prodotto.
     * @return True se l'aggiornamento è riuscito, altrimenti false.
     */
    public boolean aggiornaPrezzo(int prodottoId, int inventarioId, BigDecimal nuovoPrezzo) {
        return stockService.updatePrice(prodottoId, inventarioId, nuovoPrezzo);
    }

    /**
     * Conclude l'ordine, aggiornando lo stock e salvando la spedizione.
     *
     * @param carrello L'oggetto {@link Carrello} che contiene gli articoli acquistati.
     */
    public void concludiOrdine(Carrello carrello) {
        Map<Integer, Integer> quantitaTotalePerProdotto = new HashMap<>(); //mappa che associa prodotto e quantita totale nel carrello
        Map<Integer, BigDecimal> prezzoTotalePerProdotto = new HashMap<>(); //mappa che associa prodotto e prezzo totale nel carrello
        for (ItemCarrello item : carrello.getItems()) {
            int idProdotto = item.getFornitura().getProdotto().getIdProdotto();
            int quantita = item.getQuantita();
            BigDecimal prezzo = item.getPrezzoTotale();
            
            quantitaTotalePerProdotto.put(idProdotto, quantitaTotalePerProdotto.getOrDefault(idProdotto, 0) + quantita);
            prezzoTotalePerProdotto.put(idProdotto, prezzoTotalePerProdotto.getOrDefault(idProdotto, BigDecimal.ZERO).add(prezzo));
        }
        // Distribuisci le quantità tra gli inventari che contengono quel prodotto
        for (Map.Entry<Integer, Integer> entry : quantitaTotalePerProdotto.entrySet()) {
            int idProdotto = entry.getKey();
            int quantitaTotale = entry.getValue();
            // Inventari che contengono il prodotto
            for (Stock stock : stockService.getStockByProdotto(idProdotto)) {
                // Calcoliamo la quantità che possiamo aggiungere all'inventario
                int quantitaDisponibile = stock.getQMaxInseribile() - stock.getQuantitaDisp();
                int quantitaDaDistribuire = Math.min(quantitaTotale, quantitaDisponibile);
                // Aggiorna la quantità nello stock
                stock.setQuantitaDisp(stock.getQuantitaDisp() + quantitaDaDistribuire);
                stockService.updateStock(stock);
                quantitaTotale -= quantitaDaDistribuire;
                if (quantitaTotale <= 0) {
                    break;
                }
            }
        }
        // Salva la spedizione
        spedizioneService.salvaSpedizione(quantitaTotalePerProdotto, prezzoTotalePerProdotto);
    }
}
