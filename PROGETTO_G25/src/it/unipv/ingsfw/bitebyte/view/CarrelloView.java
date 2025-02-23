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

        rootLayout.setStyle("-fx-background-color: #dc143c;");
        aggiornaVistaCarrello(); // Inizializzo la vista del carrello
    }

    public void aggiornaVistaCarrello() {
        // Svuota completamente il layout
        rootLayout.getChildren().clear();
        // Verifico se il carrello è vuoto
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

        // Aggiunta dei bottoni
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getStyleClass().add("button-box");

        Button confermaOrdineButton = new Button("Concludi Ordine");
        confermaOrdineButton.getStyleClass().add("conferma-ordine-button");
        confermaOrdineButton.setOnAction(e -> controller.concludiOrdine());

        Button tornaAiProdottiButton = new Button("Torna ai Prodotti");
        tornaAiProdottiButton.getStyleClass().add("torna-ai-prodotti-button");
        tornaAiProdottiButton.setOnAction(e -> tornaAiProdotti());

        buttonBox.getChildren().addAll(tornaAiProdottiButton, confermaOrdineButton);
        rootLayout.getChildren().add(buttonBox);
    }

    private HBox creaRiquadroProdotto(ItemCarrello item) {
        HBox prodottoBox = new HBox(20);
        prodottoBox.setAlignment(Pos.CENTER_LEFT);
        prodottoBox.getStyleClass().add("prodotto-box");

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

        Button removeButton = new Button("🗑️");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> rimuoviProdotto(item));

        prodottoBox.getChildren().addAll(imageView, detailsBox, removeButton);
        return prodottoBox;
    }

    private void rimuoviProdotto(ItemCarrello item) {
        carrello.rimuoviItem(item);
        aggiornaVistaCarrello();
    }

    private void tornaAiProdotti() {
        controller.caricaProdotti();
        Stage stage = (Stage) rootLayout.getScene().getWindow();
        stage.close();
    }

    public void mostra() {
        Stage stage = new Stage();
        stage.setTitle("Carrello");
        
        String cssFile = getClass().getResource("/css/StileCarrello.css").toExternalForm();
        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(cssFile); 
        stage.setScene(scene);
        stage.show();
    }
}
