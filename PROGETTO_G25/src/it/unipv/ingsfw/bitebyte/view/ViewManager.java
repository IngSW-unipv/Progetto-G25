package it.unipv.ingsfw.bitebyte.view;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import it.unipv.ingsfw.bitebyte.controller.DistributoriAlternativiController;
import it.unipv.ingsfw.bitebyte.controller.ProdottiClienteController;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewManager {

    public static void showProdottiCliente(Distributore distributore) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/prodottiCliente.fxml"));
            AnchorPane root = loader.load();

            // Passiamo i dati al controller della nuova schermata
            ProdottiClienteController controller = loader.getController();
            controller.setIdInventario(distributore.getIdInventario());
            controller.setDistributoreCorrente(distributore);

            // Creiamo una nuova finestra
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("BiteByte - Prodotti Cliente");
            stage.setWidth(800);
            stage.setHeight(600);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void openDistributoriAlternativiView(String nomeProdotto, List<Distributore> distributori, Distributore distributoreCorrente) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/distributoriAlternativi.fxml"));
            AnchorPane root = loader.load();
            
            // Ottieni il controller e passa i dati necessari
            DistributoriAlternativiController controller = loader.getController();
            controller.setSearchQuery(nomeProdotto);
            controller.setDistributori(distributori, distributoreCorrente);
            
            // Creiamo una nuova finestra
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("BiteByte - Distributori Alternativi");
            stage.setWidth(800);
            stage.setHeight(600);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void openProdottiClienteView(Distributore distributore, String searchQuery) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/prodottiCliente.fxml"));
            AnchorPane root = loader.load();
            // Ottieni il controller e passa i dati
            it.unipv.ingsfw.bitebyte.controller.ProdottiClienteController controller = loader.getController();
            controller.setDistributoreCorrente(distributore);
            controller.setSearchQuery(searchQuery);
            controller.setModalitaVisualizzazione(true);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Prodotti del Distributore " + distributore.getIdDistr());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void apriGoogleMaps(double lat, double lon) {
        try {
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon;
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
