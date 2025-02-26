package it.unipv.ingsfw.bitebyte.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.ClientService;
import it.unipv.ingsfw.bitebyte.services.OrdineService;
import it.unipv.ingsfw.bitebyte.services.PortafoglioService;
import it.unipv.ingsfw.bitebyte.services.StockService;
import it.unipv.ingsfw.bitebyte.services.TransazioneService;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.GenerazioneId;
import it.unipv.ingsfw.bitebyte.utils.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AcquistoController {

    private ViewPrSelected view;
    private Stock stockSelezionato;
    private Stage previousStage;
    private ClientService clienteService;
    private PortafoglioService portafoglioService;
    private StockService stockService;
    private OrdineService ordineService; // Aggiunto per gestire gli ordini
    private Stage newStage;

    public AcquistoController(Stage previousStage,Stage newStage) {
    	
        this.view = new ViewPrSelected();
        this.previousStage = previousStage;
        this.newStage = newStage;
        this.clienteService = new ClientService();
        this.portafoglioService = new PortafoglioService();
        this.stockService = new StockService();
        this.ordineService = new OrdineService();
    }

    public void setStockSelezionato(Stock stock) {
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato != null) {
            System.out.println("Utente connesso: " + stock.getProdotto().getNome());
            this.stockSelezionato = stock;
        } else {
            System.out.println("Nessun cliente connesso. Impossibile completare l'acquisto.");
        }
    }

    public void mostraInterfaccia(Stock stock, Stage newStage) {
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }

        double saldo = ottieniSaldoCliente(clienteLoggato);
        VBox vbox = creaVista(stock, newStage, clienteLoggato, saldo);

        configuraEVisualizzaFinestra(newStage, vbox);
    }

    private Cliente ottieniClienteLoggato() {
        return Sessione.getInstance().getClienteConnesso();
    }

    private double ottieniSaldoCliente(Cliente cliente) {
        return clienteService.getSaldo(cliente);
    }

    private VBox creaVista(Stock stock, Stage newStage, Cliente cliente, double saldo) {
        return view.creaInterfaccia(stock, this, newStage, previousStage, cliente, saldo);
    }

    private void configuraEVisualizzaFinestra(Stage newStage, VBox vbox) {
        Scene scene = new Scene(vbox, 650, 500);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());

        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();

        view.aggiornaImmagine(stockSelezionato);
    }
    
    public void tornaIndietro(Stage newStage, Stage previousStage) {
        newStage.close();  // Chiudi la finestra corrente
        previousStage.show();  // Mostra la finestra precedente
    }

    public void acquistaProdotto(Stock stock) {	//Gestisce l'acquisto di un prodotto, aggiornando il saldo, lo stock e registrando l'ordine.
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }
        BigDecimal prezzoProdotto = stock.getProdotto().getPrezzo();

        if (!clienteService.saldoSufficiente(clienteLoggato, prezzoProdotto)) {
            AlertUtils.mostraAlertConferma("Saldo insufficiente!", "Il saldo a disposione non è sufficiente","Saldo insufficiente per acquistare: " + stock.getProdotto().getNome() + " " + "Ricaricare il portafoglio virtuale");
            tornaAllaPaginaProfiloCliente();
            return;
        }

        int quantitaDisponibile = stock.getQuantitaDisp();
        if (quantitaDisponibile <= 0) {
            System.out.println("Prodotto esaurito: " + stock.getProdotto().getNome());
            return;
        }
        
        if (!stockService.erroreStato(stock)) {
        	AlertUtils.mostraAlertConferma("Problema!!","Distributore malfunzionante","Prodotto non più disponibile, contattare Amministratore");
        	tornaAllaPaginaProfiloCliente();
        	return;
        } // metodo implementato per la simulazione di un errore da parte del distributore 1 possibilità su 10
        
        Ordine nuovoOrdine = new Ordine(GenerazioneId.generaIdCasuale(), LocalDateTime.now(),StatoOrd.CONFERMATO,prezzoProdotto,clienteLoggato,stock.getProdotto());     
        boolean ordineCreato = ordineService.creaOrdine(nuovoOrdine);
        if (ordineCreato) {
            System.out.println("Ordine registrato con successo! ID: " + nuovoOrdine.getIdOrdine());
            int ID_portafoglio = portafoglioService.getIdPort(clienteLoggato.getCf()); //mi recupero l'id del portafoglio per creare la transazione
            TransazioneService transazioneservice = new TransazioneService();
            boolean controlloTransazione = transazioneservice.creaTransazione(nuovoOrdine, ID_portafoglio);
	        if (controlloTransazione) {
	            // Aggiornamento saldo
	            double saldoAttuale = clienteService.getSaldo(clienteLoggato);
	            double nuovoSaldo = saldoAttuale - prezzoProdotto.doubleValue();
	            portafoglioService.aggiornaSaldo(clienteLoggato.getCf(), nuovoSaldo);        
	            // Riduzione della quantità in stock
	            stock.setQuantitaDisp(quantitaDisponibile - 1);
	            stockService.aggiornaQuantita(stock);
	        	AlertUtils.mostraAlertConferma("Successo","Transazione eseguita!","Transazione eseguita correttamente!!");
	        } else {
	        	AlertUtils.mostraAlertConferma("Insuccesso","Transazione non eseguita!","Transazione non eseguita. Riprovare!!");
	        	tornaAllaPaginaProfiloCliente();
	        	return;
	        }
            AlertUtils.mostraAlertConferma("Successo","Acquisto confermato","Acquisto confermato e prodotto erogato!!");
            tornaAllaPaginaProfiloCliente();
            
        } else {
            System.out.println("Errore nella creazione dell'ordine.");
        }
    } 
    private void tornaAllaPaginaProfiloCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/ProfiloCliente.fxml"));
            Parent root = loader.load();
            // Imposta la scena sulla stessa finestra già esistente
            Scene scene = new Scene(root, 600, 400);
            newStage.setScene(scene); // Usa newStage già creata
            newStage.setTitle("Profilo Cliente");
            newStage.show();
            // Chiudi la finestra precedente solo dopo averla mostrata
            newStage.setOnShown(event -> {
                if (previousStage != null) {
                    previousStage.close();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}