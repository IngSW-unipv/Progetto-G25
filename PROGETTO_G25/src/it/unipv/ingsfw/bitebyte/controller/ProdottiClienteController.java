package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.service.DistributoreService;
import it.unipv.ingsfw.bitebyte.service.StockService;
import it.unipv.ingsfw.bitebyte.view.ViewComponentProductBox;
import it.unipv.ingsfw.bitebyte.view.ViewManager;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ProdottiClienteController {

    private Distributore distributoreCorrente;
    private StockService stockService = new StockService();
    private DistributoreService distributoreService = new DistributoreService();
    private boolean modalitaVisualizzazione = false;
    private int idInventario;
    private int currentSugar = 0;

    @FXML
    private FlowPane prodottiContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button filterButton;
    @FXML
    private VBox filterPanel;
    @FXML
    private ComboBox<String> categoryFilter;
    @FXML
    private CheckBox availabilityFilter;
    @FXML
    private ToggleGroup priceToggleGroup;
    @FXML
    private RadioButton priceAsc;
    @FXML
    private RadioButton priceDesc;
    @FXML
    private HBox sugarControls;
    @FXML
    private Button btnSugarMinus;
    @FXML
    private Button btnSugarPlus;
    @FXML
    private Label sugarLevel;

    public void setDistributoreCorrente(Distributore distributore) {
        this.distributoreCorrente = distributore;
        if(distributore != null) {
            setIdInventario(distributore.getIdInventario());
        }
    }

    public void initialize() {
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        prodottiContainer.setPrefWrapLength(600);
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            prodottiContainer.setPrefWrapLength(newVal.doubleValue() - 20);
        });

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
        if(currentSugar > 0) {
            currentSugar--;
            sugarLevel.setText(String.valueOf(currentSugar));
        }
    }

    private void handleSugarPlus() {
        if(currentSugar < 5) {
            currentSugar++;
            sugarLevel.setText(String.valueOf(currentSugar));
        }
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        sugarControls.setVisible(idInventario == 1 || idInventario == 3 || idInventario == 5);
        caricaProdotti("");
    }

    public void caricaProdotti(String query) {
        List<Stock> stocks = stockService.getStocksByInventario(idInventario);
        // Applica qui i filtri sul nome o altri se necessari
        List<Stock> stocksFiltrati = stocks.stream()
                .filter(s -> s.getProdotto().getNome().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        prodottiContainer.getChildren().clear();

        if (stocksFiltrati.isEmpty() && !query.trim().isEmpty()) {
            // Mostra messaggio di prodotto non trovato
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
        if(stocks.isEmpty()){
            Label noResultsLabel = new Label("Nessun prodotto trovato.");
            noResultsLabel.getStyleClass().add("product-name");
            prodottiContainer.getChildren().add(noResultsLabel);
        } else {
            // Per ciascun Stock, crea la card delegando alla classe di View
            stocks.forEach(stock -> {
                // onSelectAction: definire cosa fare quando viene selezionato il prodotto
                Runnable onSelectAction = () -> handleSelect(stock);
                ViewComponentProductBox card = new ViewComponentProductBox(stock, modalitaVisualizzazione, onSelectAction);
                prodottiContainer.getChildren().add(card);
            });
        }
    }

    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato1: " + stock.getProdotto().getNome());
        AcquistoController acquistoController = new AcquistoController();
        
        // Passo lo stock (prodotto selezionato) al controller
        acquistoController.setStockSelezionato(stock);
        
        Runnable onSelectAction = () -> {
            // Codice da eseguire quando il bottone viene premuto, ad esempio, navigare a un'altra vista
            System.out.println("Prodotto selezionato!");
        };

        // Imposta modalitaVisualizzazione a true o false a seconda dei casi
        boolean modalitaVisualizzazione = false;
        
        Stage stage = (Stage) prodottiContainer.getScene().getWindow();
        Stage previousStage = (Stage) prodottiContainer.getScene().getWindow();
        // Ora chiama creaInterfaccia passando tutti e tre gli argomenti
        ViewPrSelected viewPrSelected = new ViewPrSelected();
        viewPrSelected.creaInterfaccia(stock, modalitaVisualizzazione, onSelectAction, stage, previousStage);
        
        // Passa il controllo alla vista per la creazione dell'interfaccia
     /*   
        ViewPrSelected view = new ViewPrSelected();
        view.creaInterfaccia(stock);*/
    }
	
        
        
        
        // Logica per l'azione di selezione (es. aggiungere al carrello)


    @FXML
    public void handleFilter(ActionEvent event) {
        filterPanel.setVisible(!filterPanel.isVisible());
    }

    @FXML
    public void applyFilters() {
        List<Stock> stocks = stockService.getStocksByInventario(idInventario);
        String searchQuery = searchField.getText();
        if(!searchQuery.isEmpty()){
            stocks = stocks.stream()
                    .filter(s -> s.getProdotto().getNome().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());
        }
        // Filtraggio per categoria
        String selectedCategory = categoryFilter.getValue();
        if(selectedCategory != null && !selectedCategory.isEmpty()){
            try {
                // Assumendo che Categoria sia un enum e che il nome corrisponda dopo alcune manipolazioni
                stocks = stocks.stream()
                        .filter(s -> s.getProdotto().getCategoria().toString().equalsIgnoreCase(selectedCategory.replace(" ", "_")))
                        .collect(Collectors.toList());
            } catch(Exception e) {
                System.out.println("Errore: Categoria non valida -> " + selectedCategory);
            }
        }
        if(availabilityFilter.isSelected()){
            stocks = stocks.stream()
                    .filter(s -> s.getQuantitaDisp() > 0)
                    .collect(Collectors.toList());
        }
        if(priceAsc.isSelected()){
            stocks.sort((s1, s2) -> s1.getProdotto().getPrezzo().compareTo(s2.getProdotto().getPrezzo()));
        } else if(priceDesc.isSelected()){
            stocks.sort((s1, s2) -> s2.getProdotto().getPrezzo().compareTo(s1.getProdotto().getPrezzo()));
        }
        aggiornaProdotti(stocks);
    }

    public void mostraDistributoriAlternativiByName(String nomeProdotto) {
        if(distributoreCorrente == null) {
            System.err.println("distributoreCorrente è null! Imposta correttamente il distributore.");
            return;
        }
        List<Distributore> distributori = distributoreService.getDistributoriAlternativiByName(distributoreCorrente.getIdDistr(), nomeProdotto);
        if(distributori.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prodotto non disponibile");
            alert.setContentText("Non ci sono distributori alternativi disponibili per questo prodotto.");
            alert.showAndWait();
        } else {
        	ViewManager.openDistributoriAlternativiView(nomeProdotto, distributori, distributoreCorrente);
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

  







