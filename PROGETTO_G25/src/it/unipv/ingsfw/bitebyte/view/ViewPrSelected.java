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
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

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
        VBox vboxStock = new VBox(15);
        vboxStock.getStyleClass().add("product-box");
        vboxStock.setPadding(new Insets(15));

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

        saldoLabel = new Label("Saldo disponibile: €" + String.format("%.2f", saldo));
        saldoLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label userLabel = new Label("Utente: " + (clienteLoggato != null ? clienteLoggato.getNome() + " " + clienteLoggato.getCognome() : "Nessun cliente loggato"));
        userLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox vboxInfoUtente = new VBox(10, userLabel, saldoLabel);
        vboxInfoUtente.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(vboxInfoUtente, javafx.scene.layout.Priority.ALWAYS);

        HBox infoContainer = new HBox(25, vboxInfoProdotto, vboxInfoUtente);
        infoContainer.setAlignment(Pos.CENTER_LEFT);
        infoContainer.setPadding(new Insets(10));

        ImageView backImageView = new ImageView(new Image(getClass().getResource("/immagini/back_arrow2.png").toString()));
        backImageView.setFitWidth(50);
        backImageView.setFitHeight(50);
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

        Region spacerTop = new Region();
        spacerTop.setPrefHeight(50);

        HBox backButtonContainer = new HBox(backImageView);
        backButtonContainer.setAlignment(Pos.TOP_LEFT);

        VBox backButtonBox = new VBox(spacerTop, backButtonContainer);
        backButtonBox.setAlignment(Pos.TOP_LEFT);

        selectButton = new Button("Conferma Acquisto");
        selectButton.getStyleClass().add("confirm-button");
        selectButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 20px;");
        selectButton.setOnAction(e -> controller.acquistaProdotto(stock));

        VBox buttonContainer = new VBox(15, selectButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(15));

        vboxStock.setPrefHeight(400);
        vboxStock.setPrefWidth(600);

        vboxStock.getChildren().addAll(infoContainer);

        VBox root = new VBox(backButtonBox, vboxStock, buttonContainer);
        root.setAlignment(Pos.CENTER);

        return root;
    }

    public void mostraFinestraCaricamento() {
        Stage loadingStage = new Stage();
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.initModality(Modality.APPLICATION_MODAL);

        Image gifImage = new Image(getClass().getResource("/gif/Loading_icon.gif").toString());
        loadingGif = new ImageView(gifImage);
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
