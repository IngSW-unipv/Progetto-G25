package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.service.DistributoreService;
import it.unipv.ingsfw.bitebyte.view.DistributoreBin;
import it.unipv.ingsfw.bitebyte.view.DistributoreBinFactory;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

public class DistributoriAlternativiController {

    @FXML private TableView<DistributoreBin> distributoriTable;
    @FXML private TableColumn<DistributoreBin, String> nomeColumn;
    @FXML private TableColumn<DistributoreBin, String> indirizzoColumn;
    @FXML private TableColumn<DistributoreBin, Double> distanzaColumn;
    @FXML private TableColumn<DistributoreBin, Button> azioneColumn;
    @FXML private TableColumn<DistributoreBin, Button> visualizzaProdottoColumn;

    private Distributore distributoreCorrente;
    private String searchQuery = "";
    private DistributoreService distributoreService = new DistributoreService();

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setDistributori(int distributoreCorrenteId, String nomeProdotto) {
        this.distributoreCorrente = distributoreService.getDistributoreById(distributoreCorrenteId);
        List<Distributore> distributori = distributoreService.getDistributoriConProdottoDisponibileByName(distributoreCorrenteId, nomeProdotto);
        ObservableList<DistributoreBin> data = FXCollections.observableArrayList();
        for (Distributore d : distributori) {
            DistributoreBin bin = DistributoreBinFactory.createDistributoreBin(
                d,
                distributoreCorrente,
                this.searchQuery,
                this::handleVai,
                this::handleVisualizzaProdotto
            );
            data.add(bin);
        }
        distributoriTable.setItems(data);
    }

    private void handleVai(Distributore d) {
        apriGoogleMaps(d.getLat(), d.getLon());
    }

    private void handleVisualizzaProdotto(Distributore d) {
        apriInventarioDistributore(d, this.searchQuery);
    }

    private void apriGoogleMaps(double lat, double lon) {
        try {
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon;
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void apriInventarioDistributore(Distributore distributore, String searchQuery) {
        // Usa il ViewManager per aprire la schermata ProdottiCliente
        ProdottiClienteController prodController = ViewManager.getInstance()
                .showStageWithController("/prodottiCliente.fxml", 800, 600, "Prodotti del Distributore " + distributore.getIdDistr());
        prodController.setDistributoreCorrente(distributore);
        prodController.setSearchQuery(searchQuery);
        prodController.setModalitaVisualizzazione(true);
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

