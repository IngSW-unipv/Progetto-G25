package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.controller.ProdottiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestGestioneP extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gestione-prodotti.fxml"));  // Percorso assoluto

            AnchorPane root = loader.load();

            // Prendiamo il controller e impostiamo i dati
            ProdottiController controller = loader.getController();
            controller.setIdInventario(1);  // Esempio di passaggio dati
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Distributore Automatico");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
