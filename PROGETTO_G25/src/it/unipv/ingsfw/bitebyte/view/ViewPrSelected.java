package it.unipv.ingsfw.bitebyte.view;

import javafx.scene.layout.VBox;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ViewPrSelected {

    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private Label statusLabel;
    private Button selectButton;
    private Label dynamicLabel;  // La label dinamica

    // Metodo per creare l'interfaccia grafica
    public void creaInterfaccia(Stock stock, boolean modalitaVisualizzazione, Runnable onSelectAction, Stage stage) {
        // Creazione della VBox per la disposizione verticale
        VBox vboxStock = new VBox(5);  // Spaziatura di 5 tra gli elementi
        vboxStock.getStyleClass().add("product-box");

        // Inizializza i nodi
        imageView = new ImageView();
        imageView.setFitWidth(120);  // Larghezza fissa
        imageView.setFitHeight(120); // Altezza fissa
        imageView.setPreserveRatio(true);  // Mantiene la proporzione

        nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");

        priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");

        quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");

        statusLabel = new Label();
        statusLabel.getStyleClass().add("product-status");

        // Imposta l'immagine del prodotto
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Aggiungi tutti gli elementi alla VBox
        vboxStock.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel);
        
        // Crea la scena
        // Crea una nuova scena senza controllare se esiste una scena precedente
        Scene scene = new Scene(vboxStock, 300, 350); // Dimensione della finestra
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());
        stage.setTitle("Dettagli Prodotto");
        stage.setScene(scene);
        stage.show();

        // Forza il ricalcolo del layout
        scene.getRoot().applyCss();
        scene.getRoot().layout();
        
       

    }

 
}
