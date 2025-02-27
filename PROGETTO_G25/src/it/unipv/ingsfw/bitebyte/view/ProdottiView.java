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

    private VBox rootLayout;               // Contenitore principale della vista
    private FlowPane prodottiContainer;    // Contenitore per visualizzare i prodotti
    private ScrollPane scrollPane;         // Scroll per i prodotti
    private ComboBox<Distributore> distributoreDropdown; // Dropdown per selezionare il distributore
    private Button carrelloButton;         // Bottone per aprire il carrello

    // Funzioni di callback per gli eventi
    private Runnable onApriCarrello;
    private Runnable onApriStoricoSpedizioni;
    private Consumer<Integer> onSelezionaDistributore;
    private Consumer<Stock> onRestock;
    private Consumer<Stock> onSostituzione;
    private Consumer<Stock> onCambioPrezzo;

    public ProdottiView() {
        rootLayout = new VBox(10);         // Layout principale con spazio tra gli elementi
        rootLayout.setPadding(new Insets(10));  // Aggiunge un padding attorno al layout

        // Barra del titolo
        HBox topBar = creaTopBar();

        // Contenitore per i prodotti
        prodottiContainer = new FlowPane();
        scrollPane = new ScrollPane(prodottiContainer);
        initialize();

        // Barra inferiore con bottoni
        HBox bottomBar = creaBottomBar();

        // Aggiungi tutti gli elementi al layout principale
        rootLayout.getChildren().addAll(topBar, scrollPane, bottomBar);
        rootLayout.getStylesheets().add(getClass().getResource("/css/StileAmministratore.css").toExternalForm());
    }

    // Metodo per inizializzare alcune proprietà del contenitore dei prodotti
    private void initialize() {
        prodottiContainer.setAlignment(Pos.CENTER);       // Allineamento dei prodotti al centro
        prodottiContainer.setHgap(20);                    // Spaziatura orizzontale tra i prodotti
        prodottiContainer.setVgap(20);                    // Spaziatura verticale tra i prodotti
        prodottiContainer.setPrefWrapLength(600);         // Larghezza preferita per l'avvolgimento
        prodottiContainer.setStyle("-fx-padding: 20px;"); // Padding per i prodotti

        // Imposta la larghezza del contenitore in base alla larghezza della scrollPane
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        scrollPane.setFitToWidth(true);   // Imposta la scrollPane per adattarsi alla larghezza
    }

    // Crea la barra superiore con il titolo
    private HBox creaTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);   // Allineamento al centro
        topBar.setPadding(new Insets(10)); // Padding per la barra superiore
        topBar.getStyleClass().add("top-bar");  // Applica uno stile CSS

        Label titleLabel = new Label("Gestione Prodotti");  // Etichetta per il titolo
        titleLabel.getStyleClass().add("title-label");       // Applica uno stile al titolo

        topBar.getChildren().add(titleLabel);  // Aggiungi l'etichetta alla barra superiore
        return topBar;
    }

    // Crea la barra inferiore con bottoni e dropdown
    private HBox creaBottomBar() {
        HBox bottomBar = new HBox(20);  // Barra inferiore con distanza tra gli elementi
        bottomBar.setAlignment(Pos.CENTER);  // Allineamento al centro
        bottomBar.setPadding(new Insets(10));  // Padding per la barra inferiore
        bottomBar.getStyleClass().add("bottom-bar");  // Applica uno stile CSS

        // Bottone per aprire il carrello
        carrelloButton = new Button("🛒 Carrello");
        carrelloButton.getStyleClass().add("carrello-button");
        carrelloButton.setOnAction(e -> {
            if (onApriCarrello != null) onApriCarrello.run();  // Se è stato definito, esegui l'azione
        });

        // Bottone per aprire lo storico spedizioni
        Button storicoSpedizioniButton = new Button("Storico Spedizioni");
        storicoSpedizioniButton.getStyleClass().add("storico-spedizioni-button");
        storicoSpedizioniButton.setOnAction(e -> {
            if (onApriStoricoSpedizioni != null) onApriStoricoSpedizioni.run();  // Azione per storico
        });

        // Dropdown per selezionare il distributore
        distributoreDropdown = new ComboBox<>();
        distributoreDropdown.setPrefHeight(90);  // Imposta l'altezza preferita
        distributoreDropdown.setPromptText("Seleziona Distributore");
        distributoreDropdown.getStyleClass().add("distributore-dropdown");

        distributoreDropdown.setOnAction(e -> {
            Distributore distributoreSelezionato = distributoreDropdown.getValue();
            if (distributoreSelezionato != null && onSelezionaDistributore != null) {
                onSelezionaDistributore.accept(distributoreSelezionato.getIdInventario());
            }
        });

        // Aggiungi tutti gli elementi alla barra inferiore
        bottomBar.getChildren().addAll(carrelloButton, distributoreDropdown, storicoSpedizioniButton);
        return bottomBar;
    }

    // Imposta i distributori nel ComboBox
    public void setDistributori(List<Distributore> distributori) {
        distributoreDropdown.getItems().clear();
        distributoreDropdown.getItems().addAll(distributori);
    }

    // Restituisce la vista principale
    public VBox getView() {
        return rootLayout;
    }

    // Aggiorna la visualizzazione dei prodotti
    public void aggiornaProdotti(ArrayList<Stock> stocks) {
        prodottiContainer.getChildren().clear();   // Rimuove i prodotti esistenti
        for (Stock stock : stocks) {
            prodottiContainer.getChildren().add(creaProductBox(stock));  // Aggiungi nuovi prodotti
        }
    }

    // Crea il box di un prodotto con le informazioni e le azioni
    private VBox creaProductBox(Stock stock) {
        VBox box = new VBox(10);
        box.getStyleClass().add("product-box");
        box.setAlignment(Pos.CENTER);

        // Contenitore per l'immagine del prodotto
        VBox imageContainer = new VBox();
        imageContainer.setPrefHeight(140);
        imageContainer.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        // Carica l'immagine del prodotto se esiste
        File productImageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (productImageFile.exists()) {
            imageView.setImage(new Image(productImageFile.toURI().toString()));
        } 
        imageContainer.getChildren().add(imageView);

        // Etichette per nome, prezzo, quantità e stato del prodotto
        Label nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");
        Label priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");
        Label quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");
        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("product-status");

        // Imposta lo stato del prodotto (disponibile o esaurito)
        if (stock.getQuantitaDisp() > 0) {
            statusLabel.setText("Stato: Disponibile");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        // Aggiungi i bottoni per azioni sul prodotto
        Button restockButton = creaBottoneConIcona("resources/icona spedizione.png", e -> {
            if (onRestock != null) onRestock.accept(stock);
        });

        Button replaceButton = creaBottoneConIcona("resources/icona-switch (1).png", e -> {
            if (onSostituzione != null) onSostituzione.accept(stock);
        });

        Button priceChangeButton = creaBottoneConIcona("resources/icona-modificaprezzo.png", e -> {
            if (onCambioPrezzo != null) onCambioPrezzo.accept(stock);
        });

        // Contenitore per i bottoni
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(restockButton, replaceButton, priceChangeButton);
        buttonContainer.getStyleClass().add("button-container");

        // Aggiungi tutti gli elementi al box del prodotto
        box.getChildren().addAll(imageContainer, nameLabel, priceLabel, quantityLabel, statusLabel, buttonContainer);
        return box;
    }

    // Crea un bottone con un'icona
    private Button creaBottoneConIcona(String imagePath, javafx.event.EventHandler<javafx.event.ActionEvent> eventHandler) {
        ImageView icon = new ImageView();
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        icon.setPreserveRatio(true);

        // Carica l'immagine dell'icona se esiste
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            icon.setImage(new Image(imageFile.toURI().toString()));
        } 
        Button button = new Button();
        button.setGraphic(icon);  // Imposta l'icona come contenuto del bottone
        button.getStyleClass().add("restock-button");  // Aggiungi uno stile al bottone
        button.setOnAction(eventHandler);  // Assegna l'azione al bottone
        return button;
    }
    
    // Mostra un messaggio di errore
    public void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/StileModificaPrezzo.css").toExternalForm());
        alert.showAndWait();
    }
    
    // Metodi setter per impostare i listener
    public void setOnApriCarrello(Runnable listener) { this.onApriCarrello = listener; }
    public void setOnApriStoricoSpedizioni(Runnable listener) { this.onApriStoricoSpedizioni = listener; }
    public void setOnSelezionaDistributore(Consumer<Integer> listener) { this.onSelezionaDistributore = listener; }
    public void setOnRestock(Consumer<Stock> listener) { this.onRestock = listener; }
    public void setOnSostituzione(Consumer<Stock> listener) { this.onSostituzione = listener; }
    public void setOnCambioPrezzo(Consumer<Stock> listener) { this.onCambioPrezzo = listener; }
}
