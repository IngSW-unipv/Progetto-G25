package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Finestra per modificare il prezzo di un prodotto presente nello stock.
 */
public class ModificaPrezzoView {
    private Stage stage;
    private TextField prezzoField;
    private Button confermaButton;
    private Stock stock;
    private OnPriceUpdateListener listener;

    /**
     * Costruttore della finestra di modifica prezzo.
     * 
     * @param stock    L'oggetto Stock relativo al prodotto.
     * @param listener Listener per l'aggiornamento del prezzo.
     */
    public ModificaPrezzoView(Stock stock, OnPriceUpdateListener listener) {
        this.stock = stock;
        this.listener = listener;

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Modifica Prezzo");

        Label istruzioni = new Label("Inserisci il nuovo prezzo:");
        prezzoField = new TextField();
        prezzoField.setPromptText("Nuovo prezzo");

        confermaButton = new Button("Conferma");
        confermaButton.setId("confermaButton");
        confermaButton.setOnAction(e -> getNuovoPrezzo());
        
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(istruzioni, prezzoField, confermaButton);

        Scene scene = new Scene(layout, 300, 200);
        String cssPath = "/css/StileModificaPrezzo.css"; 
        try {
            scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm()
            );
        } catch (NullPointerException e) {
            System.err.println("Errore: Il file CSS non è stato trovato in " + cssPath);
        }
        stage.setScene(scene);
    }

    /**
     * Recupera il nuovo prezzo inserito e lo passa al listener.
     * Se l'input non è valido, mostra un messaggio di errore.
     */
    public void getNuovoPrezzo() {
        try {
            BigDecimal nuovoPrezzo = new BigDecimal(prezzoField.getText());
            listener.onPriceUpdated(stock, nuovoPrezzo);
            stage.close();
        } catch (NumberFormatException e) {
            mostraErrore("Inserisci un valore numerico valido!");
        }
    }

    /**
     * Mostra un messaggio di errore in una finestra di dialogo.
     * 
     * @param messaggio Il messaggio di errore da mostrare.
     */
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
    
    /**
     * Mostra la finestra di modifica prezzo.
     */
    public void show() {
        stage.showAndWait();
    }
    
    /**
     * Interfaccia per gestire l'aggiornamento del prezzo
     * L'implementazione come classe anonima verrà fatta nel controller.
     */
    public interface OnPriceUpdateListener {
        void onPriceUpdated(Stock stock, BigDecimal nuovoPrezzo);
    }
}
