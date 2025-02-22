package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Spedizione;
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
import javafx.scene.layout.Priority;
import java.io.File;
import java.util.ArrayList;

public class StoricoSpedizioniView {

    // Metodo per mostrare lo storico delle spedizioni
    public void mostra(ArrayList<Spedizione> spedizioni) {
        // Crea la finestra
        Stage stage = new Stage();
        stage.setTitle("Storico Spedizioni");

        // Crea un layout principale (VBox)
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");  // Aggiungi classe CSS per il VBox
        vbox.setAlignment(Pos.CENTER); // Allinea gli elementi al centro

        // Usa uno ScrollPane per la lista delle spedizioni (per gestire le spedizioni lunghe)
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.getStyleClass().add("scroll-pane");  // Aggiungi classe CSS per lo ScrollPane
        scrollPane.setFitToWidth(true); // Imposta la larghezza della finestra a quella del contenuto

        // Aggiungi ogni spedizione alla vista
        for (Spedizione spedizione : spedizioni) {
            // Crea il layout orizzontale per la spedizione
            HBox hbox = new HBox(10);
            hbox.getStyleClass().add("hbox");  // Aggiungi classe CSS per l'HBox
            hbox.setAlignment(Pos.CENTER_LEFT); // Allinea gli elementi al centro

            // Crea un'ImageView per l'immagine del prodotto
            ImageView imageView = new ImageView();
            imageView.getStyleClass().add("image-view");  // Aggiungi classe CSS per l'immagine
            imageView.setPreserveRatio(true); // Mantieni il rapporto di aspetto
            imageView.setFitHeight(100); // Imposta un'altezza fissa per l'immagine
            imageView.setFitWidth(100); // Imposta la larghezza fissa per l'immagine

            // Carica l'immagine corrispondente al prodotto
            File imageFile = new File("resources/immaginiDB/" + spedizione.getIdProdotto() + ".jpg");
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            } else {
                // Se l'immagine non esiste, carica un'immagine di default
                Image image = new Image("resources/immaginiDB/default.jpg"); // esempio di immagine di default
                imageView.setImage(image);
            }

            // Crea una VBox per le informazioni del prodotto
            VBox infoBox = new VBox(5);
            infoBox.getStyleClass().add("vbox-info");  // Aggiungi classe CSS per la VBox delle info
            infoBox.setAlignment(Pos.CENTER_LEFT); // Allinea a sinistra dentro la VBox

            // Crea le label con classi uniche
            Label idSpedizioneLabel = new Label("ID Spedizione: " + spedizione.getIdSpedizione());
            idSpedizioneLabel.getStyleClass().add("id-spedizione-label");  // Classe CSS unica

            Label idProdottoLabel = new Label("ID Prodotto: " + spedizione.getIdProdotto());
            idProdottoLabel.getStyleClass().add("id-prodotto-label");  // Classe CSS unica

            Label quantitaLabel = new Label("Quantità: " + spedizione.getqOrd());
            quantitaLabel.getStyleClass().add("quantita-label");  // Classe CSS unica

            Label prezzoTotLabel = new Label("Prezzo Totale: €" + spedizione.getPrezzoTot());
            prezzoTotLabel.getStyleClass().add("prezzo-totale-label");  // Classe CSS unica

            Label dataLabel = new Label("Data spedizione: " + spedizione.getDataSp());
            dataLabel.getStyleClass().add("data-label");  // Classe CSS unica

            // Aggiungi le label alla VBox
            infoBox.getChildren().addAll(idSpedizioneLabel, idProdottoLabel, quantitaLabel, prezzoTotLabel, dataLabel);

            // Aggiungi l'immagine e le informazioni al layout orizzontale
            hbox.getChildren().addAll(imageView, infoBox);

            // Aggiungi il riquadro della spedizione al layout principale
            vbox.getChildren().add(hbox);
        }

        // Crea la bottomBar con il bottone
        HBox bottomBar = new HBox();
        bottomBar.getStyleClass().add("bottom-bar");  // Aggiungi classe CSS per la bottom bar
        bottomBar.setAlignment(Pos.CENTER); // Allinea il bottone al centro della bottom bar
        Button closeButton = new Button("Chiudi");
        closeButton.getStyleClass().add("button");  // Aggiungi classe CSS per il bottone
        closeButton.setOnAction(e -> stage.close());
        bottomBar.getChildren().add(closeButton);

        // Aggiungi la bottomBar al layout principale
        vbox.getChildren().add(bottomBar);

        // Crea la scena e imposta la scena della finestra
        Scene scene = new Scene(scrollPane, 600, 500); // Imposta una larghezza di default
        scene.getStylesheets().add(getClass().getResource("/css/StileSpedizioni.css").toExternalForm()); // Carica il file CSS
        stage.setScene(scene);
        stage.show();
    }
}
