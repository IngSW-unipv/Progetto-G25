package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.business.SupplyContext;
import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.dao.FornituraDAO;
import it.unipv.ingsfw.bitebyte.dao.ProdottoDAO;
import it.unipv.ingsfw.bitebyte.dao.SpedizioneDAO;
import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.ItemCarrello;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.strategyforn.IDiscountStrategy;
import it.unipv.ingsfw.bitebyte.strategyforn.DiscountFactory;
import it.unipv.ingsfw.bitebyte.view.CarrelloView;
import it.unipv.ingsfw.bitebyte.view.ModificaPrezzoView;
import it.unipv.ingsfw.bitebyte.view.ProdottiView;
import it.unipv.ingsfw.bitebyte.view.RifornimentoView;
import it.unipv.ingsfw.bitebyte.view.SostituzioneView;
import it.unipv.ingsfw.bitebyte.view.StoricoSpedizioniView;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GestionePController {

    private StockDAO stockDAO;
    private FornituraDAO fornituraDAO;
    private SpedizioneDAO spedizioneDAO;
    private DistributoreDAO distributoreDAO;
    private ProdottoDAO prodottoDAO; 
    private ProdottiView prodottiView;
    private int idInventario;

    // Aggiungi questa variabile membro per carrelloView
    private CarrelloView carrelloView;

    public GestionePController() {
        this.stockDAO = new StockDAO();
        this.spedizioneDAO = new SpedizioneDAO();
        this.fornituraDAO = new FornituraDAO();
        this.distributoreDAO = new DistributoreDAO();
        this.prodottoDAO = new ProdottoDAO();
        this.prodottiView = new ProdottiView(this); // Passiamo il controller alla view
        caricaDistributori();  
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        caricaProdotti();
    }
    
 // Metodo per caricare i distributori
    public void caricaDistributori() {
        List<Distributore> distributori = distributoreDAO.getAllDistributori(); // Otteniamo i distributori
        prodottiView.setDistributori(distributori); // Passiamo i distributori alla vista
    }
    
    public void caricaProdotti() {
        ArrayList<Stock> stocks = stockDAO.getStockByInventario(idInventario);
        prodottiView.aggiornaProdotti(stocks);
    }

    public ProdottiView getView() {
        return prodottiView;
    }

    public void handleRestock(Stock stock) {
        if (stock.getQuantitaDisp() == stock.getQMaxInseribile()) {
            mostraErrore("Slot prodotto già pieno");
            return;
        }

        System.out.println("🔄 Rifornimento per: " + stock.getProdotto().getNome());

        ArrayList<Fornitura> forniture = fornituraDAO.getFornitoriInfo(stock);

        RifornimentoView rifornimentoView = new RifornimentoView(forniture, stock, new RifornimentoView.RifornimentoListener() {

            @Override
            public void onFornitoreSelezionato(Fornitura fornitura, int quantita) {
                int disponibile = stock.getQuantitaDisp();
                int maxInseribile = stock.getQMaxInseribile();

                // ✅ Controllo validità quantità
                if (quantita <= 0) {
                    mostraErrore("Inserisci una quantità valida.");
                    return;
                }
                if (quantita + disponibile > maxInseribile) {
                    mostraErrore("Quantità non disponibile! Puoi ordinare al massimo " + (maxInseribile - disponibile) + " unità.");
                    return;
                }

                // ✅ Determina la strategia in base alla quantità massima
                String strategyKey = (quantita == maxInseribile) ? "maxquantity.strategy" : "quantity.strategy";

                // ✅ Creazione della strategia
                IDiscountStrategy discountStrategy = DiscountFactory.getDiscountStrategy(strategyKey);
                if (discountStrategy == null) {
                    mostraErrore("Errore nel caricamento della strategia di sconto.");
                    return;
                }

                // ✅ Calcolo del prezzo finale con la strategia di sconto applicata
                SupplyContext supplyContext = new SupplyContext(discountStrategy, fornitura.getPpu());
                BigDecimal finalPrice = supplyContext.calculateFinalPrice(quantita, stock);

                // ✅ Aggiungi l'elemento al carrello
                Carrello carrello = Carrello.getInstance();
                carrello.aggiungiItem(fornitura, quantita, finalPrice);

                // ✅ Aggiorna la vista del carrello subito
                aggiornaVistaCarrello();
                apriCarrello();
            }
        });
        rifornimentoView.mostra();
    }

    public void apriCarrello() {
        // Recupera l'istanza del carrello
        Carrello carrello = Carrello.getInstance();

        // Crea la vista del carrello (questa è un'altra view che mostrerà gli articoli nel carrello)
        carrelloView = new CarrelloView(carrello, this);
        carrelloView.aggiornaVistaCarrello();
        carrelloView.mostra();
    }
    
    public void apriStoricoSpedizioni() {
        // Recupera tutte le spedizioni dal DAO
        ArrayList<Spedizione> spedizioni = spedizioneDAO.getAllSpedizioni();

        // Crea la vista e passa le spedizioni
        StoricoSpedizioniView storicoView = new StoricoSpedizioniView();
        storicoView.mostra(spedizioni);
    }

    public void handleSostituzione(Stock stock) {
    	ArrayList<Prodotto> prodottiSostitutivi = prodottoDAO.getProdottiByCategoria(stock, stock.getProdotto().getCategoria());
    	new SostituzioneView(prodottiSostitutivi, prodottoSostituito -> {
            stockDAO.sostituisciStock(stock, prodottoSostituito.getIdProdotto());
            prodottiView.aggiornaProdotti(stockDAO.getStockByInventario(stock.getIdInventario()));
        });
    	System.out.println("🔁 Sostituzione per: " + stock.getProdotto().getNome());
    }

    public void handleCambioPrezzo(Stock stock) {
        ModificaPrezzoView view = new ModificaPrezzoView(stock, (prodotto, nuovoPrezzo) -> {
            if (nuovoPrezzo.compareTo(BigDecimal.ZERO) <= 0 || nuovoPrezzo.compareTo(new BigDecimal("5.00")) > 0) {
                mostraErrore("Il prezzo deve essere compreso tra €0.01 e €5.00!");
                return;
            }
            boolean aggiornato = stockDAO.updatePrice(stock.getProdotto().getIdProdotto(), idInventario, nuovoPrezzo);
            if (!aggiornato) {
                mostraErrore("❌ Errore durante l'aggiornamento del prezzo!");
                return;
            }

            prodottiView.aggiornaProdotti(stockDAO.getStockByInventario(idInventario));
        });

        view.show();
    }

    public void concludiOrdine() {
        Carrello carrello = Carrello.getInstance();
        
        String idSpedizione = generaIdSpedizione();
        // 1. Somma tutte le quantità totali per ogni prodotto nel carrello
        Map<Integer, Integer> quantitaTotalePerProdotto = new HashMap<>();
        Map<Integer, BigDecimal> prezzoTotalePerProdotto = new HashMap<>();

        
        for (ItemCarrello item : carrello.getItems()) {
            int idProdotto = item.getFornitura().getProdotto().getIdProdotto();
            int quantita = item.getQuantita();
            BigDecimal prezzo = item.getPrezzoTotale();
            
            quantitaTotalePerProdotto.put(idProdotto, quantitaTotalePerProdotto.getOrDefault(idProdotto, 0) + quantita);
            prezzoTotalePerProdotto.put(idProdotto, prezzoTotalePerProdotto.getOrDefault(idProdotto, BigDecimal.ZERO).add(prezzo));
        }

        // 2. Per ogni prodotto nel carrello, distribuisci le quantità tra gli inventari
        for (Map.Entry<Integer, Integer> entry : quantitaTotalePerProdotto.entrySet()) {
            int idProdotto = entry.getKey();
            int quantitaTotale = entry.getValue();

            // 3. Ottieni gli stock (inventari) che contengono il prodotto
            ArrayList<Stock> stocks = stockDAO.getStockByProdotto(idProdotto);
            // 4. Distribuisci le quantità tra gli inventari disponibili
            for (Stock stock : stocks) {
                // Calcoliamo la quantità che possiamo aggiungere all'inventario
                int quantitaDisponibile = stock.getQMaxInseribile() - stock.getQuantitaDisp();
                int quantitaDaDistribuire = Math.min(quantitaTotale, quantitaDisponibile);

                // Aggiorna la quantità nello stock
                stock.setQuantitaDisp(stock.getQuantitaDisp() + quantitaDaDistribuire);
                stockDAO.updateStock(stock);

                // Riduci la quantità totale da distribuire
                quantitaTotale -= quantitaDaDistribuire;

                // Se non ci sono più unità da distribuire, esci dal ciclo
                if (quantitaTotale <= 0) {
                    break;
                }
            }
        }
        
        salvaSpedizione(idSpedizione, quantitaTotalePerProdotto, prezzoTotalePerProdotto);
        carrello.svuota();
        caricaProdotti();
        aggiornaVistaCarrello();
       
        System.out.println("Ordine concluso con successo!");
    }

    public void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/StileModificaPrezzo.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    // Metodo per aggiornare la vista del carrello
    public void aggiornaVistaCarrello() {
        Carrello carrello = Carrello.getInstance();

        // Se la vista del carrello non è stata ancora creata o è stata svuotata, creala
        if (carrelloView == null) {
            carrelloView = new CarrelloView(carrello, this);
            carrelloView.mostra();  // Mostra la vista
        } else {
            // Se esiste già, aggiornala
            carrelloView.aggiornaVistaCarrello();  // Aggiorna la vista
        }
    }
    
    private void salvaSpedizione(String idSpedizione,  Map<Integer, Integer> quantitaTotalePerProdotto, Map<Integer, BigDecimal> prezzoTotalePerProdotto) {
    	for (Integer idProdotto : quantitaTotalePerProdotto.keySet()) {
            spedizioneDAO.salvaSpedizione(idSpedizione, idProdotto, quantitaTotalePerProdotto.get(idProdotto), prezzoTotalePerProdotto.get(idProdotto));
        }
    }
    
    private String generaIdSpedizione() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    
}