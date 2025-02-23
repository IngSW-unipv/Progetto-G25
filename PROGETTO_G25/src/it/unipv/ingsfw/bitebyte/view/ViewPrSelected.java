package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.controller.AcquistoController;
import it.unipv.ingsfw.bitebyte.models.Cliente;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.io.File;

public class ViewPrSelected {

    private ImageView imageView;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private ImageView loadingGif;
    private Button selectButton;

    // Metodo per creare l'interfaccia grafica, includendo il cliente loggato
    public VBox creaInterfaccia(Stock stock, AcquistoController controller, Stage newStage, Stage previousStage, Cliente clienteLoggato, double saldo) {
    	System.out.println(saldo);
        VBox vboxStock = new VBox(5);
        vboxStock.getStyleClass().add("product-box");
        vboxStock.setAlignment(Pos.TOP_LEFT);
        vboxStock.setPadding(new Insets(0, 0, 0, 0));

        // Caricamento della GIF di loading
        Image gifImage = new Image(getClass().getResource("/gif/Loading_icon.gif").toString());
        loadingGif = new ImageView(gifImage);
        loadingGif.setFitWidth(50);
        loadingGif.setFitHeight(50);
        nascondiGif();

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

        // Aggiungi i dettagli dell'utente loggato
        Label userLabel = new Label("Utente: " + (clienteLoggato != null ? clienteLoggato.getNome() + " " + clienteLoggato.getCognome() : "Nessun cliente loggato"));
        userLabel.getStyleClass().add("user-info");
        userLabel.setStyle("-fx-font-size: 16px;"); 
        userLabel.setTranslateY(-180); // Posiziona la label un po' più in alto se necessario
        userLabel.setTranslateX(-190);
/*
        imageView.setTranslateY(-40); // Sposta l'immagine verso l'alto
        nameLabel.setTranslateY(-40);  // Sposta il nome prodotto verso l'alto
        priceLabel.setTranslateY(-40); // Sposta il prezzo verso l'alto
        quantityLabel.setTranslateY(-40); // Sposta la quantità verso l'alto
*/
        // Bottone Torna Indietro
        ImageView backImageView = new ImageView();
        backImageView.setFitWidth(50);
        backImageView.setFitHeight(50);
        backImageView.setPreserveRatio(true);
        backImageView.setImage(new Image(getClass().getResource("/immagini/back_arrow.png").toString()));

        backImageView.setOnMouseClicked(e -> {
            mostraFinestraCaricamento();

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
        VBox.setMargin(backButtonContainer, new Insets(0, 0, 85, 0));

        // Bottone Seleziona
        selectButton = new Button("Conferma Acquisto");
        selectButton.setOnAction(e -> controller.acquistaProdotto(stock));

        // Crea un StackPane per posizionare la GIF sopra l'immagine
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, loadingGif);

        // Aggiungi gli elementi alla VBox
        vboxStock.getChildren().addAll(backButtonContainer, stackPane, userLabel, nameLabel, priceLabel, quantityLabel, selectButton);

        return vboxStock;
    }

    // Funzione mostra GIF
    public void mostraFinestraCaricamento() {
        Stage loadingStage = new Stage();
        loadingStage.setTitle("Caricamento...");

        loadingStage.initStyle(StageStyle.UNDECORATED);
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
                loadingGif.setVisible(false);
            });
        }
    }

    // Funzione per aggiornare l'immagine del prodotto
    public void aggiornaImmagine(Stock stock) {
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }
    }
}
