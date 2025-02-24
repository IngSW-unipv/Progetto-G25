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
        vboxStock.setPadding(new Insets(15));
        
        // Pulsante Indietro in alto a sinistra
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
        backButtonContainer.setPadding(new Insets(10));
        
        // Immagine Prodotto a sinistra
        imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        
        nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.setStyle("-fx-font-size: 20px;");
        
        priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.setStyle("-fx-font-size: 20px;");
        
        quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.setStyle("-fx-font-size: 20px;");
        
        VBox vboxInfoProdotto = new VBox(10, imageView, nameLabel, priceLabel, quantityLabel);
        vboxInfoProdotto.setAlignment(Pos.CENTER_LEFT);
        
        // Informazioni Utente a destra
        saldoLabel = new Label("Saldo disponibile: €" + String.format("%.2f", saldo));
        saldoLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label userLabel = new Label("Utente: " + (clienteLoggato != null ? clienteLoggato.getNome() + " " + clienteLoggato.getCognome() : "Nessun cliente loggato"));
        userLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        VBox vboxInfoUtente = new VBox(10, userLabel, saldoLabel);
        vboxInfoUtente.setAlignment(Pos.CENTER_RIGHT);
        
        HBox infoContainer = new HBox(20, vboxInfoProdotto, vboxInfoUtente);
        infoContainer.setAlignment(Pos.CENTER);
        
        selectButton = new Button("Conferma Acquisto");
        selectButton.getStyleClass().add("confirm-button");
        selectButton.setOnAction(e -> controller.acquistaProdotto(stock));
        
        vboxStock.getChildren().addAll(backButtonContainer, infoContainer, selectButton);
        vboxStock.setAlignment(Pos.TOP_CENTER);
        
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

    public void aggiornaImmagine(Stock stock) {
        File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }
    }
}
