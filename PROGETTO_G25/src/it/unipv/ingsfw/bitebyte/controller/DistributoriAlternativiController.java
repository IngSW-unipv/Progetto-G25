/**La classe DistributoriAlternativiController gestisce la logica per la visualizzazione dei distributori alternativi
 *  che offrono un determinato prodotto. In particolare, popola una tabella (TableView) con righe di tipo DistributoreBin 
 *  (un oggetto che racchiude le informazioni visuali per un distributore) e gestisce le azioni associate 
 *  (ad esempio, aprire Google Maps o visualizzare i prodotti di un distributore).
 */

package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.services.DistributoreCompletoService;
import it.unipv.ingsfw.bitebyte.view.DistributoreBin;
import it.unipv.ingsfw.bitebyte.view.DistributoreBinFactory;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.Desktop;  //Permette di aprire URL e applicazioni esterne tramite il metodo Desktop.getDesktop().browse(...)
import java.net.URI;
import java.util.List;

//controller per gestire la logica della visualizzazione dei distributori alternativi che hanno un determinato prodotto disponibile.
public class DistributoriAlternativiController {

    @FXML private TableView<DistributoreBin> distributoriTable;                     //La tabella che mostrerà la lista dei distributori alternativi, dove ogni riga è un oggetto di tipo DistributoreBin.
    @FXML private TableColumn<DistributoreBin, String> nomeColumn;                  //Le colonne della tabella
    @FXML private TableColumn<DistributoreBin, String> indirizzoColumn;
    @FXML private TableColumn<DistributoreBin, Double> distanzaColumn;
    @FXML private TableColumn<DistributoreBin, Button> azioneColumn;
    @FXML private TableColumn<DistributoreBin, Button> visualizzaProdottoColumn;
    
    //attributi
    private Distributore distributoreCorrente;  //Rappresenta il distributore "corrente" (quello con cui si sta confrontando) e viene utilizzato per escluderlo dalla lista degli alternativi.
    private String searchQuery = "";
    private DistributoreCompletoService distributoreService = new DistributoreCompletoService();

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    //Questo metodo viene chiamato per popolare la tabella con i distributori alternativi.
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
                .showStageWithController("/it/unipv/ingsfw/bitebyte/view/fxml/prodottiCliente.fxml", 800, 600, "Prodotti del Distributore " + distributore.getIdDistr());
        prodController.setDistributoreCorrente(distributore);
        prodController.setSearchQuery(searchQuery);
        prodController.setModalitaVisualizzazione(true);
    }

    //inizializzazione della tabella
    //Configura le colonne della tabella collegandole ai campi dell’oggetto DistributoreBin.
    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        distanzaColumn.setCellValueFactory(new PropertyValueFactory<>("distanza"));
        azioneColumn.setCellValueFactory(new PropertyValueFactory<>("azione"));
        visualizzaProdottoColumn.setCellValueFactory(new PropertyValueFactory<>("visualizzaProdotto"));
    }
}

