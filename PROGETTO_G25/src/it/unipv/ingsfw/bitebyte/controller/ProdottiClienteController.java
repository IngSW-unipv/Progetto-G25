package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.service.DistributoreService;
import it.unipv.ingsfw.bitebyte.service.StockService;
import it.unipv.ingsfw.bitebyte.filtri.FilterByNome;
import it.unipv.ingsfw.bitebyte.filtri.FilterFactory;
import it.unipv.ingsfw.bitebyte.filtri.IFilterStrategy;
import it.unipv.ingsfw.bitebyte.view.ProductView;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class ProdottiClienteController {

    private Distributore distributoreCorrente;
    private StockService stockService = new StockService();
    private DistributoreService distributoreService = new DistributoreService();
    private boolean modalitaVisualizzazione = false;

    @FXML private FlowPane prodottiContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField searchField;
    @FXML private Button filterButton;
    @FXML private VBox filterPanel;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private CheckBox availabilityFilter;
    @FXML private ToggleGroup priceToggleGroup;
    @FXML private RadioButton priceAsc;
    @FXML private RadioButton priceDesc;
    @FXML private HBox sugarControls;
    @FXML private Button btnSugarMinus;
    @FXML private Button btnSugarPlus;
    @FXML private Label sugarLevel;

    private int currentSugar = 0;
    private int idInventario;

    public void setDistributoreCorrente(Distributore distributore) {
        this.distributoreCorrente = distributore;
        if (distributore != null) {
            setIdInventario(distributore.getIdInventario());
        }
    }

    public void initialize() {
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        prodottiContainer.setPrefWrapLength(600);
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) ->
                prodottiContainer.setPrefWrapLength(newVal.doubleValue() - 20));

        searchField.textProperty().addListener((obs, oldVal, newVal) -> caricaProdotti(newVal));
        searchField.setFocusTraversable(false);
        filterPanel.setVisible(false);

        categoryFilter.setItems(javafx.collections.FXCollections.observableArrayList(
                "Bevanda Calda", "Bevanda Fredda", "Snack Salato", "Snack Dolce"
        ));

        sugarControls.setVisible(false);
        sugarLevel.setText(String.valueOf(currentSugar));
        btnSugarMinus.setOnAction(e -> handleSugarMinus());
        btnSugarPlus.setOnAction(e -> handleSugarPlus());
    }

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

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        if (idInventario == 1 || idInventario == 3 || idInventario == 5) {
            sugarControls.setVisible(true);
        } else {
            sugarControls.setVisible(false);
        }
        caricaProdotti("");
    }

    public void caricaProdotti(String query) {
        List<Stock> stocks = stockService.getStockByInventario(idInventario);
        List<Stock> stocksFiltrati = new FilterByNome(query).applyFilter(stocks);
        prodottiContainer.getChildren().clear();

        if (stocksFiltrati.isEmpty() && !query.trim().isEmpty()) {
            Label info = new Label("Prodotto non disponibile in questo distributore.");
            Button btnVisualizza = new Button("Visualizza distributori vicini");
            btnVisualizza.setOnAction(e -> mostraDistributoriAlternativiByName(query));
            prodottiContainer.getChildren().addAll(info, btnVisualizza);
        } else {
            aggiornaProdotti(stocksFiltrati);
        }
    }

    private void aggiornaProdotti(List<Stock> stocks) {
        prodottiContainer.getChildren().clear();
        if (stocks.isEmpty()) {
            Label noResultsLabel = new Label("Nessun prodotto trovato.");
            noResultsLabel.getStyleClass().add("product-name");
            prodottiContainer.getChildren().add(noResultsLabel);
        } else {
            for (Stock stock : stocks) {
                // ProductView crea un BorderPane per ogni prodotto
                BorderPane productBox = ProductView.createProductView(
                        stock,
                        modalitaVisualizzazione,
                        this::handleSelect,
                        s -> mostraDistributoriAlternativiByName(s.getProdotto().getNome())
                );
                prodottiContainer.getChildren().add(productBox);
            }
        }
    }

    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
        // Logica per gestire la selezione (es. aggiungere al carrello)
    }

    @FXML
    public void handleFilter(ActionEvent event) {
        filterPanel.setVisible(!filterPanel.isVisible());
    }

    @FXML
    public void applyFilters() {
        List<Stock> stocks = stockService.getStockByInventario(idInventario);
        String searchQuery = searchField.getText();
        String selectedCategory = categoryFilter.getValue();
        boolean availability = availabilityFilter.isSelected();
        boolean sortAsc = priceAsc.isSelected();
        boolean sortDesc = priceDesc.isSelected();

        IFilterStrategy filter = FilterFactory.createFilter(searchQuery, selectedCategory, availability, sortAsc, sortDesc);
        stocks = filter.applyFilter(stocks);
        aggiornaProdotti(stocks);
    }

    public void mostraDistributoriAlternativiByName(String nomeProdotto) {
        if (distributoreCorrente == null) {
            System.err.println("distributoreCorrente è null! Assicurati di impostarlo correttamente.");
            return;
        }
        List<Distributore> distributori = distributoreService.getDistributoriConProdottoDisponibileByName(
                distributoreCorrente.getIdDistr(), nomeProdotto);
        if (distributori.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prodotto non disponibile");
            alert.setHeaderText(null);
            alert.setContentText("Non ci sono distributori alternativi disponibili per questo prodotto.");
            alert.showAndWait();
        } else {
            // Utilizza il ViewManager per aprire la schermata DistributoriAlternativi
            DistributoriAlternativiController controller = ViewManager.getInstance()
                    .showStageWithController("/distributoriAlternativi.fxml", 800, 600, "Distributori Alternativi");
            controller.setSearchQuery(nomeProdotto);
            controller.setDistributori(distributori, distributoreCorrente);
        }
    }

    public void setModalitaVisualizzazione(boolean visualizza) {
        this.modalitaVisualizzazione = visualizza;
        caricaProdotti(searchField.getText());
    }

    public void setSearchQuery(String query) {
        searchField.setText(query);
        caricaProdotti(query);
    }
}

   