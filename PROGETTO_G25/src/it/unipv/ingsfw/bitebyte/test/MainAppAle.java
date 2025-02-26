package it.unipv.ingsfw.bitebyte.test;


import it.unipv.ingsfw.bitebyte.controller.GestionePController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAppAle extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creiamo il controller
        GestionePController controller = new GestionePController();

        // Simuliamo un inventario (puoi cambiare l'ID per testare)
        controller.setIdInventario(2);

        // Creiamo la scena con la vista del controller
        Scene scene = new Scene(controller.getView().getView(), 800, 600);

        // Impostiamo lo stage
        primaryStage.setTitle("Test Prodotti");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}