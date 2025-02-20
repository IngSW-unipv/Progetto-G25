package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.models.Stock;

public interface IDiscountStrategy {
    BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock);
}