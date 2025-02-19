package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.DistributoreDAO;
import it.unipv.ingsfw.bitebyte.dao.StockDAO;
import it.unipv.ingsfw.bitebyte.filtri.*;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdottiClienteController {

    private StockDAO stockDAO = new StockDAO();

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

    // Controlli per lo zucchero
    @FXML
    private HBox sugarControls;
    @FXML
    private Button btnSugarMinus;
    @FXML
    private Button btnSugarPlus;
    @FXML
    private Label sugarLevel;
    
    private int currentSugar = 0;

    private int idInventario;

    public void initialize() {
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        prodottiContainer.setPrefWrapLength(600);

        // Configura il comportamento del ScrollPane (già impostato tramite AnchorPane in FXML)
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            prodottiContainer.setPrefWrapLength(newVal.doubleValue() - 20);
        });

        // Listener per il campo di ricerca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> caricaProdotti(newVal));

        // Nascondi il pannello dei filtri inizialmente
        filterPanel.setVisible(false);
        
        // Popola la ComboBox delle categorie (senza la voce "Tutte")
        categoryFilter.setItems(javafx.collections.FXCollections.observableArrayList(
            "Bevanda Calda", "Bevanda Fredda", "Snack Salato", "Snack Dolce"
        ));
        
        // I controlli dello zucchero sono nascosti di default.
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
        if(currentSugar < 5) { // Massimo 5 zuccheri
            currentSugar++;
            sugarLevel.setText(String.valueOf(currentSugar));
        }

    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        // Mostra i controlli dello zucchero solo per i distributori 1, 3, 5
        if (idInventario == 1 || idInventario == 3 || idInventario == 5) {
            sugarControls.setVisible(true);
        } else {
            sugarControls.setVisible(false);
        }
        caricaProdotti("");
    }

    public void caricaProdotti(String query) {
        List<Stock> stocks = stockDAO.getStockByInventario(idInventario);
        // Applica il filtro per il nome del prodotto
        stocks = new FilterByNome(query).applyFilter(stocks);
     // Se non trovi alcuno stock nel distributore corrente e la query non è vuota, chiedi all'utente se desidera cercare distributori alternativi
        if (!query.trim().isEmpty() && stocks.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Prodotto non disponibile");
            alert.setHeaderText(null);
            alert.setContentText("Il prodotto \"" + query + "\" non è disponibile in questo distributore.\nVuoi cercare distributori alternativi?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                cercaDistributoriAlternativi(query);
                return; // Esci da questo metodo per evitare di aggiornare la lista con zero stock
            }
        }
        
        aggiornaProdotti(stocks);
    }
    
 

    private void aggiornaProdotti(List<Stock> stocks) {
        prodottiContainer.getChildren().clear(); 
        for (Stock stock : stocks) {
            VBox productBox = createProductBox(stock);
            prodottiContainer.getChildren().add(productBox);
        }
    }

    private VBox createProductBox(Stock stock) {
        VBox box = new VBox(5);
        box.getStyleClass().add("product-box");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        File productImageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (productImageFile.exists()) {
            imageView.setImage(new Image(productImageFile.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/immaginiDB/default.jpg")));
        }

        Label nameLabel = new Label(stock.getProdotto().getNome());
        nameLabel.getStyleClass().add("product-name");

        Label priceLabel = new Label(String.format("€ %.2f", stock.getProdotto().getPrezzo()));
        priceLabel.getStyleClass().add("product-price");

        Label quantityLabel = new Label("Disponibili: " + stock.getQuantitaDisp());
        quantityLabel.getStyleClass().add("product-quantity");

        Label statusLabel = new Label("Stato: " + stock.getStato());
        statusLabel.getStyleClass().add("product-status");
        if (stock.getQuantitaDisp() > 5) {
            statusLabel.setText("Stato: Disponibile");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (stock.getQuantitaDisp() == 0) {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button selectButton = createSelectButton(stock);
        box.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel, statusLabel, spacer, selectButton);
        return box;
    }

    public Button createSelectButton(Stock stock) {
        Button button = new Button("Seleziona");
        button.getStyleClass().add("select-button");
        button.setOnAction(e -> handleSelect(stock));
        return button;
    }

    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
    }

    @FXML
    public void handleFilter(ActionEvent event) {
        // Alterna la visibilità del pannello dei filtri
        filterPanel.setVisible(!filterPanel.isVisible());
    }

    @FXML
    public void applyFilters() {
        List<Stock> stocks = stockDAO.getStockByInventario(idInventario);

        String searchQuery = searchField.getText();
        if (!searchQuery.isEmpty()) {
            stocks = new FilterByNome(searchQuery).applyFilter(stocks);
        }

        // Filtra per categoria
        String selectedCategory = categoryFilter.getValue();
        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            try {
                // Esempio: "Bevanda Calda" -> "BEVANDA_CALDA"
                Categoria categoriaEnum = Categoria.valueOf(selectedCategory.toUpperCase().replace(" ", "_"));
                stocks = new FilterByCategoria(categoriaEnum).applyFilter(stocks);
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: Categoria non valida -> " + selectedCategory);
            }
        }

        // Filtra per disponibilità
        if (availabilityFilter.isSelected()) {
            stocks = stocks.stream().filter(stock -> stock.getQuantitaDisp() > 0).collect(Collectors.toList());
        }

        // Filtra per prezzo
        if (priceAsc.isSelected()) {
            stocks.sort((s1, s2) -> s1.getProdotto().getPrezzo().compareTo(s2.getProdotto().getPrezzo()));
        } else if (priceDesc.isSelected()) {
            stocks.sort((s1, s2) -> s2.getProdotto().getPrezzo().compareTo(s1.getProdotto().getPrezzo()));
        }

        aggiornaProdotti(stocks);
    }
}



