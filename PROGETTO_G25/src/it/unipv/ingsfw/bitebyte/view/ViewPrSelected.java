package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.controller.AcquistoController;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;




public class ViewPrSelected {

    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private ImageView loadingGif; // Aggiunta per la GIF di loading
    private Button selectButton;

    // Metodo per creare l'interfaccia grafica
    public VBox creaInterfaccia(Stock stock, AcquistoController controller, Stage newStage, Stage previousStage) {


        VBox vboxStock = new VBox(5); // Spazio minimo tra elementi
        vboxStock.getStyleClass().add("product-box");
        vboxStock.setAlignment(Pos.TOP_LEFT); // Allinea tutto in alto a sinistra
        vboxStock.setPadding(new Insets(0, 0, 0, 0)); // Rimuove margini extra
        vboxStock.getStyleClass().add("product-box");
        
        // Caricamento della GIF di loading
        Image gifImage = new Image(getClass().getResource("/gif/Loading_icon.gif").toString());
        loadingGif = new ImageView(gifImage);
        loadingGif.setFitWidth(50);
        loadingGif.setFitHeight(50);
        loadingGif.setStyle("-fx-background-color: red;"); // Aggiungi un colore di sfondo per testare visibilità
        nascondiGif(); // La GIF è inizialmente nascosta
        
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
      
       
        imageView.setTranslateY(-40); // Sposta l'immagine verso l'alto
        nameLabel.setTranslateY(-40);  // Sposta il nome prodotto verso l'alto
        priceLabel.setTranslateY(-40); // Sposta il prezzo verso l'alto
        quantityLabel.setTranslateY(-40); // Sposta la quantità verso l'alto
        
        // Bottone Torna Indietro
        ImageView backImageView = new ImageView();
        backImageView.setFitWidth(50);  // Puoi regolare la dimensione come preferisci
        backImageView.setFitHeight(50);
        backImageView.setPreserveRatio(true);

        backImageView.setImage(new Image(getClass().getResource("/immagini/back_arrow.png").toString()));

        // Listener al click per tornare indietro
        backImageView.setOnMouseClicked(e -> {
            mostraFinestraCaricamento();

            // Dopo 2 secondi chiudi la finestra e torna indietro
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Attendi 2 secondi
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                Platform.runLater(() -> {
                    controller.tornaIndietro(newStage);
                });
            }).start();
        });

        // Contenitore per l'ImageView
        HBox backButtonContainer = new HBox(backImageView);
        backButtonContainer.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(backButtonContainer, new Insets(0, 0, 90, 0));  // Posizione in alto a sinistra con margine
        
        
        // Bottone Seleziona
        selectButton = new Button("Conferma Acquisto");	
        selectButton.setOnAction(e -> controller.acquistaProdotto(stock));
        // Crea un StackPane per posizionare la GIF sopra l'immagine
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, loadingGif); // Aggiungi prima l'immagine, poi la GIF (perché deve essere sopra l'immagine)

        // Aggiungi gli elementi alla VBox
        vboxStock.getChildren().addAll(backButtonContainer, stackPane, nameLabel, priceLabel, quantityLabel, selectButton);


        return vboxStock;
    }

    // Funzione mostra GIF
    public void mostraFinestraCaricamento() {
        Stage loadingStage = new Stage();
        loadingStage.setTitle("Caricamento...");

        // Rimuove la "X" e la barra del titolo
        loadingStage.initStyle(StageStyle.UNDECORATED);

        // Blocca l'interazione con la finestra principale finché questa non si chiude
        loadingStage.initModality(Modality.APPLICATION_MODAL);

        // Caricamento della GIF
        Image gifImage = new Image(getClass().getResource("/gif/Loading_icon.gif").toString());
        ImageView loadingGif = new ImageView(gifImage);
        loadingGif.setFitWidth(100);
        loadingGif.setFitHeight(100);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(loadingGif);

        Scene scene = new Scene(layout, 200, 200);
        loadingStage.setScene(scene);

        // Mostra la finestra di caricamento
        loadingStage.show();

        // Dopo 2 secondi chiude la finestra
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(loadingStage::close);
        }).start();
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