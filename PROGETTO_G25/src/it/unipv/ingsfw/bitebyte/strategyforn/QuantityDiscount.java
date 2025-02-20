package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.models.Stock;

public class QuantityDiscount implements IDiscountStrategy {
	
    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock) {
        int maxQuantity = stock.getQMaxInseribile(); // Ottengo la quantità massima disponibile

        BigDecimal discountRate = BigDecimal.ZERO;

        if (quantity >= maxQuantity * 0.8) { // Se ordini almeno l'80% della quantità massima
            discountRate = new BigDecimal("0.10"); // 10% di sconto
        } else if (quantity >= maxQuantity * 0.5) {
            discountRate = new BigDecimal("0.7"); // 7% di sconto
        } else if (quantity >= maxQuantity * 0.2) {
            discountRate = new BigDecimal("0.05"); // 5% di sconto
        }

        return price.subtract(price.multiply(discountRate));
    }
}
