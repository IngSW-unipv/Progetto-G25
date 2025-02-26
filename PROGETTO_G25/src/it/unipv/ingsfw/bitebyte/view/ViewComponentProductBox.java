package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

public class ViewComponentProductBox extends VBox {
    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private Label statusLabel;
    private Button selectButton;

    /**
     * Crea la "card" prodotto e la popola con i dati.
     *
     * @param stock Il modello del prodotto.
     * @param modalitaVisualizzazione Se true, il bottone non è interattivo.
     * @param onSelectAction Azione da eseguire al click sul bottone.
     */
    public ViewComponentProductBox(Stock stock, boolean modalitaVisualizzazione, Runnable onSelectAction) {
        super(5); // Spaziatura di 5
        this.getStyleClass().add("product-box");

        // Inizializza i nodi
        imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

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
/*
        selectButton = new Button();
        if (modalitaVisualizzazione) {
            selectButton.setText("Visualizza");
            selectButton.setDisable(true);
        } else {
            if (stock.getQuantitaDisp() == 0) {
                selectButton.setText("Visualizza distributori vicini");
            } else {
                selectButton.setText("Seleziona");
            }
            selectButton.setOnAction(e -> onSelectAction.run());
        }
        */
        selectButton = new Button();
        if (modalitaVisualizzazione) {
            selectButton.setText("Visualizza");
            selectButton.setDisable(true);
        } else {
            // Se la quantità disponibile è zero oppure lo stato non è "Disponibile",
            // mostriamo "Visualizza distributori vicini".
            if (stock.getQuantitaDisp() == 0 || !"Disponibile".equals(stock.getStato())) {
                selectButton.setText("Visualizza distributori vicini");
            } else {
                selectButton.setText("Seleziona");
            }
            selectButton.setOnAction(e -> onSelectAction.run());
        }
        
    

        // Imposta l'immagine
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Aggiungi i nodi al layout
        this.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel, statusLabel, selectButton);
    }
}
