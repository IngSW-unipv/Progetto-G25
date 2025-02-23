package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Prodotto;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SostituzioneView {
    private Stage stage;
    private VBox rootLayout;
    private Consumer<Prodotto> onSelect;

    public SostituzioneView(ArrayList<Prodotto> prodotti, Consumer<Prodotto> onSelect) {
        this.onSelect = onSelect;
        stage = new Stage();
        rootLayout = new VBox(20);
        rootLayout.getStyleClass().add("root-layout"); 

        Label titleLabel = new Label("Seleziona un prodotto sostitutivo");
        titleLabel.getStyleClass().add("label-title"); 

        // FlowPane che contiene i prodotti
        FlowPane prodottiContainer = new FlowPane();
        prodottiContainer.getStyleClass().add("prodotti-container"); 
        prodottiContainer.setAlignment(Pos.CENTER);
        // Aggiunta dei prodotti al container
        for (Prodotto prodotto : prodotti) {
            prodottiContainer.getChildren().add(creaProductBox(prodotto));
        }

        rootLayout.getChildren().addAll(titleLabel, prodottiContainer);

        ScrollPane scrollPane = new ScrollPane(rootLayout);
        scrollPane.setFitToWidth(true);  
        scrollPane.setFitToHeight(true); 
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        Scene scene = new Scene(scrollPane, 650, 400);
        scene.getStylesheets().add(getClass().getResource("/css/StileSostituzione.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sostituzione Prodotto");
        stage.show();
    }

    private VBox creaProductBox(Prodotto prodotto) {
        VBox box = new VBox(10);
        box.getStyleClass().add("product-box"); 

        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("product-image"); 
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        File imageFile = new File("resources/immaginiDB/" + prodotto.getIdProdotto() + ".jpg");
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        }

        Label nameLabel = new Label(prodotto.getNome());
        nameLabel.getStyleClass().add("label-name");
        Label priceLabel = new Label(String.format("€ %.2f", prodotto.getPrezzo()));
        priceLabel.getStyleClass().add("label-price");
        
        Button selectButton = new Button("Seleziona");
        selectButton.getStyleClass().add("select-button");
        selectButton.setOnAction(e -> {
            onSelect.accept(prodotto);
            stage.close(); 
        });

        box.getChildren().addAll(imageView, nameLabel, priceLabel, selectButton);
        return box;
    }
}
