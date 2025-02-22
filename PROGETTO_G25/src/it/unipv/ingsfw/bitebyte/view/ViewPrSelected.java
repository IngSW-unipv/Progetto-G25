package it.unipv.ingsfw.bitebyte.view;

import javafx.scene.layout.VBox;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

//import immagini

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import javafx.scene.control.Button;
import java.io.File;

public class ViewPrSelected {

    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private Label statusLabel;
    private Button selectButton;

    // Metodo per creare l'interfaccia grafica
    public void creaInterfaccia(Stock stock, boolean modalitaVisualizzazione, Runnable onSelectAction) {
        // Creazione della VBox per la disposizione verticale
        VBox vboxStock = new VBox(5);  // Spaziatura di 5 tra gli elementi
        vboxStock.getStyleClass().add("product-box");

        // Inizializza i nodi
        imageView = new ImageView();
        imageView.setFitWidth(120);  // Larghezza fissa
        imageView.setFitHeight(120); // Altezza fissa
        imageView.setPreserveRatio(true);  // Mantiene la proporzione

        nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");

        priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");

        quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");

        statusLabel = new Label();
        statusLabel.getStyleClass().add("product-status");

        // Imposta lo stato e lo stile
        if (stock.getQuantitaDisp() > 0) {
            if ("Disponibile".equals(stock.getStato())) {
                statusLabel.setText("Stato: Disponibile");
                statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                statusLabel.setText("Stato: Non disponibile");
                statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        // Bottone per visualizzare o selezionare
        selectButton = new Button();
        if (modalitaVisualizzazione) {
            selectButton.setText("Visualizza");
            selectButton.setDisable(true);  // Disabilita il bottone in modalità visualizzazione
        } else {
            if (stock.getQuantitaDisp() == 0) {
                selectButton.setText("Visualizza distributori vicini");
            } else {
                selectButton.setText("Seleziona");
            }
            selectButton.setOnAction(e -> onSelectAction.run());
        }

        // Imposta l'immagine del prodotto
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Aggiungi tutti gli elementi alla VBox
        vboxStock.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel, statusLabel, selectButton);

        // Crea la scena
        Scene scene = new Scene(vboxStock, 300, 350); // Dimensione della finestra
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());
        // Creazione dello stage (finestra)
        Stage stage = new Stage();
        stage.setTitle("Dettagli Prodotto");
        stage.setScene(scene);
        stage.show();
    }
}