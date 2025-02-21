package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Stock;

public class AcquistoController {


	    private Stock selectedStock;

	    // Metodo per ricevere lo stock selezionato
	    public void setSelectedStock(Stock stock) {
	        this.selectedStock = stock;
	        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
	        
	        // Qui puoi aggiornare l'interfaccia utente per mostrare il prodotto selezionato
	        //aggiornaUI();
	    }

	    private void aggiornaUI() {
	        // Esempio: aggiornare un label nella schermata di acquisto
	        System.out.println("Mostrando informazioni di acquisto per: " + selectedStock.getProdotto().getNome());
	    }
	
}
