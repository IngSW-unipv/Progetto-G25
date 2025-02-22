package it.unipv.ingsfw.bitebyte.controller;

import javafx.scene.control.Label;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import javafx.fxml.FXML;

public class AcquistoController {
    
	
	
    private Stock stockSelezionato;

    // Metodo per settare lo stock selezionato
    public void setStockSelezionato(Stock stock) {
        this.stockSelezionato = stock;
    }

    // Metodo che delega alla vista la creazione dell'interfaccia
    public void passaAllaVista() {
        if (stockSelezionato != null) {
            ViewPrSelected view = new ViewPrSelected();
            view.creaInterfaccia(stockSelezionato); // Passa lo stock alla vista
            
        }
    }


}