package it.unipv.ingsfw.bitebyte.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.GenerazioneId;

public class AcquistoService {
    
    private final ClientService clientService;
    private final PortafoglioService portafoglioService;
    private final StockService stockService;
    private final OrdineService ordineService;
    private final TransazioneService transazioneService;

    public AcquistoService(ClientService clientService, PortafoglioService portafoglioService, 
                           StockService stockService, OrdineService ordineService, 
                           TransazioneService transazioneService) {
        this.clientService = clientService;
        this.portafoglioService = portafoglioService;
        this.stockService = stockService;
        this.ordineService = ordineService;
        this.transazioneService = transazioneService;
    }

    public Cliente ottieniClienteLoggato() {
        return Sessione.getInstance().getClienteConnesso();
    }

    public double ottieniSaldoCliente(Cliente cliente) {
        return clientService.getSaldo(cliente);
    }

    public void acquistaProdotto(Stock stock) {
        Cliente cliente = ottieniClienteLoggato();
        if (cliente == null) {
            System.out.println("Errore: Nessun cliente loggato.");
            return;
        }

        BigDecimal prezzo = stock.getProdotto().getPrezzo();
        if (!clientService.saldoSufficiente(cliente, prezzo)) {
            AlertUtils.showAlert("Saldo insufficiente!", "Ricaricare il portafoglio.");
            return;
        }

        if (stock.getQuantitaDisp() <= 0) {
            AlertUtils.showAlert("Prodotto esaurito", "Il prodotto non è più disponibile.");
            return;
        }

        Ordine ordine = new Ordine(GenerazioneId.generaIdCasuale(), LocalDateTime.now(), StatoOrd.CONFERMATO, prezzo, cliente, stock.getProdotto());
        if (ordineService.creaOrdine(ordine)) {
            aggiornaSaldoEStock(cliente, stock, prezzo);
            AlertUtils.showAlert("Acquisto confermato", "Transazione completata con successo.");
        } else {
            AlertUtils.showAlert("Errore", "Ordine non creato.");
        }
    }

    private void aggiornaSaldoEStock(Cliente cliente, Stock stock, BigDecimal prezzo) {
        double nuovoSaldo = clientService.getSaldo(cliente) - prezzo.doubleValue();
        portafoglioService.aggiornaSaldo(cliente.getCf(), nuovoSaldo);
        stock.setQuantitaDisp(stock.getQuantitaDisp() - 1);
        stockService.aggiornaQuantita(stock);
    }
}