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
    private Label saldoLabel;
    private ImageView loadingGif;
    private Button selectButton;

    public VBox creaInterfaccia(Stock stock, AcquistoController controller, Stage newStage, Stage previousStage, Cliente clienteLoggato, double saldo) {
        VBox vboxStock = new VBox(10);
        vboxStock.getStyleClass().add("product-box");
        vboxStock.setAlignment(Pos.CENTER_RIGHT);  // Allineamento a destra per l'intero contenitore
        vboxStock.setPadding(new Insets(15));

        // Immagine di caricamento
        Image gifImage = new Image(getClass().getResource("/gif/Loading_icon.gif").toString());
        loadingGif = new ImageView(gifImage);
        loadingGif.setFitWidth(50);
        loadingGif.setFitHeight(50);
        nascondiGif();

        imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        // Imposta la dimensione del testo
        nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.setStyle("-fx-font-size: 20px;");
        nameLabel.getStyleClass().add("product-name");
        
        priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.setStyle("-fx-font-size: 20px;");
        priceLabel.getStyleClass().add("product-price");
        
        quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.setStyle("-fx-font-size: 20px;");
        quantityLabel.getStyleClass().add("product-quantity");
        
        saldoLabel = new Label("Saldo disponibile: €" + String.format("%.2f", saldo));
        saldoLabel.setStyle("-fx-font-size: 20px;");
        saldoLabel.getStyleClass().add("user-balance");
        saldoLabel.setStyle("-fx-text-fill: white;");

        Label userLabel = new Label("Utente: " + (clienteLoggato != null ? clienteLoggato.getNome() + " " + clienteLoggato.getCognome() : "Nessun cliente loggato"));
        userLabel.setStyle("-fx-font-size: 20px;");
        userLabel.getStyleClass().add("user-info");
        userLabel.setStyle("-fx-text-fill: white;");

        HBox userInfoBox = new HBox(10, userLabel, saldoLabel);
        userInfoBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView backImageView = new ImageView(new Image(getClass().getResource("/immagini/back_arrow.png").toString()));
        backImageView.setFitWidth(40);
        backImageView.setFitHeight(40);
        backImageView.setPreserveRatio(true);
        backImageView.setOnMouseClicked(e -> {
            mostraFinestraCaricamento();
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Platform.runLater(() -> controller.tornaIndietro(newStage));
            }).start();
        });

        HBox backButtonContainer = new HBox(backImageView);
        backButtonContainer.setAlignment(Pos.TOP_LEFT);

        selectButton = new Button("Conferma Acquisto");
        selectButton.getStyleClass().add("confirm-button");
        selectButton.setOnAction(e -> controller.acquistaProdotto(stock));

        StackPane stackPane = new StackPane(imageView, loadingGif);

        VBox vboxInfo = new VBox(10);
        vboxInfo.getChildren().addAll(nameLabel, priceLabel, quantityLabel, saldoLabel);
        vboxInfo.setAlignment(Pos.CENTER_RIGHT);  // Allineamento a destra per le etichette

        vboxStock.getChildren().addAll(backButtonContainer, stackPane, userInfoBox, vboxInfo, selectButton);

        return vboxStock;
    }
    
    public void mostraFinestraCaricamento() {
        Stage loadingStage = new Stage();
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        
        Image gifImage = new Image(getClass().getResource("/gif/Loading_icon.gif").toString());
        ImageView loadingGif = new ImageView(gifImage);
        loadingGif.setFitWidth(100);
        loadingGif.setFitHeight(100);

        VBox layout = new VBox(10, loadingGif);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 200, 200);
        loadingStage.setScene(scene);
        loadingStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(loadingStage::close);
        }).start();
    }

    public void nascondiGif() {
        if (loadingGif != null) {
            Platform.runLater(() -> loadingGif.setVisible(false));
        }
    }

    public void aggiornaImmagine(Stock stock) {
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }
    }
}
