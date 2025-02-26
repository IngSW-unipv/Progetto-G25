/**
 *Classe controller che gestisce l'interfaccia grafica per la visualizzazione dei prodotti
 * disponibili in un distributore per il cliente.
 
 * @author Anna
 * 
 */

package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.facade.RicercaFacade;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.view.ProductView;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ProdottiClienteController {

	//Attributi 
    private Distributore distributoreCorrente;                 //distributore selezionato dal cliente
    private RicercaFacade ricercaFacade = new RicercaFacade(); // Uso RicercaFacade
    private boolean modalitaVisualizzazione = false;
    private int currentSugar = 0;                              //Contatore che indica il livello di zucchero selezionato (da 0 a 5).
    private int idInventario;                                  //Identificatore dell'inventario del distributore corrente.
    
    //elementi grafici
    
    @FXML private FlowPane prodottiContainer;         //Contenitore per i prodotti.
    @FXML private ScrollPane scrollPane;              //Permette lo scrolling verticale dei prodotti.
    @FXML private TextField searchField;              //Campo per la ricerca dei prodotti.
    @FXML private Button filterButton;                //Bottone che mostra/nasconde il pannello dei filtri.
    @FXML private VBox filterPanel;                   // Pannello che contiene i filtri.
    @FXML private ComboBox<String> categoryFilter;    //Filtro per la categoria dei prodotti.
    @FXML private CheckBox availabilityFilter;        //Filtro per visualizzare solo i prodotti disponibili.
    @FXML private ToggleGroup priceToggleGroup;       //Raggruppamento dei radio button per ordinare il prezzo.
    @FXML private RadioButton priceAsc;               //Ordina i prodotti per prezzo crescente.
    @FXML private RadioButton priceDesc;              //Ordina i prodotti per prezzo decrescente
    @FXML private HBox sugarControls;                 //Controlli per la gestione del livello di zucchero
    @FXML private Button btnSugarMinus;               //Pulsanti per modificare il livello di zucchero.
    @FXML private Button btnSugarPlus;
    @FXML private Label sugarLevel;                   //Mostra il livello attuale di zuccher

    
    // Inizializzazione
    /**
     * Metodo di inizializzazione chiamato automaticamente da JavaFX.
     * Configura l'interfaccia grafica e imposta i listener per la ricerca e i controlli.
     */

    public void initialize() {
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));   /*Questo codice imposta la larghezza preferita del contenitore dei prodotti (prodottiContainer) 
                                                                                                 in modo che sia sempre 20 pixel più stretta rispetto alla larghezza dello scrollPane.
                                                                                                */
        prodottiContainer.setPrefWrapLength(600);
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) ->
                prodottiContainer.setPrefWrapLength(newVal.doubleValue() - 20));

        searchField.textProperty().addListener((obs, oldVal, newVal) -> caricaProdotti(newVal));
        searchField.setFocusTraversable(false);  // Impedisce che il campo di testo searchField riceva automaticamente il focus quando la finestra viene aperta. così non ci sarà il cursore sulla barra di ricerca
        filterPanel.setVisible(false);           //pannello dei filtri non visibile inizialemnte
        
        //imposto le opzioni disponibili pe ril filtro delle categorie di prodotto nel box categoryFilter
        categoryFilter.setItems(javafx.collections.FXCollections.observableArrayList(
                "Bevanda Calda", "Bevanda Fredda", "Snack Salato", "Snack Dolce"
        ));

        sugarControls.setVisible(false);    //I controlli dello zucchero vengono mostrati solo per i distributori di beveande calde
        sugarLevel.setText(String.valueOf(currentSugar));
        btnSugarMinus.setOnAction(e -> handleSugarMinus());
        btnSugarPlus.setOnAction(e -> handleSugarPlus());
    }
    
    //METODI SETTER PER CONFIGURARE IL CONTESTO
    
    /**
     * Imposta il distributore corrente.
     * 
     * @param distributore il distributore selezionato
     */
    public void setDistributoreCorrente(Distributore distributore) {
        this.distributoreCorrente = distributore;
        
        if (distributore != null) {
            // SALVIAMO comunque l'idInventario, perché serve a "cercaProdotti(...)"
            this.idInventario = distributore.getIdInventario();

            // IN BASE AL TIPO, DECIDIAMO SE MOSTRARE LO ZUCCHERO
            if ("Bevande Calde".equalsIgnoreCase(distributore.getTipo())) {
                sugarControls.setVisible(true);
            } else {
                sugarControls.setVisible(false);
            }

            // INFINE CARICHIAMO I PRODOTTI
            caricaProdotti("");
        }
    }

  
    
 //versione non estendibile
    
  /*
    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        if (idInventario == 1 || idInventario == 3 || idInventario == 5) {
            sugarControls.setVisible(true);
        } else {
            sugarControls.setVisible(false);
        }
        caricaProdotti("");
    }
  */
    
    public void setModalitaVisualizzazione(boolean visualizza) {
        this.modalitaVisualizzazione = visualizza;
        caricaProdotti(searchField.getText());
    }

    public void setSearchQuery(String query) {
        searchField.setText(query);
        caricaProdotti(query);
    }
    
    //METODI PER LA LOGICA PRINCIPALE
    
    /**
     * Cerca e carica i prodotti in base a una query di ricerca.
     * 
     * @param query la query di ricerca
     */
    public void caricaProdotti(String query) {
        List<Stock> stocks = ricercaFacade.cercaProdotti(query, idInventario);
        prodottiContainer.getChildren().clear();

        if (stocks.isEmpty() && !query.trim().isEmpty()) {
            prodottiContainer.getChildren().add(ProductView.createNoProductView(query, this::mostraDistributoriAlternativiByName));
        } else {
            aggiornaProdotti(stocks);
        }
    }

    /**
     * Aggiorna la visualizzazione dei prodotti nella UI.
     * 
     * @param stocks la lista di prodotti disponibili
     */

    private void aggiornaProdotti(List<Stock> stocks) {
        prodottiContainer.getChildren().clear();
        if (stocks.isEmpty()) {
            prodottiContainer.getChildren().add(ProductView.createNoProductView("Nessun prodotto trovato.", null));
        } else {
            for (Stock stock : stocks) {
                prodottiContainer.getChildren().add(ProductView.createProductView(
                    stock,
                    modalitaVisualizzazione,
                    this::handleSelect,
                    s -> mostraDistributoriAlternativiByName(s.getProdotto().getNome())
                ));
            }
        }
    }

    
    
    //Questo metodo implementa la logica che deve essere eseguita quando un prodotto viene selezionato.
    /** @author Davide **/
    
    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());

        // Ottieni lo Stage attuale
        Stage stageAttuale = (Stage) prodottiContainer.getScene().getWindow();

        // Crea una nuova finestra per la schermata di dettaglio
        Stage newStage = new Stage();

        // Crea il controller dell'acquisto e gli passa il prodotto selezionato
        AcquistoController acquistoController = new AcquistoController(stageAttuale, newStage);
        acquistoController.setStockSelezionato(stock);

        // Mostra la schermata con il prodotto selezionato
        acquistoController.mostraInterfaccia(stock, newStage);

        // Nasconde la finestra attuale per simulare il cambio schermata
        stageAttuale.hide();
    }
    
    //METODI DI GESTIONE DEGLI EVENTI
    
    @FXML
    public void handleFilter(ActionEvent event) {
        filterPanel.setVisible(!filterPanel.isVisible());
    }

    @FXML
    public void applyFilters() {
        String searchQuery = searchField.getText();
        String selectedCategory = categoryFilter.getValue();
        boolean availability = availabilityFilter.isSelected();
        boolean sortAsc = priceAsc.isSelected();
        boolean sortDesc = priceDesc.isSelected();

        List<Stock> stocks = ricercaFacade.applicaFiltri(idInventario, searchQuery, selectedCategory, availability, sortAsc, sortDesc);
        aggiornaProdotti(stocks);
    }

    
    //METODI DI SUPPORTO
    private void handleSugarMinus() {
        if (currentSugar > 0) {
            currentSugar--;
            sugarLevel.setText(String.valueOf(currentSugar));
        }
    }

    private void handleSugarPlus() {
        if (currentSugar < 5) {
            currentSugar++;
            sugarLevel.setText(String.valueOf(currentSugar));
        }
    }
    
   
    //METODI PER LA GESTIONE DELLE ALTERNATIVE
    
    /**
     * Mostra i distributori alternativi che offrono il prodotto richiesto.
     * 
     * @param nomeProdotto il nome del prodotto cercato
     */
   
    public void mostraDistributoriAlternativiByName(String nomeProdotto) {
        if (distributoreCorrente == null) {
            System.err.println("distributoreCorrente è null! Assicurati di impostarlo correttamente.");
            return;
        }
        List<Distributore> distributori = ricercaFacade.cercaDistributoriConProdotto(distributoreCorrente.getIdDistr(), nomeProdotto);
        if (distributori.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prodotto non disponibile");
            alert.setHeaderText(null);
            alert.setContentText("Non ci sono distributori alternativi disponibili per questo prodotto.");
            alert.showAndWait();
        } else {
        	DistributoriAlternativiController controller = ViewManager.getInstance()
        	        .showStageWithController("/it/unipv/ingsfw/bitebyte/view/fxml/distributoriAlternativi.fxml", 800, 600, "Distributori Alternativi");
        	controller.setSearchQuery(nomeProdotto);
        	controller.setDistributori(distributoreCorrente.getIdDistr(), nomeProdotto);

        }
    }

   
}

  