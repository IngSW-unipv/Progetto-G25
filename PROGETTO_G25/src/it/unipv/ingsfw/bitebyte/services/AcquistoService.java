package it.unipv.ingsfw.bitebyte.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;
import it.unipv.ingsfw.bitebyte.utils.GenerazioneId;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.unipv.ingsfw.bitebyte.controller.AcquistoController;
import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;

public class AcquistoService {

    private ClientService clientService;
    private StockService stockService;
    private OrdineService ordineService;
    private TransazioneService transazioneService;
    private PortafoglioService portafoglioService;

    public AcquistoService(ClientService clientService, StockService stockService,
                           OrdineService ordineService, TransazioneService transazioneService,
                           PortafoglioService portafoglioService) {
        this.clientService = clientService;
        this.stockService = stockService;
        this.ordineService = ordineService;
        this.transazioneService = transazioneService;
        this.portafoglioService = portafoglioService;
    }

    public Cliente getClienteLoggato() {
        return Sessione.getInstance().getClienteConnesso();
    }

    public boolean validaSaldo(Cliente cliente, Stock stock) {
        BigDecimal prezzoProdotto = stock.getProdotto().getPrezzo();
        return clientService.saldoSufficiente(cliente, prezzoProdotto);
    }

    public boolean procediAcquisto(Stock stock, Cliente cliente) {
        int quantitaDisponibile = stock.getQuantitaDisp();
        if (quantitaDisponibile <= 0) {
            System.out.println("Prodotto esaurito: " + stock.getProdotto().getNome());
            return false;
        }

        Ordine nuovoOrdine = new Ordine(GenerazioneId.generaIdCasuale(), LocalDateTime.now(), StatoOrd.CONFERMATO,
                                        stock.getProdotto().getPrezzo(), cliente, stock.getProdotto());
        boolean ordineCreato = ordineService.creaOrdine(nuovoOrdine);
        if (ordineCreato) {
            int ID_portafoglio = portafoglioService.getIdPort(cliente.getCf());
            boolean controlloTransazione = transazioneService.creaTransazione(nuovoOrdine, ID_portafoglio);
            if (controlloTransazione) {
                // Aggiorna saldo utilizzando PortafoglioService
                double prezzoProdotto = stock.getProdotto().getPrezzo().doubleValue();  // Converte il BigDecimal in double
                portafoglioService.aggiornaSaldo(cliente.getCf(), prezzoProdotto);  // Usa il codice fiscale e il saldo in formato double
                stockService.aggiornaQuantita(stock);
                return true;
            }
        }
        return false;
    }

    public VBox creaVista(Stock stock, AcquistoController controller, Stage newStage, Stage previousStage,
                          Cliente clienteLoggato, double saldo) {
        // Crea la vista dell'interfaccia
        return new ViewPrSelected().creaInterfaccia(stock, controller, newStage, previousStage, clienteLoggato, saldo);
    }
}
