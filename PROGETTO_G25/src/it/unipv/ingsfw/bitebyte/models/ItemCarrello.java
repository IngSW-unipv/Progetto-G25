package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;

/**
 * Classe che rappresenta un singolo articolo nel carrello. Ogni articolo è associato a una 
 * fornitura (contenente un prodotto e il fornitore), una quantità ordinata e il prezzo totale 
 * (calcolato come quantità * prezzo unitario del prodotto).
 */
public class ItemCarrello {
    private Fornitura fornitura;  // Presenta fornitore e prodotto associati
    private int quantita;         // Quantità del prodotto ordinato
    private BigDecimal prezzoTotale; // Prezzo totale (quantità * prezzo unitario)

    /**
     * Costruttore per creare un nuovo articolo nel carrello.
     * 
     * @param fornitura La fornitura associata a questo articolo, contenente il prodotto e il fornitore.
     * @param quantita La quantità ordinata del prodotto.
     * @param prezzoTotale Il prezzo totale per prodotto.
     */
    public ItemCarrello(Fornitura fornitura, int quantita, BigDecimal prezzoTotale) {
        this.fornitura = fornitura;
        this.quantita = quantita;
        this.prezzoTotale = prezzoTotale;
    }

    /**
     * Restituisce la fornitura associata a questo articolo.
     * 
     * @return La fornitura dell'articolo.
     */
    public Fornitura getFornitura() {
        return fornitura;
    }

    /**
     * Restituisce la quantità di prodotto ordinato per questo articolo.
     * 
     * @return La quantità dell'articolo.
     */
    public int getQuantita() {
        return quantita;
    }

    /**
     * Restituisce il prezzo totale dell'articolo (quantità * prezzo unitario).
     * 
     * @return Il prezzo totale dell'articolo.
     */
    public BigDecimal getPrezzoTotale() {
        return prezzoTotale;
    }

    /**
     * Incrementa la quantità di un prodotto in questo articolo e ricalcola il prezzo totale
     * in base alla nuova quantità.
     * 
     * @param aggiungiQuantita La quantità da aggiungere all'articolo.
     */
    public void incrementaQuantita(int aggiungiQuantita) {
        this.quantita += aggiungiQuantita;
        // Ricalcola il prezzo totale in base alla nuova quantità
        this.prezzoTotale = this.prezzoTotale.add(this.prezzoTotale.divide(new BigDecimal(quantita - aggiungiQuantita), BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(aggiungiQuantita)));
    }

    /**
     * Imposta la fornitura associata a questo articolo.
     * 
     * @param fornitura La nuova fornitura da associare all'articolo.
     */
    public void setFornitura(Fornitura fornitura) {
        this.fornitura = fornitura;
    }

    /**
     * Imposta la quantità di prodotto per questo articolo.
     * 
     * @param quantita La nuova quantità da impostare.
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /**
     * Imposta il prezzo totale di questo articolo.
     * 
     * @param prezzoTotale Il nuovo prezzo totale da impostare.
     */
    public void setPrezzoTotale(BigDecimal prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }
}
