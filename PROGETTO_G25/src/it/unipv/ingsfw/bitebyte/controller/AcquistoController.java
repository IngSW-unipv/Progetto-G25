package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Stock;
<<<<<<< HEAD
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class AcquistoController {

    private ViewPrSelected view;
    private Stock stockSelezionato;
    private Stage previousStage; // Memorizza la finestra precedente
    
    public AcquistoController(Stage previousStage) {
        this.view = new ViewPrSelected();
        this.previousStage = previousStage;
    }

    public void setStockSelezionato(Stock stock) {
        this.stockSelezionato = stock;
    }

    // Metodo per mostrare l'interfaccia
    public void mostraInterfaccia(Stock stock, Stage newStage) {
        VBox vbox = view.creaInterfaccia(stock, this, newStage, previousStage);
        Scene scene = new Scene(vbox, 600, 400); // Imposta le dimensioni fisse
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());
        newStage.setScene(scene);
        newStage.setResizable(false); // Impedisce il ridimensionamento della finestra
        newStage.show();
        
        view.aggiornaImmagine(stock);
    }

    // Metodo chiamato quando si preme il bottone "Torna indietro"
    public void tornaIndietro(Stage currentStage) {
        view.mostraGif(); // Mostra la GIF prima di chiudere la finestra
        currentStage.close(); // Chiude la finestra attuale
        previousStage.show(); // Riporta in primo piano la finestra precedente
    }
    
    public void acquistaProdotto(Stock stock) {
        System.out.println("Acquisto effettuato per: " + stock.getProdotto().getNome());
        // Qui puoi aggiungere la logica per gestire l'acquisto
    }
    
    
}
=======
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class AcquistoController {


	    private Stock selectedStock;
	    
	    @FXML
	    private Label prodottoLabel;

	    @FXML
	    private Label prezzoLabel;
	    
	    

	    // Metodo per ricevere lo stock selezionato
	    public void setSelectedStock(Stock stock) {
	        this.selectedStock = stock;
	        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
	        
	        // Qui puoi aggiornare l'interfaccia utente per mostrare il prodotto selezionato
	        aggiornaUI();
	    }

	    private void aggiornaUI() {
	        if (selectedStock != null) {
	        	System.out.println("Prova3");
	            prodottoLabel.setText("Prodotto: " + selectedStock.getProdotto().getNome());
	            prezzoLabel.setText("Prezzo: €" + selectedStock.getProdotto().getPrezzo());
	        }
	    }

	    // Metodo per caricare il file FXML
	    // Metodo per caricare e mostrare la schermata di acquisto
	    /*
	    public void mostraSchermataAcquisto(Stage primaryStage) throws IOException {
	        // Carica il file FXML e imposta il controller
	    	
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/acquisto-view.fxml"));
	        loader.setController(this); // Imposta il controller
	        VBox vbox = loader.load(); // Carica il file FXML

	        // Imposta la scena con il VBox appena caricato
	        Scene acquistoScene = new Scene(vbox);
	        primaryStage.setScene(acquistoScene); // Imposta la scena per la finestra principale
	        primaryStage.setTitle("Acquisto Prodotto");
	        primaryStage.show(); // Mostra la finestra
	    }*/
}
>>>>>>> Copia_alice_davide
