package it.unipv.ingsfw.bitebyte.controller;

import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.service.ClienteService;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AcquistoController {

    private ViewPrSelected view;
    private Stock stockSelezionato;
    private Stage previousStage;
    private ClienteService clienteService; // Imposto il servizio per evitare di crearlo ogni volta

    public AcquistoController(Stage previousStage) {
        this.view = new ViewPrSelected();
        this.previousStage = previousStage;
        this.clienteService = new ClienteService(); // Creiamo una sola istanza per tutto il controller
    }

    public void setStockSelezionato(Stock stock) {
        Cliente clienteLoggato = Sessione.getInstance().getClienteConnesso();
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
        Scene scene = new Scene(vbox, 600, 400);
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

    public void acquistaProdotto(Stock stock) {
        Cliente clienteLoggato = ottieniClienteLoggato();
        if (clienteLoggato == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }
        BigDecimal prezzoProdotto = stock.getProdotto().getPrezzo(); // Ottieni il prezzo del prodotto (BigDecimal)
        if (clienteService.saldoSufficiente(clienteLoggato, prezzoProdotto)) {
            System.out.println("Acquisto effettuato per: " + stock.getProdotto().getNome());
            // Logica per completare l'acquisto
        } else {
            System.out.println("Saldo insufficiente per acquistare: " + stock.getProdotto().getNome());
        }
    }
}