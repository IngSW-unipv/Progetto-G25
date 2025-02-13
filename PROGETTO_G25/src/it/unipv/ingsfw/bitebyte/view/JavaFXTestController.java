package it.unipv.ingsfw.bitebyte.view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class JavaFXTestController {

    @FXML
    private Button myButton;

    // Questo metodo viene invocato quando il bottone viene cliccato
    @FXML
    private void handleButtonClick() {
        myButton.setText("Clicked!");
    }
}