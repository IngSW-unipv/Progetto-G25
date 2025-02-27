/**
 * La classe ProductView è responsabile della creazione delle viste grafiche per i prodotti
 * all'interno dell'interfaccia utente dell'applicazione. Fornisce metodi per generare
 * rappresentazioni visive dei prodotti disponibili, mostrando informazioni come nome, prezzo,
 * quantità disponibile e stato del prodotto. Inoltre, gestisce la creazione di bottoni di azione
 * per la selezione dei prodotti o per la visualizzazione di distributori alternativi in caso di
 * indisponibilità del prodotto.
 *
 * La classe utilizza componenti JavaFX per costruire l'interfaccia grafica, tra cui `BorderPane`,
 * `VBox`, `HBox`, `Label`, `Button` e `ImageView`. I metodi di questa classe sono statici per
 * consentire la creazione di viste senza la necessità di istanziare la classe.
 *
 * @author Anna
 * 
 */

package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.function.Consumer;

public class ProductView {

    /**
     * Crea e restituisce la view (BorderPane) per un prodotto. 
     * BorderPane permette di posizionare facilmente il contenuto in aree specifiche
     *
     * @param stock                   il modello da rappresentare
     * @param modalitaVisualizzazione se siamo in modalità di sola visualizzazione
     * @param onSelect                callback per la selezione del prodotto
     * @param onShowAlternatives      callback per mostrare distributori alternativi
     * @return il BorderPane che rappresenta la view del prodotto
     */
    public static BorderPane createProductView(Stock stock, boolean modalitaVisualizzazione,
                                                 Consumer<Stock> onSelect, Consumer<Stock> onShowAlternatives) {
        BorderPane productPane = new BorderPane();
        productPane.getStyleClass().add("product-box");
        productPane.setPadding(new Insets(10));   //per garantire margini interni

        //È un layout che dispone i suoi figli in una colonna verticale
        VBox centerBox = createProductInfo(stock);   
        productPane.setCenter(centerBox);

        if (!modalitaVisualizzazione) {
        	//È un layout che dispone i suoi figli in una riga orizzontale
            HBox buttonContainer = createActionButton(stock, onSelect, onShowAlternatives);
            productPane.setBottom(buttonContainer);
        }

        return productPane;
    }

    /**
     * Crea il layout centrale con l'immagine, nome, prezzo, quantità e stato del prodotto.
     */
    private static VBox createProductInfo(Stock stock) {
        ImageView imageView = createProductImage(stock);
        Label nameLabel = createLabel(stock.getProdotto().getNome(), "product-name");
        Label priceLabel = createLabel(String.format("€ %.2f", stock.getProdotto().getPrezzo()), "product-price");
        Label quantityLabel = createLabel("Disponibili: " + stock.getQuantitaDisp(), "product-quantity");
        Label statusLabel = createStatusLabel(stock);

        VBox centerBox = new VBox(5, imageView, nameLabel, priceLabel, quantityLabel, statusLabel);  //organizzo tutti gli elementi in una VBox
        centerBox.setAlignment(Pos.CENTER);
        return centerBox;
    }

    /**
     * Crea e restituisce l'immagine del prodotto.
     */
    private static ImageView createProductImage(Stock stock) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        File productImageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
        if (productImageFile.exists()) {
            imageView.setImage(new Image(productImageFile.toURI().toString()));  //conversione del percorso in un oggetto URI 
                                                                                 //e converto URI in una stringa che è accettata dal construttore di Image(JavaFx)
        } else {
        	imageView.setImage(null);
        }

        return imageView;
    }

    /**
     * Crea e restituisce una label con uno stile specifico.
     */
    private static Label createLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    /**
     * Crea e restituisce una label con lo stato del prodotto (Disponibile, Non disponibile, Esaurito).
     */
    private static Label createStatusLabel(Stock stock) {
        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("product-status");

        if (stock.getQuantitaDisp() > 0) {
            if ("Disponibile".equals(stock.getStato())) {
                statusLabel.setText("Stato: Disponibile");
                statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else if ("Non disponibile".equals(stock.getStato())) {
                statusLabel.setText("Stato: Non disponibile");
                statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            statusLabel.setText("Stato: Esaurito");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        return statusLabel;
    }

    /**
     * Crea il bottone di selezione o il bottone per mostrare distributori alternativi.
     */
    private static HBox createActionButton(Stock stock, Consumer<Stock> onSelect, Consumer<Stock> onShowAlternatives) {
        Button actionButton;
        
        // Se il prodotto è esaurito (quantità = 0) oppure lo stato è "Non disponibile"
        if (stock.getQuantitaDisp() == 0 || "Non disponibile".equals(stock.getStato())) {
            actionButton = new Button("Visualizza distributori vicini");
            actionButton.setOnAction(e -> onShowAlternatives.accept(stock));
        } else { 
            // Il pulsante "Seleziona" appare solo se il prodotto è "Disponibile"
            actionButton = new Button("Seleziona");
            actionButton.setOnAction(e -> onSelect.accept(stock));
        }
        
        actionButton.getStyleClass().add("select-button");

        HBox buttonContainer = new HBox(actionButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 10, 0));

        return buttonContainer;
    }

    
    /**
     * Crea una vista che informa l'utente che il prodotto cercato non è disponibile
     * nel distributore corrente e offre la possibilità di visualizzare distributori alternativi.
     *
     * @param query la query di ricerca utilizzata per il prodotto non disponibile
     * @param onShowAlternatives una callback che gestisce l'azione per mostrare i distributori alternativi
     *                           che offrono il prodotto cercato
     * @return un VBox contenente il messaggio di prodotto non disponibile e un pulsante
     *         per visualizzare distributori alternativi
     */
    public static VBox createNoProductView(String query, Consumer<String> onShowAlternatives) {
        Label info = new Label("Prodotto non disponibile in questo distributore.");
        info.getStyleClass().add("product-name");

        Button btnVisualizza = new Button("Visualizza distributori vicini");
        btnVisualizza.setOnAction(e -> onShowAlternatives.accept(query));

        VBox vbox = new VBox(10, info, btnVisualizza);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

}