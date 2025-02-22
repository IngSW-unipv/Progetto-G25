package it.unipv.ingsfw.bitebyte.view;

import javafx.scene.layout.VBox;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.models.Stock;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

//import immagini

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.File;

public class ViewPrSelected {

 // Metodo per creare l'interfaccia grafica
 public void creaInterfaccia(Stock stock) {
     // Creazione della VBox per la disposizione verticale
     VBox vboxStock = new VBox();
     vboxStock.setSpacing(20);  // Aumenta la distanza tra gli elementi
     
     // Carica l'immagine del prodotto
     File imageFile = new File("resources/immaginiDB/" + stock.getProdotto().getIdProdotto() + ".jpg");
     Image image = new Image(imageFile.toURI().toString());
     ImageView imageView = new ImageView(image);
     imageView.setFitWidth(200);  // Imposta la larghezza dell'immagine
     imageView.setPreserveRatio(true);  // Mantiene le proporzioni dell'immagine

     // Crea le etichette per il nome e il prezzo del prodotto
     Label nomeStockLabel = new Label("Nome Prodotto: " + stock.getProdotto().getNome());
     nomeStockLabel.setFont(Font.font("Arial", 16));  // Cambia il font per un aspetto più gradevole

     Label prezzoStockLabel = new Label("Prezzo: €" + stock.getProdotto().getPrezzo());
     prezzoStockLabel.setFont(Font.font("Arial", 14));  // Font per il prezzo

     // Aggiungi l'immagine, il nome e il prezzo alla VBox
     vboxStock.getChildren().addAll(imageView, nomeStockLabel, prezzoStockLabel);

     // Crea la scena
     Scene scene = new Scene(vboxStock, 400, 300);  // Aumenta la larghezza della scena

     // Creazione dello stage (finestra)
     Stage stage = new Stage();
     stage.setTitle("Dettagli Prodotto");
     stage.setScene(scene);
     stage.show();
 }
}
