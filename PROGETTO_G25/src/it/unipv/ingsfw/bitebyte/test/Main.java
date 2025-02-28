package it.unipv.ingsfw.bitebyte.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	    	System.out.println("Benvenuto in BiteByte");
	        Parent root = FXMLLoader.load(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/login-view.fxml"));
	        primaryStage.setTitle("Login	");
	        primaryStage.setScene(new Scene(root, 600, 400));
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}


