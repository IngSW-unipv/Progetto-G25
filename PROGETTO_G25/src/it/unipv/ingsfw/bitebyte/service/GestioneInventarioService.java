package it.unipv.ingsfw.bitebyte.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.strategyforn.DiscountFactory;
import it.unipv.ingsfw.bitebyte.strategyforn.IDiscountStrategy;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class GestioneInventarioService {
    
    private StockService stockService;
    private FornituraService fornituraService;
    private ProdottoService prodottoService;
    private DistributoreService distributoreService;
    private SpedizioneService spedizioneService;

    public GestioneInventarioService(StockService stockService, FornituraService fornituraService, 
                                     ProdottoService prodottoService, DistributoreService distributoreService,
                                     SpedizioneService spedizioneService) {
        this.stockService = stockService;
        this.fornituraService = fornituraService;
        this.prodottoService = prodottoService;
        this.distributoreService = distributoreService;
        this.spedizioneService = spedizioneService;
    }

    public List<Distributore> getDistributori() {
        return distributoreService.getAllDistributori();
    }

    public ArrayList<Stock> getProdottiByInventario(int idInventario) {
        return stockService.getStockByInventario(idInventario);
    }

    public ArrayList<Fornitura> getFornitoriByStock (Stock stock) {
        return fornituraService.getFornitoriInfo(stock);
    }
    
    public ArrayList<Spedizione> getAllSpedizioni() {
    	return spedizioneService.getAllSpedizioni();
    }
    
    public ArrayList<Prodotto> getProdottiByCategoria(Stock stock, Categoria categoria) {
    	return prodottoService.getProdottiByCategoria(stock, categoria);
    }
    
    public ArrayList<Stock> getStockByInventario(int idInventario) {
		return stockService.getStockByInventario(idInventario);
	}
    
    public void handleSostituzione(Stock stock, Prodotto prodottoSostitutivo) {
        // Sostituzione del prodotto nello stock
        stockService.sostituisciStock(stock, prodottoSostitutivo.getIdProdotto());
    }
    
    public boolean handleCambioPrezzo(Stock stock, BigDecimal nuovoPrezzo) {
        if (nuovoPrezzo.compareTo(BigDecimal.ZERO) <= 0 || nuovoPrezzo.compareTo(new BigDecimal("5.00")) > 0) {
            throw new IllegalArgumentException("Il prezzo deve essere compreso tra €0.01 e €5.00!");
        }

        return stockService.updatePrice(stock.getProdotto().getIdProdotto(), stock.getIdInventario(), nuovoPrezzo);
    }
    
    public void handleRestock(Stock stock, Fornitura fornitura, int quantita) {
        int disponibile = stock.getQuantitaDisp();
        int maxInseribile = stock.getQMaxInseribile();

        // Controllo validità quantità
        if (quantita <= 0) {
            throw new IllegalArgumentException("Inserisci una quantità valida.");
        }
        if (quantita + disponibile > maxInseribile) {
            throw new IllegalArgumentException("Quantità non disponibile! Puoi ordinare al massimo " + (maxInseribile - disponibile) + " unità.");
        }
        BigDecimal finalPrice = calcolaPrezzoScontato(fornitura, quantita, stock);
        // Aggiungi al carrello
        Carrello carrello = Carrello.getInstance();
        carrello.aggiungiItem(fornitura, quantita, finalPrice);
        // Aggiorna lo stock
        stock.setQuantitaDisp(disponibile + quantita);
        stockService.updateStock(stock);
    }


    public BigDecimal calcolaPrezzoScontato(Fornitura fornitura, int quantita, Stock stock) {
        String strategyKey = (quantita == stock.getQMaxInseribile()) ? "maxquantity.strategy" : "quantity.strategy";
        IDiscountStrategy discountStrategy = DiscountFactory.getDiscountStrategy(strategyKey);
        if (discountStrategy == null) {
            throw new RuntimeException("Errore nel caricamento della strategia di sconto.");
        }
        SupplyContext supplyContext = new SupplyContext(discountStrategy, fornitura.getPpu());
        return supplyContext.calculateFinalPrice(quantita, stock);
    }

    public void sostituisciProdotto(Stock stock, int nuovoProdottoId) {
        stockService.sostituisciStock(stock, nuovoProdottoId);
    }

    public boolean aggiornaPrezzo(int prodottoId, int inventarioId, BigDecimal nuovoPrezzo) {
        return stockService.updatePrice(prodottoId, inventarioId, nuovoPrezzo);
    }
    
  
    // Nuovo metodo per concludere l'ordine e salvare la spedizione
    public void concludiOrdine(Carrello carrello, String idSpedizione, 
                               Map<Integer, Integer> quantitaTotalePerProdotto,
                               Map<Integer, BigDecimal> prezzoTotalePerProdotto) {

        // Distribuisci le quantità tra gli inventari
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
        salvaSpedizione(idSpedizione, quantitaTotalePerProdotto, prezzoTotalePerProdotto);
    }

    // Metodo per salvare la spedizione
    private void salvaSpedizione(String idSpedizione, Map<Integer, Integer> quantitaTotalePerProdotto, Map<Integer, BigDecimal> prezzoTotalePerProdotto) {
        for (Integer idProdotto : quantitaTotalePerProdotto.keySet()) {
            int quantita = quantitaTotalePerProdotto.get(idProdotto);
            BigDecimal prezzoTotale = prezzoTotalePerProdotto.get(idProdotto);
            spedizioneService.salvaSpedizione(idSpedizione, idProdotto, quantita, prezzoTotale);
        }
    }
    
    public String generaIdSpedizione() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    

}
