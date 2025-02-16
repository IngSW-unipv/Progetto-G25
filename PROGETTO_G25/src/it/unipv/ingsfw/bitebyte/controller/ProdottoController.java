package it.unipv.ingsfw.bitebyte.controller;

import java.util.ArrayList;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProdottoController {

    private StockDAO stockDAO;  // Riferimento al DAO

    // Costruttore per inizializzare il DAO
    public ProdottoController(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public void verificaDisponibilita(String idProdotto) {
        // Recupera la lista di Stock per tutti gli inventari
        ArrayList<Stock> stocks = stockDAO.getStockByInventario(1); // Supponiamo che l'inventario sia identificato da 1. Puoi personalizzare questa parte

        Stock stockTrovato = null;

        // Cerca l'oggetto Stock per il prodotto selezionato
        for (Stock stock : stocks) {
            if (stock.getProdotto().getIdProdotto().equals(idProdotto)) {
                stockTrovato = stock;
                break;
            }
        }

        // Se il prodotto è trovato, verifica la disponibilità
        if (stockTrovato != null) {
            if (stockTrovato.getQuantitaDisp() <= 0) {
                mostraMessaggioErrore("Prodotto non disponibile", "Il prodotto selezionato non è disponibile nel distributore.");
            } else {
                System.out.println("Il prodotto è disponibile.");
            }
        } else {
            mostraMessaggioErrore("Prodotto non trovato", "Il prodotto selezionato non esiste nel distributore.");
        }
    }

    private void mostraMessaggioErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}


