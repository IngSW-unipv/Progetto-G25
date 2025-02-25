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
import javafx.stage.Stage;

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
        this.prodottiView = new ProdottiView(); 
        initialize();
    }
    
    private void initialize() {
        this.prodottiView.setOnSelezionaDistributore(this::setIdInventario); // Imposta listener per il distributore
        this.prodottiView.setOnRestock(this::handleRestock); // Imposta listener per il rifornimento
        this.prodottiView.setOnSostituzione(this::handleSostituzione); // Imposta listener per la sostituzione
        this.prodottiView.setOnCambioPrezzo(this::handleCambioPrezzo); // Imposta listener per il cambio prezzo
        this.prodottiView.setOnApriCarrello(this::apriCarrello); // Imposta listener per aprire il carrello
        this.prodottiView.setOnApriStoricoSpedizioni(this::apriStoricoSpedizioni); // Imposta listener per aprire storico spedizioni
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
        int quantitaOriginale = stock.getQuantitaDisp();
        ArrayList<Fornitura> forniture = gestioneInventarioService.getFornitoriByStock(stock);
        RifornimentoView rifornimentoView = new RifornimentoView(forniture, stock, new RifornimentoView.RifornimentoListener() {
            @Override
            public void onFornitoreSelezionato(Fornitura fornitura, int quantita) {
                try {
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
        ArrayList<Prodotto> prodottiSostitutivi = gestioneInventarioService.getProdottiByCategoria(stock, stock.getProdotto().getCategoria());
        new SostituzioneView(prodottiSostitutivi, prodottoSostituito -> {
            gestioneInventarioService.handleSostituzione(stock, prodottoSostituito);
            prodottiView.aggiornaProdotti(gestioneInventarioService.getStockByInventario(stock.getIdInventario()));
        });
    }

    public void handleCambioPrezzo(Stock stock) {
        ModificaPrezzoView view = new ModificaPrezzoView(stock, (prodotto, nuovoPrezzo) -> {
            try {
                boolean aggiornato = gestioneInventarioService.handleCambioPrezzo(stock, nuovoPrezzo);
                if (!aggiornato) {
                    mostraErrore("Errore durante l'aggiornamento del prezzo!");
                    return;
                }
                prodottiView.aggiornaProdotti(gestioneInventarioService.getStockByInventario(stock.getIdInventario()));
            } catch (IllegalArgumentException e) {
                mostraErrore(e.getMessage());
            }
        });
        view.show();
    }

    public void concludiOrdine() {
        Carrello carrello = Carrello.getInstance();
        gestioneInventarioService.concludiOrdine(carrello);
        carrello.svuota();
        caricaProdotti();
        aggiornaVistaCarrello();
        System.out.println("Ordine concluso con successo!");
    }

    public void apriCarrello() {
        if (carrelloView != null) {
            Stage stage = (Stage) carrelloView.getRootLayout().getScene().getWindow();
            if (stage != null) {
                stage.close();  
            }
        }
        Carrello carrello = Carrello.getInstance();
        carrelloView = new CarrelloView(carrello);
        // Aggiungi il listener per la rimozione del prodotto
        carrelloView.setOnRimuoviProdotto(item -> {
            carrello.rimuoviItem(item);
            aggiornaVistaCarrello();
        });
        // Aggiungi il listener per tornare ai prodotti (chiudendo il carrello)
        carrelloView.setOnTornaAiProdotti(() -> {
            Stage stage = (Stage) carrelloView.getRootLayout().getScene().getWindow();
            if (stage != null) {
                stage.close();
            }
        });
        // Aggiungi il listener per concludere l'ordine
        carrelloView.setOnConcludiOrdine(() -> {
            concludiOrdine();
        });
        carrelloView.mostra();
    }

    public void apriStoricoSpedizioni() {
        ArrayList<Spedizione> spedizioni = gestioneInventarioService.getAllSpedizioni();
        StoricoSpedizioniView storicoView = new StoricoSpedizioniView();
        storicoView.mostra(spedizioni);
    }

    public void aggiornaVistaCarrello() {
        Carrello carrello = Carrello.getInstance();
        if (carrelloView == null) {
            carrelloView = new CarrelloView(carrello);
            carrelloView.mostra();  
        } else {
            carrelloView.aggiornaVistaCarrello();  
        }
    }

    private void mostraErrore(String messaggio) {
        prodottiView.mostraErrore(messaggio);
    }
}
