package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import java.math.RoundingMode;

import it.unipv.ingsfw.bitebyte.models.Stock;

public class MaxQuantityDiscount implements IDiscountStrategy {
	
    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock) {
        if (quantity == stock.getQMaxInseribile()) {
            BigDecimal discountPrice = (price.multiply(new BigDecimal("0.90"))).multiply(new BigDecimal(quantity)); // 10% di sconto
            BigDecimal roundedDiscountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
            return roundedDiscountPrice;
        }
        return price;
    }
}
