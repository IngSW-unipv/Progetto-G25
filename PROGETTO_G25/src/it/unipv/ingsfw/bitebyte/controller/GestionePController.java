package it.unipv.ingsfw.bitebyte.controller;
import it.unipv.ingsfw.bitebyte.service.GestioneInventarioService;
import it.unipv.ingsfw.bitebyte.service.DistributoreService;
import it.unipv.ingsfw.bitebyte.service.StockService;
import it.unipv.ingsfw.bitebyte.service.FornituraService;
import it.unipv.ingsfw.bitebyte.service.ProdottoService;
import it.unipv.ingsfw.bitebyte.service.SpedizioneService;
import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.ItemCarrello;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.view.CarrelloView;
import it.unipv.ingsfw.bitebyte.view.ModificaPrezzoView;
import it.unipv.ingsfw.bitebyte.view.RifornimentoView;
import it.unipv.ingsfw.bitebyte.view.SostituzioneView;
import it.unipv.ingsfw.bitebyte.view.StoricoSpedizioniView;
import it.unipv.ingsfw.bitebyte.view.ProdottiView;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionePController {
   
    private int idInventario;
    private GestioneInventarioService gestioneInventarioService;
    
    private ProdottiView prodottiView;
    private CarrelloView carrelloView;

    public GestionePController() {
        this.gestioneInventarioService = new GestioneInventarioService(
            new StockService(), new FornituraService(), new ProdottoService(), new DistributoreService(), new SpedizioneService()
        );
        this.prodottiView = new ProdottiView(this); 
        caricaDistributori();  
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        caricaProdotti();
    }
    
    public void caricaDistributori() {
        List<Distributore> distributori = gestioneInventarioService.getDistributori(); 
        prodottiView.setDistributori(distributori); 
    }
    
    public void caricaProdotti() {
        ArrayList<Stock> stocks = gestioneInventarioService.getProdottiByInventario(idInventario);
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
       // Ottieni la lista di forniture per il prodotto
        ArrayList<Fornitura> forniture = gestioneInventarioService.getFornitoriByStock(stock);
        RifornimentoView rifornimentoView = new RifornimentoView(forniture, stock, new RifornimentoView.RifornimentoListener() {
            @Override
            public void onFornitoreSelezionato(Fornitura fornitura, int quantita) {
                try {
                    // Chiamata al servizio per gestire il rifornimento
                    gestioneInventarioService.handleRestock(stock, fornitura, quantita);
                    aggiornaVistaCarrello();  
                    apriCarrello();  
                } catch (IllegalArgumentException e) {
                    mostraErrore(e.getMessage());
                } catch (RuntimeException e) {
                    mostraErrore("Errore nel caricamento della strategia di sconto.");
                }
            }
        });
        rifornimentoView.mostra();
    }
    
    public void handleSostituzione(Stock stock) {
        // Ottieni i prodotti sostitutivi per il prodotto attuale
        ArrayList<Prodotto> prodottiSostitutivi = gestioneInventarioService.getProdottiByCategoria(stock, stock.getProdotto().getCategoria());
        // Mostra la vista per la sostituzione
        new SostituzioneView(prodottiSostitutivi, prodottoSostituito -> {
            // Invoca il servizio per gestire la sostituzione
            gestioneInventarioService.handleSostituzione(stock, prodottoSostituito);
            // Ricarica i prodotti e aggiorna la vista
            prodottiView.aggiornaProdotti(gestioneInventarioService.getStockByInventario(stock.getIdInventario()));
        });
    }
    
    public void handleCambioPrezzo(Stock stock) {
        // Mostra la vista per modificare il prezzo
        ModificaPrezzoView view = new ModificaPrezzoView(stock, (prodotto, nuovoPrezzo) -> {
            try {
                // Chiamata al servizio per gestire il cambio del prezzo
                boolean aggiornato = gestioneInventarioService.handleCambioPrezzo(stock, nuovoPrezzo);
                if (!aggiornato) {
                    mostraErrore("Errore durante l'aggiornamento del prezzo!");
                    return;
                }
                // Aggiorna i prodotti dopo aver cambiato il prezzo
                prodottiView.aggiornaProdotti(gestioneInventarioService.getStockByInventario(stock.getIdInventario()));
            } catch (IllegalArgumentException e) {
                mostraErrore(e.getMessage());
            }
        });
        view.show();
    }

    public void apriCarrello() {
        Carrello carrello = Carrello.getInstance();
        carrelloView = new CarrelloView(carrello, this);
        carrelloView.aggiornaVistaCarrello();
        carrelloView.mostra();
    }
    
    public void apriStoricoSpedizioni() {
        ArrayList<Spedizione> spedizioni = gestioneInventarioService.getAllSpedizioni();
        StoricoSpedizioniView storicoView = new StoricoSpedizioniView();
        storicoView.mostra(spedizioni);
    }
    
    public void concludiOrdine() {
        Carrello carrello = Carrello.getInstance();
        String idSpedizione = gestioneInventarioService.generaIdSpedizione();
        
        Map<Integer, Integer> quantitaTotalePerProdotto = new HashMap<>();
        Map<Integer, BigDecimal> prezzoTotalePerProdotto = new HashMap<>();
        for (ItemCarrello item : carrello.getItems()) {
            int idProdotto = item.getFornitura().getProdotto().getIdProdotto();
            int quantita = item.getQuantita();
            BigDecimal prezzo = item.getPrezzoTotale();
            
            quantitaTotalePerProdotto.put(idProdotto, quantitaTotalePerProdotto.getOrDefault(idProdotto, 0) + quantita);
            prezzoTotalePerProdotto.put(idProdotto, prezzoTotalePerProdotto.getOrDefault(idProdotto, BigDecimal.ZERO).add(prezzo));
        }
        // Delegato al servizio per la gestione dell'ordine
        gestioneInventarioService.concludiOrdine(carrello, idSpedizione, quantitaTotalePerProdotto, prezzoTotalePerProdotto);
        carrello.svuota();
        caricaProdotti();
        aggiornaVistaCarrello();
        System.out.println("Ordine concluso con successo!");
    }

    public void aggiornaVistaCarrello() {
        Carrello carrello = Carrello.getInstance();
        if (carrelloView == null) {
            carrelloView = new CarrelloView(carrello, this);
            carrelloView.mostra();  
        } else {
            carrelloView.aggiornaVistaCarrello();  
        }
    }
    
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/StileModificaPrezzo.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
        alert.showAndWait();
    }
}
