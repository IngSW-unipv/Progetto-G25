package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;

public class ItemCarrello {
    private Fornitura fornitura;  // Presenta fornitore e prodotto associati
    private int quantita;         // Quantità del prodotto ordinato
    private BigDecimal prezzoTotale; // Prezzo totale (quantità * prezzo unitario)

    public ItemCarrello(Fornitura fornitura, int quantita, BigDecimal prezzoTotale) {
        this.fornitura = fornitura;
        this.quantita = quantita;
        this.prezzoTotale = prezzoTotale;
    }

    public Fornitura getFornitura() {
        return fornitura;
    }

    public int getQuantita() {
        return quantita;
    }

    public BigDecimal getPrezzoTotale() {
        return prezzoTotale;
    }

    // Incrementa la quantità di un prodotto
    public void incrementaQuantita(int aggiungiQuantita) {
        this.quantita += aggiungiQuantita;
        // Ricalcola il prezzo totale in base alla nuova quantità
        this.prezzoTotale = this.prezzoTotale.add(this.prezzoTotale.divide(new BigDecimal(quantita - aggiungiQuantita), BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(aggiungiQuantita)));
    }

    public void setFornitura(Fornitura fornitura) {
        this.fornitura = fornitura;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setPrezzoTotale(BigDecimal prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }
}