package it.unipv.ingsfw.bitebyte.controller;


import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.view.ProdottiView;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import it.unipv.ingsfw.bitebyte.view.ModificaPrezzoView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProdController {

    private StockDAO stockDAO;
    private ProdottiView prodottiView;
    private int idInventario;

    public ProdController() {
        this.stockDAO = new StockDAO();
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

    // Metodi chiamati dai bottoni
    public void handleRestock(Stock stock) {
        System.out.println("🔄 Rifornimento per: " + stock.getProdotto().getNome());
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
            boolean aggiornato = stockDAO.aggiornaPrezzo(stock.getProdotto().getIdProdotto(), idInventario, nuovoPrezzo);
            if (!aggiornato) {
                mostraErrore("❌ Errore durante l'aggiornamento del prezzo!");
                return;
            }
            // Se il prezzo è stato aggiornato, aggiorna il modello e la UI
    //      stock.getProdotto().setPrezzo(nuovoPrezzo);
            prodottiView.aggiornaProdotti(stockDAO.getStockByInventario(idInventario)); // Ricarica i dati dalla DB

        });

        view.show();
    }

    public void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        // Associa il CSS al dialog pane dell'alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/StileModificaPrezzo.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert"); 
        alert.showAndWait();
    }
}
