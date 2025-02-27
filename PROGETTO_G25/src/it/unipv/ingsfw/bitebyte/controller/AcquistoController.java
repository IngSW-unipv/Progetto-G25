package it.unipv.ingsfw.bitebyte.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
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
    private Stage newStage;
    
    private ClientService clienteService;
    private PortafoglioService portafoglioService;
    private StockService stockService;
    private OrdineService ordineService;

    public AcquistoController(Stage previousStage, Stage newStage) {
        this.view = new ViewPrSelected();
        this.previousStage = previousStage;
        this.newStage = newStage;
        this.clienteService = new ClientService();
        this.portafoglioService = new PortafoglioService();
        this.stockService = new StockService();
        this.ordineService = new OrdineService(new OrdineDAO());
    }

    public void setStockSelezionato(Stock stock) {
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato != null) {
            this.stockSelezionato = stock;
            System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
        } else {
            System.out.println("Nessun cliente loggato.");
        }
    }

    public void mostraInterfaccia(Stock stock) {
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }

        double saldo = ottieniSaldoCliente(clienteLoggato);
        VBox vbox = creaVista(stock, clienteLoggato, saldo);
        configuraEVisualizzaFinestra(vbox);
    }

    private Cliente ottieniClienteLoggato() {
        return Sessione.getInstance().getClienteConnesso();
    }

    private double ottieniSaldoCliente(Cliente cliente) {
        return clienteService.getSaldo(cliente);
    }

    private VBox creaVista(Stock stock, Cliente cliente, double saldo) {
        return view.creaInterfaccia(stock, this, newStage, previousStage, cliente, saldo);
    }

    private void configuraEVisualizzaFinestra(VBox vbox) {
        Scene scene = new Scene(vbox, 650, 500);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());

        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();
        view.aggiornaImmagine(stockSelezionato);
    }

    public void acquistaProdotto(Stock stock) {
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }

        BigDecimal prezzoProdotto = stock.getProdotto().getPrezzo();

        if (!clienteService.saldoSufficiente(clienteLoggato, prezzoProdotto)) {
            AlertUtils.mostraAlertConferma("Saldo insufficiente", "Saldo insufficiente per acquistare il prodotto.", "Ricarica il portafoglio.");
            tornaAllaPaginaProfiloCliente();
            return;
        }

        if (stock.getQuantitaDisp() <= 0) {
            System.out.println("Prodotto esaurito: " + stock.getProdotto().getNome());
            return;
        }

        if (!stockService.erroreStato(stock)) {
            AlertUtils.mostraAlertConferma("Problema", "Distributore malfunzionante", "Prodotto non disponibile.");
            tornaAllaPaginaProfiloCliente();
            return;
        }

        Ordine nuovoOrdine = new Ordine(GenerazioneId.generaIdCasuale(), LocalDateTime.now(), StatoOrd.CONFERMATO, prezzoProdotto, clienteLoggato, stock.getProdotto());
        boolean ordineCreato = ordineService.creaOrdine(nuovoOrdine);

        if (ordineCreato) {
            int ID_portafoglio = portafoglioService.getIdPort(clienteLoggato.getCf());
            TransazioneService transazioneservice = new TransazioneService();
            boolean controlloTransazione = transazioneservice.creaTransazione(nuovoOrdine, ID_portafoglio);
            if (controlloTransazione) {
                aggiornaSaldoCliente(clienteLoggato, prezzoProdotto);
                aggiornaQuantitaStock(stock);
                AlertUtils.mostraAlertConferma("Successo", "Acquisto confermato", "Prodotto erogato!");
            } else {
                AlertUtils.mostraAlertConferma("Insuccesso", "Transazione non eseguita", "Riprovare!");
                tornaAllaPaginaProfiloCliente();
            }
        } else {
            System.out.println("Errore nella creazione dell'ordine.");
        }
    }

    private void aggiornaSaldoCliente(Cliente cliente, BigDecimal prezzoProdotto) {
        double saldoAttuale = clienteService.getSaldo(cliente);
        double nuovoSaldo = saldoAttuale - prezzoProdotto.doubleValue();
        portafoglioService.aggiornaSaldo(cliente.getCf(), nuovoSaldo);
        PortafoglioVirtuale portafoglio = Sessione.getInstance().getPortafoglioCliente();
        if (portafoglio != null) {
            portafoglio.setSaldo(nuovoSaldo);
        }
    }

    private void aggiornaQuantitaStock(Stock stock) {
        stock.setQuantitaDisp(stock.getQuantitaDisp() - 1);
        stockService.aggiornaQuantita(stock);
    }

    private void tornaAllaPaginaProfiloCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/ProfiloCliente.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            newStage.setScene(scene);
            newStage.setTitle("Profilo Cliente");
            newStage.show();
            newStage.setOnShown(event -> {
                if (previousStage != null) {
                    previousStage.close();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void tornaIndietro(Stage newStage, Stage previousStage) {
        newStage.close();  // Chiudi la finestra corrente
        previousStage.show();  // Mostra la finestra precedente
    }
}
