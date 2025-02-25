package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Fornitura;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe per la gestione della visualizzazione del rifornimento dei prodotti.
 * Permette di selezionare un fornitore e specificare la quantità di prodotti da ordinare da esso.
 */
public class RifornimentoView {
    private VBox rootLayout;
    private FlowPane fornitoriContainer;
    private RifornimentoListener listener;

    /**
     * Costruttore della vista di rifornimento.
     * 
     * @param forniture Lista delle forniture disponibili.
     * @param stock Stock del prodotto da rifornire.
     * @param listener Listener per gestire la selezione del fornitore.
     */
    public RifornimentoView(ArrayList<Fornitura> forniture, Stock stock, RifornimentoListener listener) {
        this.listener = listener;

        rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(15));
        rootLayout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Seleziona un fornitore per: " + stock.getProdotto().getNome());
        titleLabel.getStyleClass().add("title-label");

        fornitoriContainer = new FlowPane();
        fornitoriContainer.setAlignment(Pos.CENTER);
        fornitoriContainer.setHgap(20);
        fornitoriContainer.setVgap(20);

        ScrollPane scrollPane = new ScrollPane(fornitoriContainer);
        scrollPane.setFitToWidth(true);

        for (Fornitura f : forniture) {
            fornitoriContainer.getChildren().add(creaRiquadroFornitura(f, stock));
        }

        rootLayout.getChildren().addAll(titleLabel, scrollPane);
    }

    /**
     * Crea un riquadro contenente le informazioni di un fornitore e un campo per specificare la quantità
     * del prodotto desiderato.
     * 
     * @param fornitura La fornitura del fornitore.
     * @param stock Il prodotto da rifornire.
     * @return Un VBox contenente le informazioni del fornitore e il pulsante di selezione
     *         per confermare la quantità inserita e il fornitore scelto.
     */
    private VBox creaRiquadroFornitura(Fornitura fornitura, Stock stock) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        box.setPrefSize(350, 200);
        box.getStyleClass().add("fornitore-box");

        Label nameLabel = new Label("Fornitore: " + fornitura.getFornitore().getNomeF());
        Label priceLabel = new Label("Prezzo unitario: €" + fornitura.getPpu());
        Label cityLabel = new Label("Città: " + fornitura.getFornitore().getCittaF());
        
        nameLabel.getStyleClass().add("nome-fornitore");
        priceLabel.getStyleClass().add("prezzo-fornitore");
        cityLabel.getStyleClass().add("citta-fornitore");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantità");

        Button selectButton = new Button("Seleziona");
        selectButton.setOnAction(e -> {
            try {
                int quantita = Integer.parseInt(quantityField.getText());

                // Passa al controller per il controllo e la logica dello sconto
                listener.onFornitoreSelezionato(fornitura, quantita);
                
            } catch (NumberFormatException ex) {
                showError("Inserisci un numero valido per la quantità.");
            }
        });

        box.getChildren().addAll(nameLabel, cityLabel, priceLabel, quantityField, selectButton);
        return box;
    }

    /**
     * Mostra un messaggio di errore in caso di input non valido.
     * 
     * @param message Il messaggio di errore da mostrare.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Mostra la finestra per l'elenco dei fornitori
     */
    public void mostra() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Seleziona un fornitore");

        Scene scene = new Scene(getView());
        String css = getClass().getResource("/css/StileForniture.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Restituisce il layout principale della vista.
     * 
     * @return Il layout principale come VBox.
     */
    public VBox getView() {
        return rootLayout;
    }

    /**
     * Interfaccia per la gestione della selezione di un fornitore.
     */
    public interface RifornimentoListener {
        void onFornitoreSelezionato(Fornitura fornitura, int quantita);
    }
}
