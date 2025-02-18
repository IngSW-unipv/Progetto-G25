package it.unipv.ingsfw.bitebyte.controller;


import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.view.ProdottiView;

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
        System.out.println("💰 Cambio prezzo per: " + stock.getProdotto().getNome());
    }
}
