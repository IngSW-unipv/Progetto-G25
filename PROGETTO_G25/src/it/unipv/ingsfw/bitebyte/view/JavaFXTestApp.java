package it.unipv.ingsfw.bitebyte.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXTestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
        Scene scene = new Scene(loader.load());

        // Imposta il titolo della finestra
        primaryStage.setTitle("JavaFX FXML Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}