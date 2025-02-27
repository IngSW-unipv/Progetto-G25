package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.ItemCarrello;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.function.Consumer;

public class CarrelloView {
    private Carrello carrello;
    private VBox rootLayout;
    private Runnable onTornaAiProdotti;
    private Runnable onConcludiOrdine;
    private Consumer<ItemCarrello> onRimuoviProdotto;
    private Stage stage; // Variabile per tracciare l'istanza di Stage (finestra del carrello)

    // Costruttore che inizializza la vista del carrello
    public CarrelloView(Carrello carrello) {
        this.carrello = carrello;
        this.rootLayout = new VBox(10);
        this.rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.setStyle("-fx-background-color: #dc143c;"); // Imposta il colore di sfondo
        aggiornaVistaCarrello(); // Popola la vista con i dati iniziali
    }

    // Metodo per aggiornare la vista del carrello
    public void aggiornaVistaCarrello() {
        rootLayout.getChildren().clear(); // Pulisce il layout prima di aggiornare
        
        if (carrello.getItems().isEmpty()) {
            // Mostra un messaggio se il carrello è vuoto
            Label emptyLabel = new Label("Il carrello è vuoto!");
            emptyLabel.getStyleClass().add("empty-label");
            rootLayout.getChildren().add(emptyLabel);
        } else {
            // Aggiunge ogni prodotto nel carrello alla vista
            for (ItemCarrello item : carrello.getItems()) {
                HBox prodottoBox = creaRiquadroProdotto(item);
                rootLayout.getChildren().add(prodottoBox);
            }
        }

        // Creazione della barra dei pulsanti
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getStyleClass().add("button-box");

        // Pulsante per confermare l'ordine
        Button confermaOrdineButton = new Button("Concludi Ordine");
        confermaOrdineButton.getStyleClass().add("conferma-ordine-button");
        confermaOrdineButton.setOnAction(e -> {
            if (onConcludiOrdine != null) onConcludiOrdine.run();
        });

        // Pulsante per tornare ai prodotti
        Button tornaAiProdottiButton = new Button("Torna ai Prodotti");
        tornaAiProdottiButton.getStyleClass().add("torna-ai-prodotti-button");
        tornaAiProdottiButton.setOnAction(e -> {
            if (onTornaAiProdotti != null) onTornaAiProdotti.run();
            Stage stage = (Stage) rootLayout.getScene().getWindow();
            stage.close(); // Chiude la finestra del carrello
        });

        buttonBox.getChildren().addAll(tornaAiProdottiButton, confermaOrdineButton);
        rootLayout.getChildren().add(buttonBox);
    }

    // Metodo per creare un riquadro con i dettagli del prodotto nel carrello
    private HBox creaRiquadroProdotto(ItemCarrello item) {
        HBox prodottoBox = new HBox(20);
        prodottoBox.setAlignment(Pos.CENTER_LEFT);
        prodottoBox.getStyleClass().add("prodotto-box");

        // Recupera l'immagine del prodotto
        String nomeImmagine = item.getFornitura().getProdotto().getIdProdotto() + ".jpg";
        File file = new File("resources/immaginiDB/" + nomeImmagine);
        ImageView imageView = new ImageView();
        if (file.exists()) {
            imageView.setImage(new Image(file.toURI().toString()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
        } else {
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
        }

        // Box contenente i dettagli del prodotto
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

        // Pulsante per rimuovere il prodotto dal carrello
        Button removeButton = new Button("Rimuovi");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> {
            if (onRimuoviProdotto != null) onRimuoviProdotto.accept(item);
            aggiornaVistaCarrello(); // Aggiorna la vista dopo la rimozione
        });

        prodottoBox.getChildren().addAll(imageView, detailsBox, removeButton);
        return prodottoBox;
    }

    // Metodo per mostrare la finestra del carrello
    public void mostra() {
        Stage stage = new Stage();
        stage.setTitle("Carrello");
        
        // Carica il foglio di stile
        String cssFile = getClass().getResource("/css/StileCarrello.css").toExternalForm();
        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        
        // Mostra la finestra
        stage.show();
    }

    // Metodo getter per ottenere il layout principale
    public VBox getRootLayout() {
        return rootLayout;
    }

    // Setter per i listener degli eventi
    public void setOnTornaAiProdotti(Runnable listener) { this.onTornaAiProdotti = listener; }
    public void setOnConcludiOrdine(Runnable listener) { this.onConcludiOrdine = listener; }
    public void setOnRimuoviProdotto(Consumer<ItemCarrello> listener) { this.onRimuoviProdotto = listener; }
}
