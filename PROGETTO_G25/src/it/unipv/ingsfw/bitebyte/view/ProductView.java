package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.function.Consumer;

public class ProductView {

    /**
     * Crea e restituisce la view (BorderPane) per un prodotto.
     *
     * @param stock                   il modello da rappresentare
     * @param modalitaVisualizzazione se siamo in modalità di sola visualizzazione
     * @param onSelect                callback per la selezione del prodotto
     * @param onShowAlternatives      callback per mostrare distributori alternativi
     * @return il BorderPane che rappresenta la view del prodotto
     */
    public static BorderPane createProductView(Stock stock, boolean modalitaVisualizzazione,
                                                 Consumer<Stock> onSelect, Consumer<Stock> onShowAlternatives) {
        BorderPane productPane = new BorderPane();
        productPane.getStyleClass().add("product-box");
        productPane.setPadding(new Insets(10));

        // Creazione e configurazione dell'immagine del prodotto
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        File productImageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (productImageFile.exists()) {
            imageView.setImage(new Image(productImageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(ProductView.class.getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Creazione delle etichette per nome, prezzo, quantità e stato
        Label nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");

        Label priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");

        Label quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");

        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("product-status");
        if (stock.getQuantitaDisp() > 0) {
            if ("Disponibile".equals(stock.getStato())) {
                statusLabel.setText("Stato: Disponibile");
                statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else if ("Non disponibile".equals(stock.getStato())) {
                statusLabel.setText("Stato: Non disponibile");
                statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        // Raggruppa l'immagine e le etichette in una VBox centrata
        VBox centerBox = new VBox(5, imageView, nameLabel, priceLabel, quantityLabel, statusLabel);
        centerBox.setAlignment(Pos.CENTER);
        productPane.setCenter(centerBox);

        // Se non siamo in modalità visualizzazione, aggiungi il bottone nella parte inferiore
        if (!modalitaVisualizzazione) {
            Button actionButton;
            if (stock.getQuantitaDisp() == 0) {
                actionButton = new Button("Visualizza distributori vicini");
                actionButton.setOnAction(e -> onShowAlternatives.accept(stock));
            } else {
                actionButton = new Button("Seleziona");
                actionButton.setOnAction(e -> onSelect.accept(stock));
            }
            actionButton.getStyleClass().add("select-button");

            // Inserisci il bottone in un HBox centrato con margini fissi
            HBox buttonContainer = new HBox(actionButton);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setPadding(new Insets(10, 0, 10, 0));
            productPane.setBottom(buttonContainer);
        }

        return productPane;
    }
}

