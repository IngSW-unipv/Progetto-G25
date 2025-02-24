package it.unipv.ingsfw.bitebyte.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	    	System.out.println("prova");
	        Parent root = FXMLLoader.load(getClass().getResource("/login-view.fxml"));
	        primaryStage.setTitle("Login	");
	        primaryStage.setScene(new Scene(root, 600, 400));
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}

// /it/unipv/ingsfw/bitebyte/view/fxml


