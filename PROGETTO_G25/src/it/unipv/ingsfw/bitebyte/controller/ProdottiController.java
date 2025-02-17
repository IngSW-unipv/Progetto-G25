package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import it.unipv.ingsfw.bitebyte.models.*;
import javafx.scene.layout.HBox;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ProdottiController {

    private StockDAO s = new StockDAO();

    @FXML
    private FlowPane prodottiContainer;

    @FXML
    private ScrollPane scrollPane;

    private int idInventario;

    public void initialize() {
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        prodottiContainer.setPrefWrapLength(600); // Cambia dinamicamente

        // Assicura che il ScrollPane si adatti alla finestra
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            prodottiContainer.setPrefWrapLength(newVal.doubleValue() - 20);
        });
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        caricaProdotti();
    }

    public void caricaProdotti() {
        ArrayList<Stock> stocks = s.getStockByInventario(idInventario);
        prodottiContainer.getChildren().clear(); // Puliamo i prodotti prima di caricarli

        for (Stock stock : stocks) {
            VBox productBox = createProductBox(stock);
            prodottiContainer.getChildren().add(productBox);
        }
    }

    private VBox createProductBox(Stock stock) {
        VBox box = new VBox(5);
        box.getStyleClass().add("product-box");

        // Immagine del prodotto con dimensioni fisse
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        // Caricamento immagine prodotto con fallback
        File productImageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (productImageFile.exists()) {
            imageView.setImage(new Image(productImageFile.toURI().toString()));
        } else {
            System.err.println("❌ Immagine non trovata -> " + productImageFile.getAbsolutePath());
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Etichette con info prodotto
        Label nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");

        Label priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");

        Label quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");

        Label statusLabel = new Label("Stato: " + stock.getStato());
        statusLabel.getStyleClass().add("product-status");
        if (stock.getQuantitaDisp() > 5) {
            statusLabel.setText("Stato: Disponibile");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (stock.getQuantitaDisp() == 0) {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        // Bottone di rifornimento con immagine
        ImageView restockIcon = new ImageView();
        restockIcon.setFitWidth(20);
        restockIcon.setFitHeight(20);
        restockIcon.setPreserveRatio(true);

        File restockImageFile = new File("resources/modifica-prezzo-removebg-preview.png");
        if (restockImageFile.exists()) {
            restockIcon.setImage(new Image(restockImageFile.toURI().toString()));
        } else {
            System.err.println("❌ Icona non trovata -> " + restockImageFile.getAbsolutePath());
            restockIcon.setImage(null); // Non impostiamo un'icona se non esiste
        }

        Button restockButton = createImageButton("resources/icona spedizione.png", e -> handleRestock(stock));
        Button replaceButton = createImageButton("resources/icona-switch (1).png", e -> handleSostituzione(stock));
        Button priceChangeButton = createImageButton("resources/icona-modificaprezzo.png", e -> handleCambioPrezzo(stock));
     // Contenitore orizzontale per i bottoni
        HBox buttonContainer = new HBox(10); // Spaziatura tra bottoni
        buttonContainer.getChildren().addAll(restockButton, replaceButton, priceChangeButton);
        
        // Aggiunta degli elementi al box prodotto
        box.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel, statusLabel, buttonContainer);
        return box;
    }
    
 // Metodo per creare un bottone con immagine
    private Button createImageButton(String imagePath, EventHandler<ActionEvent> eventHandler) {
        ImageView icon = new ImageView();
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        icon.setPreserveRatio(true);

        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            icon.setImage(new Image(imageFile.toURI().toString()));
        } else {
            System.err.println("❌ Icona non trovata -> " + imageFile.getAbsolutePath());
            icon.setImage(null);
        }

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("restock-button"); // Mantiene lo stile attuale
        button.setOnAction(eventHandler);
        return button;
    }

    
    private void handleRestock(Stock stock) {
        System.out.println("Rifornimento per: " + stock.getProdotto().getNome());
    }
    
    private void handleSostituzione(Stock stock) {
        System.out.println("Sostituzione per: " + stock.getProdotto().getNome());
    }

    private void handleCambioPrezzo(Stock stock) {
        System.out.println("Cambio prezzo per: " + stock.getProdotto().getNome());
    }
}

