package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.ItemCarrello;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Spedizione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.DistributoreService;
import it.unipv.ingsfw.bitebyte.services.FornituraService;
import it.unipv.ingsfw.bitebyte.services.GestioneInventarioService;
import it.unipv.ingsfw.bitebyte.services.ProdottoService;
import it.unipv.ingsfw.bitebyte.services.SpedizioneService;
import it.unipv.ingsfw.bitebyte.services.StockService;
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

/**
 * Controller per la gestione delle operazioni sull'inventario che un amministratore può realizzare.
 * Gestisce l'interazione con l'inventario, il rifornimento dei prodotti, la modifica dei prezzi,
 * la sostituzione dei prodotti, la conclusione degli ordini e la gestione del carrello.
 */
public class GestionePController {

    private int idInventario;
    private GestioneInventarioService gestioneInventarioService;

    private ProdottiView prodottiView;
    private CarrelloView carrelloView;

    /**
     * Costruisce una nuova istanza del controller GestionePController, inizializzando i servizi e le viste.
     */
    public GestionePController() {
        this.gestioneInventarioService = new GestioneInventarioService(
            new StockService(), new FornituraService(), new ProdottoService(), new DistributoreService(), new SpedizioneService()
        );
        this.prodottiView = new ProdottiView(); 
        initialize();
    }

    /**
     * Inizializza il controller impostando i listener per le varie azioni.
     */
    private void initialize() {
        this.prodottiView.setOnSelezionaDistributore(this::setIdInventario); // Imposta listener per la selezione del distributore
        this.prodottiView.setOnRestock(this::handleRestock); // Imposta listener per il rifornimento
        this.prodottiView.setOnSostituzione(this::handleSostituzione); // Imposta listener per la sostituzione del prodotto
        this.prodottiView.setOnCambioPrezzo(this::handleCambioPrezzo); // Imposta listener per il cambio del prezzo
        this.prodottiView.setOnApriCarrello(this::apriCarrello); // Imposta listener per l'apertura del carrello
        this.prodottiView.setOnApriStoricoSpedizioni(this::apriStoricoSpedizioni); // Imposta listener per l'apertura dello storico spedizioni
        caricaDistributori();  
    }

    /**
     * Imposta l'ID dell'inventario e carica i prodotti corrispondenti.
     * 
     * @param idInventario L'ID dell'inventario.
     */
    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        caricaProdotti();
    }

    /**
     * Carica la lista dei distributori e aggiorna la vista.
     */
    public void caricaDistributori() {
        List<Distributore> distributori = gestioneInventarioService.getDistributori(); 
        prodottiView.setDistributori(distributori); 
    }

    /**
     * Carica i prodotti associati all'inventario corrente e aggiorna la vista.
     */
    public void caricaProdotti() {
        ArrayList<Stock> stocks = gestioneInventarioService.getProdottiByInventario(idInventario);
        prodottiView.aggiornaProdotti(stocks);
    }

    /**
     * Restituisce la vista associata ai prodotti.
     * 
     * @return L'oggetto ProdottiView.
     */
    public ProdottiView getView() {
        return prodottiView;
    }

    /**
     * Gestisce il rifornimento di un prodotto nell'inventario
     * mediante chiamata alla classe service.
     * 
     * @param stock L'oggetto stock per il quale viene effettuato il rifornimento.
     */
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

    /**
     * Gestisce la sostituzione di un prodotto nell'inventario
     * mediante chiamata alla classe service.
     * 
     * @param stock L'oggetto stock che rappresenta il prodotto da sostituire.
     */
    public void handleSostituzione(Stock stock) {
        ArrayList<Prodotto> prodottiSostitutivi = gestioneInventarioService.getProdottiByCategoria(stock, stock.getProdotto().getCategoria());
        new SostituzioneView(prodottiSostitutivi, prodottoSostituito -> {
            gestioneInventarioService.handleSostituzione(stock, prodottoSostituito);
            prodottiView.aggiornaProdotti(gestioneInventarioService.getStockByInventario(stock.getIdInventario()));
        });
    }

    /**
     * Gestisce la modifica del prezzo di un prodotto nell'inventario
     * mediante chiamata alla classe service.
     * @param stock L'oggetto stock per il quale viene applicato il cambio prezzo.
     */
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

    /**
     * Conclude l'ordine corrente, finalizzando la spedizione e svuotando il carrello
     * servendosi sempre della classe service.
     */
    public void concludiOrdine() {
        Carrello carrello = Carrello.getInstance();
        gestioneInventarioService.concludiOrdine(carrello);
        carrello.svuota();
        caricaProdotti();
        aggiornaVistaCarrello();
        System.out.println("Ordine concluso con successo!");
    }

    /**
     * Apre la vista del carrello, permettendo all'utente di gestire gli articoli nel carrello.
     */
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

    /**
     * Apre la vista dello storico delle spedizioni per visualizzare le spedizioni passate.
     */
    public void apriStoricoSpedizioni() {
        ArrayList<Spedizione> spedizioni = gestioneInventarioService.getAllSpedizioni();
        StoricoSpedizioniView storicoView = new StoricoSpedizioniView();
        storicoView.mostra(spedizioni);
    }

    /**
     * Aggiorna la vista del carrello con gli ultimi articoli nel carrello.
     */
    public void aggiornaVistaCarrello() {
        Carrello carrello = Carrello.getInstance();
        if (carrelloView == null) {
            carrelloView = new CarrelloView(carrello);
            carrelloView.mostra();  
        } else {
            carrelloView.aggiornaVistaCarrello();  
        }
    }

    /**
     * Mostra un messaggio di errore nella vista dei prodotti.
     * 
     * @param messaggio Il messaggio di errore da visualizzare.
     */
    private void mostraErrore(String messaggio) {
        prodottiView.mostraErrore(messaggio);
    }
}
