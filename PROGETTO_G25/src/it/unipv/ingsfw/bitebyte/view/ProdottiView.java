package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.controller.GestionePController;
import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
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

public class ProdottiView {

    private VBox rootLayout;
    private FlowPane prodottiContainer;
    private ScrollPane scrollPane;
    private GestionePController controller;
    private ComboBox<Distributore> distributoreDropdown;
    private Button carrelloButton;

    public ProdottiView(GestionePController controller) {
        this.controller = controller;

        rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(10));

        // 🟢 Barra del titolo
        HBox topBar = creaTopBar();

        prodottiContainer = new FlowPane();
        scrollPane = new ScrollPane(prodottiContainer);

        initialize();

        // 🔻 Barra inferiore con menu distributore e bottone carrello
        HBox bottomBar = creaBottomBar();

        rootLayout.getChildren().addAll(topBar, scrollPane, bottomBar);

        // Caricamento CSS
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

        // 🔻 Bottone Carrello
        carrelloButton = new Button("🛒 Carrello");
        carrelloButton.getStyleClass().add("carrello-button");
        carrelloButton.setOnAction(e -> controller.handleApriCarrello());

        // 🔻 Dropdown per selezione Distributore
        distributoreDropdown = new ComboBox<>();
        distributoreDropdown.setPrefHeight(90);
        distributoreDropdown.setPromptText("Seleziona Distributore");
        distributoreDropdown.getStyleClass().add("distributore-dropdown");

        // Carica distributori dal database
        caricaDistributori();

        // Quando cambia il distributore, aggiorniamo i prodotti
        distributoreDropdown.setOnAction(e -> {
            Distributore distributoreSelezionato = distributoreDropdown.getValue();
            if (distributoreSelezionato != null) {
                controller.setIdInventario(distributoreSelezionato.getIdInventario());
            }
        });

        bottomBar.getChildren().addAll(carrelloButton, distributoreDropdown);
        return bottomBar;
    }

    private void caricaDistributori() {
        DistributoreDAO distributoreDAO = new DistributoreDAO();
        List<Distributore> distributori = distributoreDAO.getAllDistributori();
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
        } else {
            System.err.println("❌ Immagine non trovata -> " + productImageFile.getAbsolutePath());
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
        if (stock.getQuantitaDisp() > 5) {
            statusLabel.setText("Stato: Disponibile");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (stock.getQuantitaDisp() == 0) {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        Button restockButton = creaBottoneConIcona("resources/icona spedizione.png", e -> controller.handleRestock(stock));
        Button replaceButton = creaBottoneConIcona("resources/icona-switch (1).png", e -> controller.handleSostituzione(stock));
        Button priceChangeButton = creaBottoneConIcona("resources/icona-modificaprezzo.png", e -> controller.handleCambioPrezzo(stock));

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
        } else {
            System.err.println("❌ Icona non trovata -> " + imageFile.getAbsolutePath());
        }

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("restock-button");
        button.setOnAction(eventHandler);
        return button;
    }
}
