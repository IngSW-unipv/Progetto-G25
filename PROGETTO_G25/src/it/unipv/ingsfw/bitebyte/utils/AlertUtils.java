package it.unipv.ingsfw.bitebyte.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

    /**
     * Mostra un alert di conferma dell'acquisto.
     */
    public static void mostraAlertConferma(String title, String header, String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
