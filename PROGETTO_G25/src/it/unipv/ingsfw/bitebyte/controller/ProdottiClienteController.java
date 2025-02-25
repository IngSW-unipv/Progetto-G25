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

	 private Distributore distributoreCorrente; // Distributore corrente
	 private StockDAO stockDAO = new StockDAO();
	 private DistributoreDAO distributoreDAO = new DistributoreDAO();
	 private boolean modalitaVisualizzazione = false;
	 
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
    
    private int currentSugar = 0;

    private int idInventario;

    
 // In ProdottiClienteController
    public void setDistributoreCorrente(Distributore distributore) {
        this.distributoreCorrente = distributore;
        if (distributore != null) {
            setIdInventario(distributore.getIdInventario()); // Imposta l'ID inventario automaticamente
        }
    }
    // Inizializzazione della scena
    public void initialize() {
    	
    	
        prodottiContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        prodottiContainer.setPrefWrapLength(600);

        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            prodottiContainer.setPrefWrapLength(newVal.doubleValue() - 20);
        });

        // Listener per il campo di ricerca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> caricaProdotti(newVal));
        searchField.setFocusTraversable(false);
        filterPanel.setVisible(false); // Nasconde inizialmente i filtri
        
        categoryFilter.setItems(javafx.collections.FXCollections.observableArrayList(
            "Bevanda Calda", "Bevanda Fredda", "Snack Salato", "Snack Dolce"
        ));

        sugarControls.setVisible(false); // I controlli dello zucchero sono nascosti
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
        if (idInventario == 1 || idInventario == 3 || idInventario == 5) {
            sugarControls.setVisible(true);
        } else {
            sugarControls.setVisible(false);
        }
        caricaProdotti("");
    }

    public void caricaProdotti(String query) {
        List<Stock> stocks = stockDAO.getStockByInventario(idInventario);
        List<Stock> stocksFiltrati = new FilterByNome(query).applyFilter(stocks);
        prodottiContainer.getChildren().clear();

        if (stocksFiltrati.isEmpty() && !query.trim().isEmpty()) {
            // Nessun prodotto trovato nel distributore corrente:
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
                VBox productBox = createProductBox(stock);
                prodottiContainer.getChildren().add(productBox);
            }
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
        if (stock.getQuantitaDisp() > 0) {
            if (stock.getStato().equals("Disponibile")) {
                statusLabel.setText("Stato: Disponibile");
                statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else if (stock.getStato().equals("Non disponibile")) {
                statusLabel.setText("Stato: Non disponibile");
                statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else { 
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Aggiungi gli elementi nella sequenza: immagine, nome, prezzo, quantità, stato
        box.getChildren().addAll(imageView, nameLabel, priceLabel, quantityLabel, statusLabel);

        // Se non siamo in modalità visualizzazione, aggiungi il bottone sotto lo stato
        if (!modalitaVisualizzazione) {
            Button purchaseButton = createProductButton(stock);  // Nuovo nome del metodo
            purchaseButton.setOnAction(e -> { 
            	try {
                // Carica la schermata di acquisto
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/acquisto-view.fxml"));
                AcquistoController acquistoController = new AcquistoController(); // Crea il controller
                loader.setController(acquistoController); // Imposta il controller
                VBox vbox = loader.load(); // Carica il file FXML

                // Passa lo stock al controller
                acquistoController.setSelectedStock(stock);

                // Crea una scena per la schermata di acquisto
                Scene acquistoScene = new Scene(vbox);
                Stage primaryStage = (Stage) purchaseButton.getScene().getWindow(); // Usa la finestra corrente
                primaryStage.setScene(acquistoScene); // Imposta la scena
                primaryStage.setTitle("Acquisto Prodotto");
                primaryStage.show(); // Mostra la finestra

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            });
        
        box.getChildren().add(purchaseButton);    
        box.getChildren().add(spacer);
        
        }
        return box;
    }
    
    private Button createProductButton(Stock stock) {
        Button button = new Button("Aggiungi al carrello");
        button.getStyleClass().add("product-button");
        button.setPrefWidth(200);
        // Aggiungi altre configurazioni del bottone, se necessario
        return button;
    }
    /*
    public Button createSelectButton(Stock stock) {
        Button button = new Button("Seleziona");
        button.getStyleClass().add("select-button");
        button.setOnAction(e -> handleSelect(stock));
        return button;
    }

 
    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
        if (stock.getQuantitaDisp() == 0) {
            // Passa il nome del prodotto anziché l'ID
            mostraDistributoriAlternativiByName(stock.getProdotto().getNome());
        }
    }
    
   */
    
    /*
    public Button createSelectButton(Stock stock) {
        Button button;
        
        if (stock.getQuantitaDisp() == 0) {
            // Se il prodotto è esaurito, crea il pulsante "Visualizza distributori vicini"
            button = new Button("Visualizza distributori vicini");
            button.getStyleClass().add("select-button");
            button.setOnAction(e -> mostraDistributoriAlternativiByName(stock.getProdotto().getNome()));
        } else {
            // Se il prodotto è disponibile, crea il pulsante "Seleziona"
            button = new Button("Seleziona");
            button.getStyleClass().add("select-button");
            button.setOnAction(e -> handleSelect(stock));
        }

        return button;
    }
    */

    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());
        // Aggiungi logica per il prodotto disponibile (es. aggiungere al carrello o altro)
    }


    @FXML
    public void handleFilter(ActionEvent event) {
        filterPanel.setVisible(!filterPanel.isVisible());
    }

    @FXML
    public void applyFilters() {
        List<Stock> stocks = stockDAO.getStockByInventario(idInventario);

        String searchQuery = searchField.getText();
        if (!searchQuery.isEmpty()) {
            stocks = new FilterByNome(searchQuery).applyFilter(stocks);
        }

        String selectedCategory = categoryFilter.getValue();
        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            try {
                Categoria categoriaEnum = Categoria.valueOf(selectedCategory.toUpperCase().replace(" ", "_"));
                stocks = new FilterByCategoria(categoriaEnum).applyFilter(stocks);
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: Categoria non valida -> " + selectedCategory);
            }
        }

        if (availabilityFilter.isSelected()) {
            stocks = stocks.stream().filter(stock -> stock.getQuantitaDisp() > 0).collect(Collectors.toList());
        }

        if (priceAsc.isSelected()) {
            stocks.sort((s1, s2) -> s1.getProdotto().getPrezzo().compareTo(s2.getProdotto().getPrezzo()));
        } else if (priceDesc.isSelected()) {
            stocks.sort((s1, s2) -> s2.getProdotto().getPrezzo().compareTo(s1.getProdotto().getPrezzo()));
        }

        aggiornaProdotti(stocks);
    }

    
    public void mostraDistributoriAlternativiByName(String nomeProdotto) {
        if(distributoreCorrente == null) {
            System.err.println("distributoreCorrente è null! Assicurati di impostarlo correttamente.");
            return;
        }
        
        // Utilizza il nuovo metodo DAO che sfrutta il composite filter
        List<Distributore> distributori = distributoreDAO.getDistributoriConProdottoDisponibileByName(
                distributoreCorrente.getIdDistr(), nomeProdotto);

        if (distributori.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prodotto non disponibile");
            alert.setHeaderText(null);
            alert.setContentText("Non ci sono distributori alternativi disponibili per questo prodotto.");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/distributoriAlternativi.fxml"));
                Parent root = loader.load();
                DistributoriAlternativiController controller = loader.getController();
                controller.setSearchQuery(nomeProdotto);
                controller.setDistributori(distributori, distributoreCorrente);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Distributori Alternativi");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        }
    }
        
        //parte nuova
    public void setModalitaVisualizzazione(boolean visualizza) {
        this.modalitaVisualizzazione = visualizza;
        // Ricarica i prodotti in base al testo attuale del campo di ricerca
        caricaProdotti(searchField.getText());
    }
        
        // Imposta il campo di ricerca e ricarica i prodotti filtrati
        public void setSearchQuery(String query) {
            searchField.setText(query);
            caricaProdotti(query);
        }
        
        // Modifica il metodo createSelectButton per gestire la modalità visualizzazione
        public Button createSelectButton(Stock stock) {
            if (modalitaVisualizzazione) {
                // In modalità visualizzazione, il bottone di selezione non deve essere attivo
                Button button = new Button("Visualizza");
                button.getStyleClass().add("select-button");
                button.setDisable(true);
                return button;
            } else {
                if (stock.getQuantitaDisp() == 0) {
                    Button button = new Button("Visualizza distributori vicini");
                    button.getStyleClass().add("select-button");
                    button.setOnAction(e -> mostraDistributoriAlternativiByName(stock.getProdotto().getNome()));
                    return button;
                } else {
                    Button button = new Button("Seleziona");
                    button.getStyleClass().add("select-button");
                    button.setOnAction(e -> handleSelect(stock));
                    return button;
                }
            }
        }
    }

    
    /*
    public void mostraDistributoriAlternativiByName(String nomeProdotto) {
        if(distributoreCorrente == null) {
            System.err.println("distributoreCorrente è null! Assicurati di impostarlo correttamente.");
            return;
        }
        
        DistributoreDAO distributoreDAO = new DistributoreDAO();
        List<Distributore> distributori = distributoreDAO.getDistributoriConProdottoDisponibileByName(
                distributoreCorrente.getIdDistr(), nomeProdotto);

        if (distributori.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prodotto non disponibile");
            alert.setHeaderText(null);
            alert.setContentText("Non ci sono distributori alternativi disponibili per questo prodotto.");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/distributoriAlternativi.fxml"));
                Parent root = loader.load();
                DistributoriAlternativiController controller = loader.getController();
                controller.setDistributori(distributori, distributoreCorrente);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Distributori Alternativi");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/