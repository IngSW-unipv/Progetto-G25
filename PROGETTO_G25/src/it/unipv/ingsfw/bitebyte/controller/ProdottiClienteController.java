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

<<<<<<< HEAD
    private Distributore distributoreCorrente;
    private StockService stockService = new StockService();
    private DistributoreService distributoreService = new DistributoreService();
    private boolean modalitaVisualizzazione = false;
    private int idInventario;
    private int currentSugar = 0;

=======
	 private Distributore distributoreCorrente; // Distributore corrente
	 private StockDAO stockDAO = new StockDAO();
	 private DistributoreDAO distributoreDAO = new DistributoreDAO();
	 private boolean modalitaVisualizzazione = false;
	 
>>>>>>> Copia_alice_davide
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
<<<<<<< HEAD
=======
    	
    	
>>>>>>> Copia_alice_davide
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
    

<<<<<<< HEAD
=======
    
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

>>>>>>> Copia_alice_davide
    public void handleSelect(Stock stock) {
        System.out.println("Prodotto selezionato: " + stock.getProdotto().getNome());

        // Ottieni lo Stage attuale
        Stage stageAttuale = (Stage) prodottiContainer.getScene().getWindow();

        // Crea una nuova finestra per la schermata di dettaglio
        Stage newStage = new Stage();

        // Crea il controller dell'acquisto e gli passa il prodotto selezionato
        AcquistoController acquistoController = new AcquistoController(stageAttuale);
        acquistoController.setStockSelezionato(stock);

        // Mostra la schermata con il prodotto selezionato
        acquistoController.mostraInterfaccia(stock, newStage);

        // Nasconde la finestra attuale per simulare il cambio schermata
        stageAttuale.hide();
    }

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

  

<<<<<<< HEAD






=======
    
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
>>>>>>> Copia_alice_davide
