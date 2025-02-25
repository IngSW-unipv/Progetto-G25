package it.unipv.ingsfw.bitebyte.view;
import javafx.scene.control.Button;

// Definizione della classe wrapper 
public class DistributoreBin {
    private String nome;
    private String indirizzo;
    private double distanza;
    private Button azione;
    private Button visualizzaProdotto; // Pulsante "Visualizza prodotto"
   
    //costruttore
    public DistributoreBin(String nome, String indirizzo, double distanza, Button azione, Button visualizzaProdotto) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.distanza = distanza;
        this.azione = azione;
        this.visualizzaProdotto = visualizzaProdotto;
    }
    
    // Getter per la TableView
    public String getNome() { return nome; }
    public String getIndirizzo() { return indirizzo; }
    public String getDistanza() {
        return String.format("%.2f km", distanza); // Restituisce la distanza formattata con due decimali
    }

    public Button getAzione() { return azione; }
    
    public Button getVisualizzaProdotto() {
        return visualizzaProdotto;
    }
}