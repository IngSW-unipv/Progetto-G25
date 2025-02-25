package it.unipv.ingsfw.bitebyte.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.ingsfw.bitebyte.models.Spedizione;

/**
 * Classe per la visualizzazione dello storico delle spedizioni.
 * Mostra una finestra con l'elenco delle spedizioni e i dettagli dei prodotti associati.
 */
public class StoricoSpedizioniView {

    /**
     * Mostra la finestra con l'elenco delle spedizioni.
     * 
     * @param spedizioni Lista di spedizioni da visualizzare.
     */
    public void mostra(ArrayList<Spedizione> spedizioni) {
        Stage stage = new Stage();
        stage.setTitle("Storico Spedizioni");

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");  
        vbox.setAlignment(Pos.CENTER); 

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.getStyleClass().add("scroll-pane");  
        scrollPane.setFitToWidth(true);

        // Raggruppamento delle spedizioni per ID spedizione
        Map<String, ArrayList<Spedizione>> spedizioniRaggruppate = new HashMap<>();
        for (Spedizione spedizione : spedizioni) {
            spedizioniRaggruppate
                .computeIfAbsent(spedizione.getIdSpedizione(), k -> new ArrayList<>())
                .add(spedizione);
        }

        // Creazione delle sezioni per ogni spedizione
        for (Map.Entry<String, ArrayList<Spedizione>> entry : spedizioniRaggruppate.entrySet()) {
            String idSpedizione = entry.getKey();
            List<Spedizione> prodotti = entry.getValue();

            VBox spedizioneBox = new VBox(10);
            spedizioneBox.getStyleClass().add("spedizione-box");
            spedizioneBox.setAlignment(Pos.CENTER_LEFT);

            Label titoloSpedizione = new Label("ID Spedizione: " + idSpedizione);
            titoloSpedizione.getStyleClass().add("titolo-spedizione");
            spedizioneBox.getChildren().add(titoloSpedizione);

            // Aggiunta dei prodotti alla rispettiva spedizione
            for (Spedizione spedizione : prodotti) {
                HBox hbox = new HBox(10);
                hbox.getStyleClass().add("hbox");  
                hbox.setAlignment(Pos.CENTER_LEFT); 
                
                // Immagine del prodotto
                ImageView imageView = new ImageView();
                imageView.getStyleClass().add("image-view");  
                imageView.setPreserveRatio(true); 
                imageView.setFitHeight(100); 
                imageView.setFitWidth(100); 
                
                File imageFile = new File("resources/immaginiDB/" + spedizione.getIdProdotto() + ".jpg");
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                } 
                
                // Informazioni sul prodotto
                VBox infoBox = new VBox(5);
                infoBox.getStyleClass().add("vbox-info");  
                infoBox.setAlignment(Pos.CENTER_LEFT); 

                Label idProdottoLabel = new Label("ID Prodotto: " + spedizione.getIdProdotto());
                idProdottoLabel.getStyleClass().add("id-prodotto-label"); 
                Label quantitaLabel = new Label("Quantità: " + spedizione.getqOrd());
                quantitaLabel.getStyleClass().add("quantita-label");  
                Label prezzoTotLabel = new Label("Prezzo Totale: €" + spedizione.getPrezzoTot());
                prezzoTotLabel.getStyleClass().add("prezzo-totale-label");  
                Label dataLabel = new Label("Data spedizione: " + spedizione.getDataSp());
                dataLabel.getStyleClass().add("data-label");  

                infoBox.getChildren().addAll(idProdottoLabel, quantitaLabel, prezzoTotLabel, dataLabel);
                hbox.getChildren().addAll(imageView, infoBox);
                spedizioneBox.getChildren().add(hbox);
            }
            vbox.getChildren().add(spedizioneBox);
        }

        // Barra inferiore con pulsante di chiusura
        HBox bottomBar = new HBox();
        bottomBar.getStyleClass().add("bottom-bar"); 
        bottomBar.setAlignment(Pos.CENTER);
        Button closeButton = new Button("Chiudi");
        closeButton.getStyleClass().add("button"); 
        closeButton.setOnAction(e -> stage.close());
        bottomBar.getChildren().add(closeButton);

        vbox.getChildren().add(bottomBar);

        // Creazione e visualizzazione della scena
        Scene scene = new Scene(scrollPane, 600, 500); 
        scene.getStylesheets().add(getClass().getResource("/css/StileSpedizioni.css").toExternalForm()); 
        stage.setScene(scene);
        stage.show();
    }
}
