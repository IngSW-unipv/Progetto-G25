package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import java.math.RoundingMode;

import it.unipv.ingsfw.bitebyte.models.Stock;

public class QuantityDiscount implements IDiscountStrategy {
	
    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock) {
        int maxQuantity = stock.getQMaxInseribile(); // Ottengo la quantità massima inseribile per quel prodotto

        BigDecimal discountRate = BigDecimal.ZERO;

        if (quantity >= maxQuantity * 0.8) { // 
            discountRate = new BigDecimal("0.10"); // 10% di sconto per l'80% della quantità massima
        } else if (quantity >= maxQuantity * 0.5) {
            discountRate = new BigDecimal("0.07"); // 7% di sconto per il 50% della quantità massima
        } else if (quantity >= maxQuantity * 0.2) {
            discountRate = new BigDecimal("0.05"); // 5% di sconto per il 20% della quantità massima
        }

        BigDecimal discountPrice = price.subtract(price.multiply(discountRate)).multiply(new BigDecimal(quantity));
        BigDecimal roundedDiscountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
        return roundedDiscountPrice;
    }
}
