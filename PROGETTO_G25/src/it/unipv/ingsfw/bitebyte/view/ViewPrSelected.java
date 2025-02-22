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
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.InputStream;

public class ViewPrSelected {

    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private Label statusLabel;
    private Button selectButton;
    private Label dynamicLabel;  // La label dinamica
    private ImageView loadingGif; // Aggiunta per la GIF di loading
    private Stage previousStage;  // Per gestire la finestra precedente

    // Metodo per creare l'interfaccia grafica
    public void creaInterfaccia(Stock stock, boolean modalitaVisualizzazione, Runnable onSelectAction, Stage stage, Stage previousStage) {
        // Salvo il riferimento al precedente stage
        this.previousStage = previousStage;

        // Creazione della VBox per la disposizione verticale
        VBox vboxStock = new VBox(5);  // Spaziatura di 5 tra gli elementi
        vboxStock.getStyleClass().add("product-box");

        // Aggiungi una StackPane per gestire la GIF di loading
        StackPane loadingPane = new StackPane();

        // Caricamento della GIF di loading
        InputStream gifInputStream = getClass().getResourceAsStream("/resources/gif/Loading_icon.gif");
        if (gifInputStream != null) {
            loadingGif = new ImageView(new Image(gifInputStream));
            loadingGif.setFitWidth(50);
            loadingGif.setFitHeight(50);
        } else {
            System.out.println("Errore nel caricamento della GIF: il file non è stato trovato.");
        }

        // Aggiungi la GIF se è stata caricata correttamente
        if (loadingGif != null) {
        	loadingPane.getChildren().add(loadingGif);
        }

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

        // Aggiungi il bottone Torna Indietro
        Button backButton = new Button("Torna Indietro");
        backButton.setOnAction(e -> {
            // Chiudi la finestra attuale
            stage.close();
            // Mostra la finestra precedente
            if (previousStage != null) {
                previousStage.show();
            }
        });

        // Imposta l'immagine del prodotto
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        // Aggiungi gli elementi alla VBox
        vboxStock.getChildren().addAll(backButton, loadingGif, imageView, nameLabel, priceLabel, quantityLabel);


        // Crea la scena
        Scene scene = new Scene(vboxStock, 300, 350); // Dimensione della finestra
        scene.getStylesheets().add(getClass().getResource("/CSS/styles2.css").toExternalForm());
        stage.setTitle("Dettagli Prodotto");
        stage.setScene(scene);
        stage.show();

        // Mostra la GIF di loading
        loadingGif.setVisible(true);

        // Simula il caricamento (puoi rimuoverlo quando il contenuto è pronto)
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simula un caricamento di 2 secondi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Dopo il "caricamento", nascondi la GIF
            loadingGif.setVisible(false);

            // Dopo il caricamento, puoi mostrare l'immagine del prodotto, ecc.
        }).start();
    }
}
