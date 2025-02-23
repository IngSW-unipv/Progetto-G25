package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Stock;
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