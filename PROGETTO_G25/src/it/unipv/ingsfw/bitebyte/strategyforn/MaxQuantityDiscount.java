package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class MaxQuantityDiscount implements IDiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock) {
        if (quantity == stock.getQMaxInseribile()) {
            return price.multiply(new BigDecimal("0.90")); // 10% di sconto
        }
        return price;
    }
}
