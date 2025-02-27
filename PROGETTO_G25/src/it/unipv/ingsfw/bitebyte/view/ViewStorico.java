package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Ordine;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import java.util.List;

public class ViewStorico {

    public ScrollPane creaInterfaccia(List<Ordine> ordini) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));
        vbox.setAlignment(Pos.CENTER);

        if (ordini != null && !ordini.isEmpty()) {
            for (Ordine ordine : ordini) {
                vbox.getChildren().add(creaOrdineBox(ordine));
            }
        } else {
            Label noOrdersLabel = new Label("Nessun ordine disponibile.");
            noOrdersLabel.setStyle("-fx-font-size: 18px;");
            vbox.getChildren().add(noOrdersLabel);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(400);
        return scrollPane;
    }

    private HBox creaOrdineBox(Ordine ordine) {
        HBox ordineContainer = new HBox(20);
        ordineContainer.setAlignment(Pos.CENTER_LEFT);
        ordineContainer.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px;");

        Label ordineLabel = new Label("Ordine: " + ordine.getIdOrdine());
        Label dataLabel = new Label("Data: " + ordine.getDataOrd().toLocalDate());
        Label statoLabel = new Label("Stato: " + ordine.getStatoOrd());
        Label nomeLabel = new Label("Nome: " + ordine.getProdotto().getNome());
        Label prezzoLabel = new Label("Prezzo: " + ordine.getTotale());

        // Assegna ID per test e CSS
        ordineLabel.setId("ordine-label-" + ordine.getIdOrdine());

        // Aggiungi pulsante dettagli
        Button dettagliBtn = new Button("Dettagli");
        dettagliBtn.setOnAction(e -> mostraDettagliOrdine(ordine));

        ordineContainer.getChildren().addAll(ordineLabel, dataLabel, statoLabel, nomeLabel, prezzoLabel, dettagliBtn);
        return ordineContainer;
    }

    private void mostraDettagliOrdine(Ordine ordine) {
        System.out.println("Mostra dettagli per ordine: " + ordine.getIdOrdine());
        // Qui puoi aggiungere una finestra di dialogo con i dettagli
    }
}
