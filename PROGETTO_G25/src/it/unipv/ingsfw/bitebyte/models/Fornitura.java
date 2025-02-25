package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;

/**
 * Rappresenta una fornitura, che è l'associazione tra un prodotto, un fornitore e il prezzo unitario
 * che il fornitore applica per quel prodotto.
 */
public class Fornitura {
    private Prodotto prodotto;   // Prodotto associato alla fornitura
    private Fornitore fornitore; // Fornitore che offre il prodotto
    private BigDecimal ppu;      // Prezzo unitario del prodotto fatto dal fornitore

    /**
     * Costruttore per creare un oggetto Fornitura con il prodotto, il fornitore e il prezzo unitario.
     * 
     * @param prodotto Il prodotto fornito.
     * @param fornitore Il fornitore che offre il prodotto.
     * @param ppu Il prezzo unitario per il prodotto fornito.
     */
    public Fornitura(Prodotto prodotto, Fornitore fornitore, BigDecimal ppu) {
        this.prodotto = prodotto;
        this.fornitore = fornitore;
        this.ppu = ppu;
    }

    /**
     * Restituisce il prodotto associato alla fornitura.
     * 
     * @return Il prodotto fornito.
     */
    public Prodotto getProdotto() {
        return prodotto;
    }

    /**
     * Restituisce il fornitore che offre il prodotto.
     * 
     * @return Il fornitore della fornitura.
     */
    public Fornitore getFornitore() {
        return fornitore;
    }

    /**
     * Restituisce il prezzo unitario del prodotto fornito.
     * 
     * @return Il prezzo unitario del prodotto.
     */
    public BigDecimal getPpu() {
        return ppu;
    }

    /**
     * Imposta il prodotto per questa fornitura.
     * 
     * @param prodotto Il prodotto da associare alla fornitura.
     */
    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    /**
     * Imposta il fornitore per questa fornitura.
     * 
     * @param fornitore Il fornitore da associare alla fornitura.
     */
    public void setFornitore(Fornitore fornitore) {
        this.fornitore = fornitore;
    }

    /**
     * Imposta il prezzo unitario per il prodotto fornito.
     * 
     * @param ppu Il nuovo prezzo unitario per il prodotto.
     */
    public void setPpu(BigDecimal ppu) {
        this.ppu = ppu;
    }
}
