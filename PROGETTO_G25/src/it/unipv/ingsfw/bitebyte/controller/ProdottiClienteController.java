package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.facade.RicercaFacade;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
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
    private RicercaFacade ricercaFacade = new RicercaFacade(); // Usa RicercaFacade
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
        List<Stock> stocks = ricercaFacade.cercaProdotti(query, idInventario);
        prodottiContainer.getChildren().clear();

        if (stocks.isEmpty() && !query.trim().isEmpty()) {
            Label info = new Label("Prodotto non disponibile in questo distributore.");
            Button btnVisualizza = new Button("Visualizza distributori vicini");
            btnVisualizza.setOnAction(e -> mostraDistributoriAlternativiByName(query));
            prodottiContainer.getChildren().addAll(info, btnVisualizza);
        } else {
            aggiornaProdotti(stocks);
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
    }

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
        	        .showStageWithController("/distributoriAlternativi.fxml", 800, 600, "Distributori Alternativi");
        	controller.setSearchQuery(nomeProdotto);
        	controller.setDistributori(distributoreCorrente.getIdDistr(), nomeProdotto);

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


   