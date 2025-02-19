package it.unipv.ingsfw.bitebyte.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import it.unipv.ingsfw.bitebyte.controller.ProdottiClienteController;

public class TestControllerCliente extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carica il layout FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/prodottiCliente.fxml"));
        AnchorPane root = loader.load();

        // Ottieni il controller dal loader
        ProdottiClienteController controller = loader.getController();

        // Imposta un ID di inventario per il test
        controller.setIdInventario(1); // Usa un id inventario di esempio

        // Crea la scena e imposta la finestra principale
        Scene scene = new Scene(root);

        // Aggiungi il CSS (opzionale)
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());

        // Imposta la finestra principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("BiteByte - Prodotti Cliente");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        // Mostra la finestra
        primaryStage.show();
    }
}

