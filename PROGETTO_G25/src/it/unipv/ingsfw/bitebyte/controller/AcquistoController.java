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
import it.unipv.ingsfw.bitebyte.service.ClienteService;
import it.unipv.ingsfw.bitebyte.service.OrdineService;
import it.unipv.ingsfw.bitebyte.service.PortafoglioService;
import it.unipv.ingsfw.bitebyte.service.StockService;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.GenerazioneId;
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
    private ClienteService clienteService;
    private PortafoglioService portafoglioService;
    private StockService stockService;
    private OrdineService ordineService; // Aggiunto per gestire gli ordini

    public AcquistoController(Stage previousStage) {
        this.view = new ViewPrSelected();
        this.previousStage = previousStage;
        this.clienteService = new ClienteService();
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

    public void tornaIndietro(Stage currentStage) {
        currentStage.close();
        previousStage.show();
    }

    public void acquistaProdotto(Stock stock) {	//Gestisce l'acquisto di un prodotto, aggiornando il saldo, lo stock e registrando l'ordine.
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }

        BigDecimal prezzoProdotto = stock.getProdotto().getPrezzo();

        if (!clienteService.saldoSufficiente(clienteLoggato, prezzoProdotto)) {
            System.out.println("Saldo insufficiente per acquistare: " + stock.getProdotto().getNome());
            return;
        }

        int quantitaDisponibile = stock.getQuantitaDisp();
        if (quantitaDisponibile <= 0) {
            System.out.println("Prodotto esaurito: " + stock.getProdotto().getNome());
            return;
        }

        // Aggiornamento saldo
        double saldoAttuale = clienteService.getSaldo(clienteLoggato);
        double nuovoSaldo = saldoAttuale - prezzoProdotto.doubleValue();
        portafoglioService.aggiornaSaldo(clienteLoggato.getCf(), nuovoSaldo);

        // Riduzione della quantità in stock
        stock.setQuantitaDisp(quantitaDisponibile - 1);
        stockService.aggiornaQuantita(stock);

        System.out.println("Acquisto effettuato per: " + stock.getProdotto().getNome());
        System.out.println("Il nuovo saldo è: " + nuovoSaldo);
        System.out.println("Nuova quantità disponibile: " + stock.getQuantitaDisp());

        // Creazione dell'ordine con il prodotto associato
        Ordine nuovoOrdine = new Ordine(GenerazioneId.generaIdCasuale(), LocalDateTime.now(),StatoOrd.CONFERMATO,prezzoProdotto,clienteLoggato,stock.getProdotto()
        );

        boolean ordineCreato = ordineService.creaOrdine(nuovoOrdine);
        if (ordineCreato) {
            System.out.println("Ordine registrato con successo! ID: " + nuovoOrdine.getIdOrdine());
            AlertUtils.mostraAlertConferma("Successo","Acquisto confermato","Acquisto confermato e prodotto erogato!!");
            tornaAllaPaginaProfiloCliente();
            
        } else {
            System.out.println("Errore nella creazione dell'ordine.");
        }
    }
    
    @FXML
    private void tornaAllaPaginaProfiloCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfiloCliente.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 400);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Profilo Cliente");
            newStage.show();

            if (previousStage != null) {
                previousStage.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
