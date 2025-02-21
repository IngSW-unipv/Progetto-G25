package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.ItemCarrello;
import it.unipv.ingsfw.bitebyte.controller.GestionePController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import java.io.File;

public class CarrelloView {
    private Carrello carrello;
    private VBox rootLayout;
    private GestionePController controller;

    public CarrelloView(Carrello carrello, GestionePController controller) {
        this.carrello = carrello;
        this.controller = controller;
        this.rootLayout = new VBox(10);
        this.rootLayout.setAlignment(Pos.TOP_CENTER);

        // Imposta lo sfondo rosso direttamente nel layout principale
        rootLayout.setStyle("-fx-background-color: #dc143c;");

        aggiornaVistaCarrello(); // Inizializza la vista del carrello
    }

    public void aggiornaVistaCarrello() {
        // Svuota completamente il layout
        rootLayout.getChildren().clear();

        // Aggiungi un controllo che verifica se il carrello è vuoto
        if (carrello.getItems().isEmpty()) {
            Label emptyLabel = new Label("Il carrello è vuoto!");
            emptyLabel.getStyleClass().add("empty-label");
            rootLayout.getChildren().add(emptyLabel);
        } else {
            // Ricarica la lista aggiornata dei prodotti nel carrello
            for (ItemCarrello item : carrello.getItems()) {
                HBox prodottoBox = creaRiquadroProdotto(item);
                rootLayout.getChildren().add(prodottoBox);
            }
        }

        // Aggiungi i bottoni (conferma ordine, torna ai prodotti)
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getStyleClass().add("button-box");

        Button confermaOrdineButton = new Button("Concludi Ordine");
        confermaOrdineButton.getStyleClass().add("conferma-ordine-button");
        confermaOrdineButton.setOnAction(e -> concludiOrdine());

        Button tornaAiProdottiButton = new Button("Torna ai Prodotti");
        tornaAiProdottiButton.getStyleClass().add("torna-ai-prodotti-button");
        tornaAiProdottiButton.setOnAction(e -> tornaAiProdotti());

        buttonBox.getChildren().addAll(tornaAiProdottiButton, confermaOrdineButton);
        rootLayout.getChildren().add(buttonBox);

        // Aggiungi ScrollPane per la lista di articoli nel carrello
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rootLayout);
        scrollPane.setFitToWidth(true);  // Assicura che il contenuto si adatti alla larghezza
        scrollPane.setStyle("-fx-background-color: transparent;");  // Rimuove lo sfondo trasparente
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Mostra sempre la barra di scorrimento verticale
    }

    private HBox creaRiquadroProdotto(ItemCarrello item) {
        HBox prodottoBox = new HBox(20);
        prodottoBox.setAlignment(Pos.CENTER_LEFT);
        prodottoBox.getStyleClass().add("prodotto-box");

        // Costruisci il percorso dell'immagine in base all'ID prodotto
        String nomeImmagine = item.getFornitura().getProdotto().getIdProdotto() + ".jpg";
        File file = new File("resources/immaginiDB/" + nomeImmagine);

        ImageView imageView = new ImageView();
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
        } else {
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
        }

        VBox detailsBox = new VBox(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Prodotto: " + item.getFornitura().getProdotto().getNome());
        nameLabel.getStyleClass().add("name-label");

        Label supplierLabel = new Label("Fornitore: " + item.getFornitura().getFornitore().getNomeF());
        supplierLabel.getStyleClass().add("supplier-label");

        Label quantityLabel = new Label("Quantità: " + item.getQuantita());
        quantityLabel.getStyleClass().add("quantity-label");

        Label totalLabel = new Label("Totale: €" + item.getPrezzoTotale());
        totalLabel.getStyleClass().add("total-label");

        detailsBox.getChildren().addAll(nameLabel, supplierLabel, quantityLabel, totalLabel);

        // Bottone per rimuovere il prodotto dal carrello
        Button removeButton = new Button("🗑️");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> rimuoviProdotto(item));

        prodottoBox.getChildren().addAll(imageView, detailsBox, removeButton);

        return prodottoBox;
    }

    private void rimuoviProdotto(ItemCarrello item) {
        // Rimuovi il prodotto dal carrello
        carrello.rimuoviItem(item);

        // Ricarica la vista aggiornata
        aggiornaVistaCarrello();
    }

    private void concludiOrdine() {
        // Implementa la logica per concludere l'ordine (ad esempio, inviare al sistema di spedizione)
        System.out.println("Ordine confermato!");
        carrello.svuota();  // Svuota il carrello dopo l'ordine
        aggiornaVistaCarrello(); // Ricarica la vista del carrello vuoto
    }

    private void tornaAiProdotti() {
        // Chiama il controller per mostrare la vista dei prodotti
        controller.caricaProdotti();
        Stage stage = (Stage) rootLayout.getScene().getWindow();
        stage.close(); // Chiudi la finestra del carrello
    }

    public void mostra() {
        Stage stage = new Stage();
        stage.setTitle("Carrello");

        // Carica il file CSS
        String cssFile = getClass().getResource("/css/StileCarrello.css").toExternalForm();
        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(cssFile); // Aggiungi il CSS alla scena

        stage.setScene(scene);
        stage.show();
    }
}
