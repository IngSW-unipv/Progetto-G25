package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.FornituraDAO;
import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.view.ModificaPrezzoView;
import it.unipv.ingsfw.bitebyte.view.ProdottiView;
import it.unipv.ingsfw.bitebyte.view.RifornimentoView;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.math.BigDecimal;
import java.util.ArrayList;

public class GestionePController {

    private StockDAO stockDAO;
    private FornituraDAO fornituraDAO;
    private ProdottiView prodottiView;
    private int idInventario;

    public GestionePController() {
        this.stockDAO = new StockDAO();
        this.fornituraDAO = new FornituraDAO();
        this.prodottiView = new ProdottiView(this); // Passiamo il controller alla view
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        caricaProdotti();
    }

    public void caricaProdotti() {
        ArrayList<Stock> stocks = stockDAO.getStockByInventario(idInventario);
        prodottiView.aggiornaProdotti(stocks);
    }

    public ProdottiView getView() {
        return prodottiView;
    }

    public void handleRestock(Stock stock) {
        System.out.println("🔄 Rifornimento per: " + stock.getProdotto().getNome());

        ArrayList<Fornitura> forniture = fornituraDAO.getFornitoriInfo(stock);

        RifornimentoView rifornimentoView = new RifornimentoView(forniture, stock, new RifornimentoView.RifornimentoListener() {
            @Override
            public void onFornitoreSelezionato(Fornitura fornitura, int quantita) {
                System.out.println("✅ Fornitore selezionato: " + fornitura.getFornitore().getNomeF());
                System.out.println("📦 Quantità richiesta: " + quantita);
                // Implementazione della logica di aggiornamento DB
            }

            @Override
            public void onConfermaOrdine() {
                System.out.println("🛒 Ordine confermato!");
            }
        });

        rifornimentoView.mostra();
    }

    public void handleSostituzione(Stock stock) {
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
    
    public void handleApriCarrello() {
    	System.out.println("Carrello");
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
}
