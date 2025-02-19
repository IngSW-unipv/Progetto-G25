package it.unipv.ingsfw.bitebyte.view;
import javafx.scene.control.Button;

// Definizione della classe wrapper interna (o crea una classe a parte)
public class DistributoreWrapper {
    private String nome;
    private String indirizzo;
    private double distanza;
    private Button azione;
    
    public DistributoreWrapper(String nome, String indirizzo, double distanza, Button azione) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.distanza = distanza;
        this.azione = azione;
    }
    
    // Getter per la TableView
    public String getNome() { return nome; }
    public String getIndirizzo() { return indirizzo; }
    public double getDistanza() { return distanza; }
    public Button getAzione() { return azione; }
}