package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.controller.AcquistoController;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.InputStream;

public class ViewPrSelected {

    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private ImageView loadingGif; // Aggiunta per la GIF di loading
    private Button selectButton;

    // Metodo per creare l'interfaccia grafica
    public VBox creaInterfaccia(Stock stock, AcquistoController controller, Stage newStage, Stage previousStage) {
        VBox vboxStock = new VBox(5);

        vboxStock.getStyleClass().add("product-box");
        
        // Caricamento della GIF di loading
        InputStream gifInputStream = getClass().getResourceAsStream("/gif/Loading_icon.gif");
        if (gifInputStream != null) {
            loadingGif = new ImageView(new Image(gifInputStream));
            loadingGif.setFitWidth(50);
            loadingGif.setFitHeight(50);
            loadingGif.setStyle("-fx-background-color: red;"); // Aggiungi un colore di sfondo per testare visibilità
            nascondiGif(); // La GIF è inizialmente nascosta
        } else {
            System.out.println("La GIF non è stata trovata!");
        }

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
        
        Button backButton = new Button("Torna indietro");
        backButton.setOnAction(e -> controller.tornaIndietro(newStage));  // Qui associ il metodo del controller

        // Bottone Seleziona
        selectButton = new Button("Seleziona");

        // Crea un StackPane per posizionare la GIF sopra l'immagine
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, loadingGif); // Aggiungi prima l'immagine, poi la GIF (perché deve essere sopra l'immagine)

        // Aggiungi gli elementi alla VBox
        vboxStock.getChildren().addAll(backButton, selectButton, stackPane, nameLabel, priceLabel, quantityLabel);

        return vboxStock;
    }

    // Funzione per mostrare la GIF
    public void mostraGif() {
        if (loadingGif != null) {
            System.out.println("Mostrando GIF...");

            Platform.runLater(() -> {
                loadingGif.setVisible(true);
                loadingGif.getScene().getRoot().requestLayout(); // Forza il layout
            });
        }
    }

    // Funzione per nascondere la GIF
    public void nascondiGif() {
        if (loadingGif != null) {
            Platform.runLater(() -> {
                loadingGif.setVisible(false); // Nascondi la GIF
            });
        }
    }

    // Funzione per aggiornare l'immagine del prodotto
    public void aggiornaImmagine(Stock stock) {
        // Imposta l'immagine del prodotto
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }
    }
}
