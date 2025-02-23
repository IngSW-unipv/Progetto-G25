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

public class ModificaPrezzoView {
    private Stage stage;
    private TextField prezzoField;
    private Button confermaButton;
    private Stock stock;
    private OnPriceUpdateListener listener;

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

    public void getNuovoPrezzo() {
        try {
            BigDecimal nuovoPrezzo = new BigDecimal(prezzoField.getText());
            listener.onPriceUpdated(stock, nuovoPrezzo);
            stage.close();
        } catch (NumberFormatException e) {
            mostraErrore("Inserisci un valore numerico valido!");
        }
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
    
    public void show() {
        stage.showAndWait();
    }
    //Interfaccia implementata come classe anonima nel controller
    public interface OnPriceUpdateListener {
        void onPriceUpdated(Stock stock, BigDecimal nuovoPrezzo);
    }
}
