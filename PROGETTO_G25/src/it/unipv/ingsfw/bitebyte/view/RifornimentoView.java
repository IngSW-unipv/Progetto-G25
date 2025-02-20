package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;
//import it.unipv.ingsfw.bitebyte.strategy.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RifornimentoView {
    private VBox rootLayout;
    private FlowPane fornitoriContainer;
    private Button confermaOrdineButton;
    private ComboBox<String> scontoSelector;
    private RifornimentoListener listener;
  //  private ScontoStrategy scontoStrategy;

    public RifornimentoView(ArrayList<Fornitura> forniture, Stock stock, RifornimentoListener listener) {
        this.listener = listener;
    //    this.scontoStrategy = new NessunoSconto(); // Default senza sconto

        rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(15));
        rootLayout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Seleziona un fornitore per: " + stock.getProdotto().getNome());

        fornitoriContainer = new FlowPane();
        fornitoriContainer.setAlignment(Pos.CENTER);
        fornitoriContainer.setHgap(20);
        fornitoriContainer.setVgap(20);

        ScrollPane scrollPane = new ScrollPane(fornitoriContainer);
        scrollPane.setFitToWidth(true);

        for (Fornitura f : forniture) {
            fornitoriContainer.getChildren().add(creaRiquadroFornitura(f));
        }

        scontoSelector = new ComboBox<>();
        scontoSelector.getItems().addAll("Nessuno sconto", "Sconto variabile per quantità", "Sconto a quantità massima", "Sconto progressivo");
        scontoSelector.setValue("Nessuno sconto");
        scontoSelector.setOnAction(e -> aggiornaScontoStrategy());

        confermaOrdineButton = new Button("Conferma Ordine");
        confermaOrdineButton.setOnAction(e -> listener.onConfermaOrdine());

        rootLayout.getChildren().addAll(titleLabel, scontoSelector, scrollPane, confermaOrdineButton);
    }

    private VBox creaRiquadroFornitura(Fornitura forniture) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        box.setPrefSize(350, 200);

        Label nameLabel = new Label("Fornitore: " + forniture.getFornitore().getNomeF());
        Label priceLabel = new Label("Prezzo unitario: €" + forniture.getPpu());

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantità");

        Button selectButton = new Button("Seleziona");
        selectButton.setOnAction(e -> {
            try {
                int quantita = Integer.parseInt(quantityField.getText());
   //             double prezzoFinale = scontoStrategy.calcolaPrezzo(quantita, forniture.getPpu());
    //            listener.onFornitoreSelezionato(forniture, quantita, prezzoFinale);
            } catch (NumberFormatException ex) {
                showError("Inserisci un numero valido per la quantità.");
            }
        });

        box.getChildren().addAll(nameLabel, priceLabel, quantityField, selectButton);
        return box;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void aggiornaScontoStrategy() {
        String selected = scontoSelector.getValue();
        switch (selected) {
            case "Sconto variabile per quantità":
       //         scontoStrategy = new ScontoVariabile();
                break;
            case "Sconto a quantità massima":
       //         scontoStrategy = new ScontoMassimo();
                break;
            case "Sconto progressivo":
       //         scontoStrategy = new ScontoProgressivo();
                break;
            default:
       //         scontoStrategy = new NessunoSconto();
        }
    }

    public void mostra() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Seleziona un fornitore");

        Scene scene = new Scene(getView());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public VBox getView() {
        return rootLayout;
    }

    public interface RifornimentoListener {
        void onFornitoreSelezionato(Fornitura fornitura, int quantita, double prezzoFinale);
        void onConfermaOrdine();
    }
}
