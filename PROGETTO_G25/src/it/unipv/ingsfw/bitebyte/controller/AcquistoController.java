package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Sessione;
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
    	Cliente clienteLoggato = Sessione.getInstance().getClienteConnesso();
        this.stockSelezionato = stock;
        if (clienteLoggato != null) {
            System.out.println("Acquisto effettuato per: " + stock.getProdotto().getNome());
            System.out.println("Utente che ha effettuato l'acquisto: " + clienteLoggato.getNome() + " " + clienteLoggato.getCognome());
            
            // Aggiungi la logica per gestire l'acquisto, associando l'acquisto al cliente loggato
            // Puoi ad esempio chiamare un metodo di acquisto passando il cliente loggato e lo stock
        } else {
            System.out.println("Nessun cliente connesso. Impossibile completare l'acquisto.");
        }
    }

    // Metodo per mostrare l'interfaccia
    public void mostraInterfaccia(Stock stock, Stage newStage) {
    	Cliente clienteLoggato = Sessione.getInstance().getClienteConnesso(); // Recupera il cliente loggato

        // Passa il cliente loggato alla vista per aggiornare l'interfaccia con i suoi dettagli
        VBox vbox = view.creaInterfaccia(stock, this, newStage, previousStage, clienteLoggato);
        Scene scene = new Scene(vbox, 600, 400); // Imposta le dimensioni fisse
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());
        newStage.setScene(scene);
        newStage.setResizable(false); // Impedisce il ridimensionamento della finestra
        newStage.show();
        
        view.aggiornaImmagine(stock);
    }

    // Metodo chiamato quando si preme il bottone "Torna indietro"
    public void tornaIndietro(Stage currentStage) {
        currentStage.close(); // Chiude la finestra attuale
        previousStage.show(); // Riporta in primo piano la finestra precedente
    }
    
    public void acquistaProdotto(Stock stock) {
        System.out.println("Acquisto effettuato per: " + stock.getProdotto().getNome());
        // Qui puoi aggiungere la logica per gestire l'acquisto
    }
    
    
}