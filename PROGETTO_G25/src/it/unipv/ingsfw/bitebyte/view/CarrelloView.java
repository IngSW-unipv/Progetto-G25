package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Carrello;
import it.unipv.ingsfw.bitebyte.models.ItemOrdine;
import it.unipv.ingsfw.bitebyte.controller.GestionePController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;  // Importa ScrollPane
import javafx.scene.layout.Region;  // Per gestire la crescita degli oggetti
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

        // Aggiungi i prodotti nel carrello
        for (ItemOrdine item : carrello.getItems()) {
            // Crea il riquadro per il prodotto
            HBox prodottoBox = new HBox(20);
            prodottoBox.setAlignment(Pos.CENTER_LEFT);
            prodottoBox.getStyleClass().add("prodotto-box");
            
            // Costruisci il percorso dell'immagine in base all'ID prodotto
            String nomeImmagine = item.getFornitura().getProdotto().getIdProdotto() + ".jpg";
            File file = new File("resources/immaginiDB/" + nomeImmagine);
            
            ImageView imageView = new ImageView();
            if (file.exists()) {
                // Crea un oggetto Image usando il percorso del file
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
            } else {
                // Se l'immagine non esiste, lascia la ImageView vuota
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
            }
            
            // Aggiungi i dettagli del prodotto a destra dell'immagine
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
            
            // Crea il bottone per rimuovere il prodotto dal carrello
            Button removeButton = new Button("🗑️");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(e -> rimuoviProdotto(item));
            
            // Aggiungi immagine e dettagli al riquadro del prodotto
            prodottoBox.getChildren().addAll(imageView, detailsBox, removeButton);

            // Aggiungi il riquadro del prodotto alla rootLayout
            rootLayout.getChildren().add(prodottoBox);
            rootLayout.getStyleClass().add("root-layout");
        }

        // Crea una HBox per i bottoni
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getStyleClass().add("button-box");
        
        // Aggiungi un pulsante per confermare l'ordine
        Button confermaOrdineButton = new Button("Concludi Ordine");
        confermaOrdineButton.getStyleClass().add("conferma-ordine-button");
        confermaOrdineButton.setOnAction(e -> concludiOrdine());

        // Aggiungi un pulsante per tornare alla vista dei prodotti
        Button tornaAiProdottiButton = new Button("Torna ai Prodotti");
        tornaAiProdottiButton.getStyleClass().add("torna-ai-prodotti-button");
        tornaAiProdottiButton.setOnAction(e -> tornaAiProdotti());

        // Aggiungi i bottoni all'HBox
        buttonBox.getChildren().addAll(tornaAiProdottiButton, confermaOrdineButton);
        
        // Aggiungi l'HBox alla rootLayout in basso
        rootLayout.getChildren().add(buttonBox);
        
        // Aggiungi rootLayout a un ScrollPane per renderlo scorrevole
        ScrollPane scrollPane = new ScrollPane(rootLayout);
        scrollPane.setFitToWidth(true);  // Adatta la larghezza al contenuto
        scrollPane.setFitToHeight(true); // Adatta l'altezza se necessario
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Mostra la barra verticale solo se necessario
        
       
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

    private void concludiOrdine() {
        // Implementa la logica per concludere l'ordine (ad esempio, inviare al sistema di spedizione)
        System.out.println("Ordine confermato!");
        carrello.svuota();  // Svuota il carrello dopo l'ordine
    }

    private void tornaAiProdotti() {
        // Chiama il controller per mostrare la vista dei prodotti
        controller.caricaProdotti();
        Stage stage = (Stage) rootLayout.getScene().getWindow();
        stage.close(); // Chiudi la finestra del carrello
    }

    private void rimuoviProdotto(ItemOrdine item) {
        // Rimuovi il prodotto dal carrello
        carrello.rimuoviItem(item);
        // Ricarica la vista aggiornata
        rootLayout.getChildren().clear();
        // Ricarica il carrello con i prodotti rimanenti
        for (ItemOrdine remainingItem : carrello.getItems()) {
            // Aggiungi nuovamente i prodotti dopo la rimozione
            HBox prodottoBox = new HBox(20);
            prodottoBox.setAlignment(Pos.CENTER_LEFT);
            prodottoBox.getStyleClass().add("prodotto-box");
            
            String nomeImmagine = remainingItem.getFornitura().getProdotto().getIdProdotto() + ".jpg";
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
            
            Label nameLabel = new Label("Prodotto: " + remainingItem.getFornitura().getProdotto().getNome());
            nameLabel.getStyleClass().add("name-label");
            
            Label supplierLabel = new Label("Fornitore: " + remainingItem.getFornitura().getFornitore().getNomeF());
            supplierLabel.getStyleClass().add("supplier-label");
            
            Label quantityLabel = new Label("Quantità: " + remainingItem.getQuantita());
            quantityLabel.getStyleClass().add("quantity-label");
            
            Label totalLabel = new Label("Totale: €" + remainingItem.getPrezzoTotale());
            totalLabel.getStyleClass().add("total-label");
            
            detailsBox.getChildren().addAll(nameLabel, supplierLabel, quantityLabel, totalLabel);
            
            Button removeButton = new Button("🗑️");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(e -> rimuoviProdotto(remainingItem));
            
            prodottoBox.getChildren().addAll(imageView, detailsBox, removeButton);

            rootLayout.getChildren().add(prodottoBox);
        }

        // Aggiungi nuovamente i bottoni
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
    }
}
