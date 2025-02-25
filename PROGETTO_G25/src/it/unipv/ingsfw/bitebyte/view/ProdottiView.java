package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ProdottiView {

    private VBox rootLayout;
    private FlowPane prodottiContainer;
    private ScrollPane scrollPane;
    private ComboBox<Distributore> distributoreDropdown;
    private Button carrelloButton;

    private Runnable onApriCarrello;
    private Runnable onApriStoricoSpedizioni;
    private Consumer<Integer> onSelezionaDistributore;
    private Consumer<Stock> onRestock;
    private Consumer<Stock> onSostituzione;
    private Consumer<Stock> onCambioPrezzo;

    public ProdottiView() {
        rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(10));

        // Barra del titolo
        HBox topBar = creaTopBar();

        prodottiContainer = new FlowPane();
        scrollPane = new ScrollPane(prodottiContainer);
        initialize();

        // Barra inferiore
        HBox bottomBar = creaBottomBar();

        rootLayout.getChildren().addAll(topBar, scrollPane, bottomBar);
        rootLayout.getStylesheets().add(getClass().getResource("/css/StileAmministratore.css").toExternalForm());
    }

    private void initialize() {
        prodottiContainer.setAlignment(Pos.CENTER);
        prodottiContainer.setHgap(20);
        prodottiContainer.setVgap(20);
        prodottiContainer.setPrefWrapLength(600);
        prodottiContainer.setStyle("-fx-padding: 20px;");

        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        scrollPane.setFitToWidth(true);
    }

    private HBox creaTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));
        topBar.getStyleClass().add("top-bar");

        Label titleLabel = new Label("Gestione Prodotti");
        titleLabel.getStyleClass().add("title-label");

        topBar.getChildren().add(titleLabel);
        return topBar;
    }

    private HBox creaBottomBar() {
        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));
        bottomBar.getStyleClass().add("bottom-bar");

        carrelloButton = new Button("🛒 Carrello");
        carrelloButton.getStyleClass().add("carrello-button");
        carrelloButton.setOnAction(e -> {
            if (onApriCarrello != null) onApriCarrello.run();
        });

        Button storicoSpedizioniButton = new Button("Storico Spedizioni");
        storicoSpedizioniButton.getStyleClass().add("storico-spedizioni-button");
        storicoSpedizioniButton.setOnAction(e -> {
            if (onApriStoricoSpedizioni != null) onApriStoricoSpedizioni.run();
        });

        distributoreDropdown = new ComboBox<>();
        distributoreDropdown.setPrefHeight(90);
        distributoreDropdown.setPromptText("Seleziona Distributore");
        distributoreDropdown.getStyleClass().add("distributore-dropdown");

        distributoreDropdown.setOnAction(e -> {
            Distributore distributoreSelezionato = distributoreDropdown.getValue();
            if (distributoreSelezionato != null && onSelezionaDistributore != null) {
                onSelezionaDistributore.accept(distributoreSelezionato.getIdInventario());
            }
        });

        bottomBar.getChildren().addAll(carrelloButton, distributoreDropdown, storicoSpedizioniButton);
        return bottomBar;
    }

    public void setDistributori(List<Distributore> distributori) {
        distributoreDropdown.getItems().clear();
        distributoreDropdown.getItems().addAll(distributori);
    }

    public VBox getView() {
        return rootLayout;
    }

    public void aggiornaProdotti(ArrayList<Stock> stocks) {
        prodottiContainer.getChildren().clear();
        for (Stock stock : stocks) {
            prodottiContainer.getChildren().add(creaProductBox(stock));
        }
    }

    private VBox creaProductBox(Stock stock) {
        VBox box = new VBox(10);
        box.getStyleClass().add("product-box");
        box.setAlignment(Pos.CENTER);

        VBox imageContainer = new VBox();
        imageContainer.setPrefHeight(140);
        imageContainer.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        File productImageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (productImageFile.exists()) {
            imageView.setImage(new Image(productImageFile.toURI().toString()));
        } 
        imageContainer.getChildren().add(imageView);

        Label nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");
        Label priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");
        Label quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");
        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("product-status");

        if (stock.getQuantitaDisp() > 0) {
            statusLabel.setText("Stato: Disponibile");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        Button restockButton = creaBottoneConIcona("resources/icona spedizione.png", e -> {
            if (onRestock != null) onRestock.accept(stock);
        });

        Button replaceButton = creaBottoneConIcona("resources/icona-switch (1).png", e -> {
            if (onSostituzione != null) onSostituzione.accept(stock);
        });

        Button priceChangeButton = creaBottoneConIcona("resources/icona-modificaprezzo.png", e -> {
            if (onCambioPrezzo != null) onCambioPrezzo.accept(stock);
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(restockButton, replaceButton, priceChangeButton);
        buttonContainer.getStyleClass().add("button-container");

        box.getChildren().addAll(imageContainer, nameLabel, priceLabel, quantityLabel, statusLabel, buttonContainer);
        return box;
    }

    private Button creaBottoneConIcona(String imagePath, javafx.event.EventHandler<javafx.event.ActionEvent> eventHandler) {
        ImageView icon = new ImageView();
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        icon.setPreserveRatio(true);

        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            icon.setImage(new Image(imageFile.toURI().toString()));
        } 
        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("restock-button");
        button.setOnAction(eventHandler);
        return button;
    }
    
    public void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/StileModificaPrezzo.css").toExternalForm());
        alert.showAndWait();
    }

    public void setOnApriCarrello(Runnable listener) { this.onApriCarrello = listener; }
    public void setOnApriStoricoSpedizioni(Runnable listener) { this.onApriStoricoSpedizioni = listener; }
    public void setOnSelezionaDistributore(Consumer<Integer> listener) { this.onSelezionaDistributore = listener; }
    public void setOnRestock(Consumer<Stock> listener) { this.onRestock = listener; }
    public void setOnSostituzione(Consumer<Stock> listener) { this.onSostituzione = listener; }
    public void setOnCambioPrezzo(Consumer<Stock> listener) { this.onCambioPrezzo = listener; }

}
