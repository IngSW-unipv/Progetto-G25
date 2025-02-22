package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;
import it.unipv.ingsfw.bitebyte.view.DistributoreBin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class DistributoriAlternativiController {

    @FXML
    private TableView<DistributoreBin> distributoriTable;
    @FXML
    private TableColumn<DistributoreBin, String> nomeColumn;
    @FXML
    private TableColumn<DistributoreBin, String> indirizzoColumn;
    @FXML
    private TableColumn<DistributoreBin, Double> distanzaColumn;
    @FXML
    private TableColumn<DistributoreBin, Button> azioneColumn;
    @FXML
    private TableColumn<DistributoreBin, Button> visualizzaProdottoColumn; // Nuova colonna
    
    
    // Il distributore corrente serve per calcolare la distanza
    private Distributore distributoreCorrente;
    private String searchQuery = "";
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setDistributori(List<Distributore> distributori, Distributore distributoreCorrente) {
        this.distributoreCorrente = distributoreCorrente;
        ObservableList<DistributoreBin> data = FXCollections.observableArrayList();
        for (Distributore d : distributori) {
            double distanza = CalcolaDistanza.calcolaDistanza(distributoreCorrente, d);
            
            // Crea il pulsante "Vai" per aprire Google Maps
            Button btnVai = new Button("Vai");
            btnVai.setOnAction((ActionEvent event) -> {
                apriGoogleMaps(d.getLat(), d.getLon());
            });
            
         // Crea il pulsante "Visualizza prodotto" per aprire l'inventario del distributore
            Button btnVisualizzaProdotto = new Button("Visualizza prodotto");
            btnVisualizzaProdotto.setOnAction((ActionEvent event) -> {
            	 apriInventarioDistributore(d, this.searchQuery);
            });
            
            data.add(new DistributoreBin(
                    String.valueOf(d.getIdDistr()),          // Nome/ID del distributore
                    d.getVia() + " " + d.getNCivico(),           // Indirizzo
                    distanza,                                    // Distanza
                    btnVai,                                      // Pulsante "Vai"
                    btnVisualizzaProdotto                        // Pulsante "Visualizza prodotto"
                ));
            }
        distributoriTable.setItems(data);
    }

    
    private void apriGoogleMaps(double lat, double lon) {
        try {
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon;
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // Metodo per aprire l'inventario del distributore (schermata ProdottiCliente)
    
    private void apriInventarioDistributore(Distributore distributore, String searchQuery) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/prodottiCliente.fxml"));
            Parent root = loader.load();
            ProdottiClienteController controller = loader.getController();
            controller.setDistributoreCorrente(distributore);
            controller.setSearchQuery(searchQuery);
            controller.setModalitaVisualizzazione(true);
            
            Stage stage = new Stage();
            // Imposta una Scene con dimensioni maggiori (ad es. 800x600)
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Prodotti del Distributore " + distributore.getIdDistr());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        distanzaColumn.setCellValueFactory(new PropertyValueFactory<>("distanza"));
        azioneColumn.setCellValueFactory(new PropertyValueFactory<>("azione"));
        visualizzaProdottoColumn.setCellValueFactory(new PropertyValueFactory<>("visualizzaProdotto"));
    }
}
