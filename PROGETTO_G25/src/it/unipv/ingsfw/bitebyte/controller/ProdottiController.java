package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.StockDAO;
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

import java.io.File;
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
        box.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-radius: 5; -fx-alignment: center;");
        box.setPrefWidth(180); // Larghezza fissa per evitare layout errati

        // Immagine del prodotto con dimensioni fisse
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        // Caricamento immagine con fallback
        File file = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (file.exists()) {
            imageView.setImage(new Image(file.toURI().toString()));
        } else {
            System.err.println("❌ Immagine non trovata -> " + file.getAbsolutePath());
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Etichette con info prodotto
        Label nameLabel = new Label(stock.getProdotto().getNome());
        Label priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        Label quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        Label statusLabel = new Label("Stato: " + stock.getStato());

        // Bottone di rifornimento
        Button restockButton = new Button("Rifornisci");
        restockButton.setOnAction(e -> handleRestock(stock));

        // Aggiunta di tutti gli elementi al box del prodotto
        box.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel, statusLabel, restockButton);
        return box;
    }

    private void handleRestock(Stock stock) {
        System.out.println("Rifornimento per: " + stock.getProdotto().getNome());
    }
}

