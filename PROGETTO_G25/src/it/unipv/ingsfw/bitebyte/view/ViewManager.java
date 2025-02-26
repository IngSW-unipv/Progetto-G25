package it.unipv.ingsfw.bitebyte.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * ViewManager centralizza la logica di creazione delle scene e la navigazione.
 * Implementato come singleton per garantire una sola istanza globale.
 */
public class ViewManager {

    private static ViewManager instance;

    private ViewManager() {
        // Costruttore privato per il singleton
    }

    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    /**
     * Carica un file FXML, crea una nuova Stage con la Scene e restituisce il controller associato.
     *
     * @param fxmlPath percorso dell'FXML (es. "/prodottiCliente.fxml")
     * @param width    larghezza della scena
     * @param height   altezza della scena
     * @param title    titolo della finestra
     * @param <T>      tipo del controller associato
     * @return il controller caricato dall'FXML
     */
    public <T> T showStageWithController(String fxmlPath, double width, double height, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
         
            Parent root = loader.load();
            T controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, height));
            stage.setTitle(title);
            stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Imposta una nuova scena in uno Stage esistente.
     *
     * @param stage    Stage esistente
     * @param fxmlPath percorso dell'FXML
     * @param width    larghezza della scena
     * @param height   altezza della scena
     * @param title    titolo della finestra
     */
    public void setScene(Stage stage, String fxmlPath, double width, double height, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            stage.setScene(new Scene(root, width, height));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
