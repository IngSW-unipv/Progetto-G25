
package it.unipv.ingsfw.bitebyte.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestController extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carica il file FXML (Assicurati che il percorso sia corretto)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/collegamentoDistributore.fxml"));

        // Carica la scena con il file FXML
        AnchorPane root = loader.load();

        // Imposta la scena e la finestra
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Test Collegamento Distributore");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lancia l'applicazione
    }
}


