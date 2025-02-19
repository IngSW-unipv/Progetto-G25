package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;
import it.unipv.ingsfw.bitebyte.view.DistributoreWrapper;
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
    private TableView<DistributoreWrapper> distributoriTable;
    @FXML
    private TableColumn<DistributoreWrapper, String> nomeColumn;
    @FXML
    private TableColumn<DistributoreWrapper, String> indirizzoColumn;
    @FXML
    private TableColumn<DistributoreWrapper, Double> distanzaColumn;
    @FXML
    private TableColumn<DistributoreWrapper, Button> azioneColumn;
    
    // Il distributore corrente serve per calcolare la distanza
    private Distributore distributoreCorrente;

    public void setDistributori(List<Distributore> distributori, Distributore distributoreCorrente) {
        this.distributoreCorrente = distributoreCorrente;
        ObservableList<DistributoreWrapper> data = FXCollections.observableArrayList();
        for (Distributore d : distributori) {
            double distanza = CalcolaDistanza.calcolaDistanza(distributoreCorrente, d);
            // Crea un pulsante "Vai" per ogni riga
            Button btnVai = new Button("Vai");
            btnVai.setOnAction((ActionEvent event) -> {
                apriGoogleMaps(d.getLat(), d.getLon());
            });
            // Crea il wrapper con le informazioni
            data.add(new DistributoreWrapper(
                    d.getTipo(), // oppure un nome/descrizione che preferisci
                    d.getVia() + " " + d.getNCivico(),
                    distanza,
                    btnVai
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
