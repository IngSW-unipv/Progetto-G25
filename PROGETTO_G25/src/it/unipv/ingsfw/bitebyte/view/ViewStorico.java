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

    private VBox vbox;  // Riferimento alla VBox principale

    public ScrollPane creaInterfaccia(List<Ordine> ordini) {
        vbox = new VBox(15);  // Imposta lo spazio tra gli elementi
        vbox.setPadding(new Insets(15));  // Aggiunge padding ai bordi
        vbox.setAlignment(Pos.CENTER);  // Allinea al centro gli elementi

        // Se ci sono ordini, visualizzali
        if (ordini != null && !ordini.isEmpty()) {
            for (Ordine ordine : ordini) {
                HBox ordineContainer = new HBox(20);  // Usa HBox per disporre gli elementi orizzontalmente
                ordineContainer.setAlignment(Pos.CENTER_LEFT);
                ordineContainer.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px;");

                // Crea i label per ogni dato dell'ordine
                Label ordineLabel = new Label("Ordine: " + ordine.getIdOrdine());
                ordineLabel.setStyle("-fx-font-size: 16px;");

                Label dataLabel = new Label("Data: " + ordine.getDataOrd().toLocalDate());
                dataLabel.setStyle("-fx-font-size: 16px;");

                
                System.out.println(ordine.getStatoOrd());
                Label statoLabel = new Label("Stato: " + ordine.getStatoOrd());
                statoLabel.setStyle("-fx-font-size: 16px;");
                
                Label nomeLabel = new Label("Nome: " + ordine.getProdotto().getNome());
                nomeLabel.setStyle("-fx-font-size: 16px;");
                
                Label prezzoLabel = new Label("Prezzo: " + ordine.getTotale());
                prezzoLabel.setStyle("-fx-font-size: 16px;");

                // Aggiungi i label all'HBox
                ordineContainer.getChildren().addAll(ordineLabel, dataLabel, statoLabel,nomeLabel, prezzoLabel);
                
                // Aggiungi l'HBox alla VBox principale
                vbox.getChildren().add(ordineContainer);
            }
        } else {
            Label noOrdersLabel = new Label("Nessun ordine disponibile.");
            noOrdersLabel.setStyle("-fx-font-size: 18px;");
            vbox.getChildren().add(noOrdersLabel);
        }

        // Aggiungi un ScrollPane per abilitare lo scrolling
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true); // Assicurati che il contenuto si adatti alla larghezza della finestra
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefWidth(600);  // Larghezza fissa
        scrollPane.setPrefHeight(400); // Altezza fissa
        return scrollPane;
    }
}
