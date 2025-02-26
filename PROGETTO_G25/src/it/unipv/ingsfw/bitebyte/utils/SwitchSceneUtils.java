package it.unipv.ingsfw.bitebyte.utils;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchSceneUtils {
	
    public static void switchScene(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SwitchSceneUtils.class.getResource(fxmlPath));
            Parent root = loader.load();         
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Potresti anche loggare l'errore in un file
        }
    }
}