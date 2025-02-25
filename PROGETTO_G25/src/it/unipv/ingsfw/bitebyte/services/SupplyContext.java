package it.unipv.ingsfw.bitebyte.services;

import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.strategyforn.IDiscountStrategy;

/**
 * La classe SupplyContext gestisce l'applicazione di strategie di sconto sui prezzi 
 * di base dei prodotti offerti dai fornitori in base alla quantità e allo stock disponibile.
 */
public class SupplyContext {
    private IDiscountStrategy discountStrategy;
    private BigDecimal basePrice;

    /**
     * Costruttore della classe SupplyContext.
     *
     * @param discountStrategy La strategia di sconto da applicare, tramite interfaccia.
     * @param basePrice Il prezzo base del prodotto.
     * @throws IllegalArgumentException Se il prezzo base è null.
     */
    public SupplyContext(IDiscountStrategy discountStrategy, BigDecimal basePrice) {
        if (basePrice == null) {
            throw new IllegalArgumentException("Il prezzo base non può essere null.");
        }
        this.discountStrategy = discountStrategy;
        this.basePrice = basePrice;
    }

    /**
     * Imposta una nuova strategia di sconto.
     *
     * @param discountStrategy La nuova strategia di sconto da applicare.
     */
    public void setDiscountStrategy(IDiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    /**
     * Calcola il prezzo finale applicando la strategia di sconto attuale.
     *
     * @param quantity La quantità di prodotti acquistati.
     * @param stock L'oggetto Stock associato al prodotto.
     * @return Il prezzo finale con lo sconto applicato, garantendo che non sia negativo.
     * @throws IllegalStateException Se non è stata impostata una strategia di sconto.
     */
    public BigDecimal calculateFinalPrice(int quantity, Stock stock) {
        if (discountStrategy == null) {
            throw new IllegalStateException("Nessuna strategia di sconto impostata.");
        }
        return discountStrategy.applyDiscount(basePrice, quantity, stock).max(BigDecimal.ZERO);
    }
}
