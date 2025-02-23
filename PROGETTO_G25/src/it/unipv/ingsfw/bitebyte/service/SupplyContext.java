package it.unipv.ingsfw.bitebyte.service;

import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.strategyforn.IDiscountStrategy;

public class SupplyContext {
    private IDiscountStrategy discountStrategy;
    private BigDecimal basePrice;

    public SupplyContext(IDiscountStrategy discountStrategy, BigDecimal basePrice) {
        if (basePrice == null) {
            throw new IllegalArgumentException("Il prezzo base non può essere null.");
        }
        this.discountStrategy = discountStrategy;
        this.basePrice = basePrice;
    }

    public void setDiscountStrategy(IDiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public BigDecimal calculateFinalPrice(int quantity, Stock stock) {
        if (discountStrategy == null) {
            throw new IllegalStateException("Nessuna strategia di sconto impostata.");
        }
        return discountStrategy.applyDiscount(basePrice, quantity, stock).max(BigDecimal.ZERO);
    }
}
