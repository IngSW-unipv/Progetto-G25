package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;
import it.unipv.ingsfw.bitebyte.view.DistributoreBin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.Desktop;
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
    
    // Il distributore corrente serve per calcolare la distanza
    private Distributore distributoreCorrente;

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
            
            data.add(new DistributoreBin(
                String.valueOf(d.getIdDistr()),        // Nome/ID del distributore
                d.getVia() + " " + d.getNCivico(),         // Indirizzo
                distanza,                                  // Distanza
                btnVai                                     // Pulsante per aprire Google Maps
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
    
 
    
    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        distanzaColumn.setCellValueFactory(new PropertyValueFactory<>("distanza"));
        azioneColumn.setCellValueFactory(new PropertyValueFactory<>("azione"));
    }
}
