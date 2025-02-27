package it.unipv.ingsfw.bitebyte.utils;

import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SwitchSceneUtils {

	//Davide
	public static void switchScene(ActionEvent event, String fxml, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(
			SwitchSceneUtils.class.getResource("/it/unipv/ingsfw/bitebyte/view/fxml/" + fxml));
			Parent root = loader.load();
			
			 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	         stage.setTitle(title);
	         stage.setScene(new Scene(root));
	         stage.show();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	//Alice
	public void Scene(Button bottone, String fxml, String title) {
		try {
			Stage stage = (Stage) bottone.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/" + fxml));
			Parent root = loader.load();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Questo mostrerà eventuali errori
		}
	}
	
	
	
}