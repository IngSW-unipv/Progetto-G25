package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import it.unipv.ingsfw.bitebyte.models.Stock;

/**
 * Strategia di sconto basata sulla quantità massima inseribile nel magazzino.
 * Se la quantità acquistata di un prodotto è uguale alla quantità massima inseribile nello stock, 
 * viene applicato uno sconto del 10% sul prezzo totale.
 */
public class MaxQuantityDiscount implements IDiscountStrategy {

    /**
     * Applica uno sconto del 10% sul prezzo totale di un prodotto se la quantità acquistata
     * è uguale alla quantità massima inseribile nello stock.
     * 
     * @param price Il prezzo unitario del prodotto.
     * @param quantity La quantità acquistata del prodotto.
     * @param stock Le informazioni relative allo stock del prodotto.
     * @return Il prezzo totale scontato se la quantità acquistata è uguale alla quantità massima inseribile, 
     *         altrimenti il prezzo originale senza sconto.
     */
    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock) {
        // Verifica se la quantità acquistata è uguale alla quantità massima inseribile nello stock
        if (quantity == stock.getQMaxInseribile()) {
            // Calcola il prezzo scontato (15% di sconto)
            BigDecimal discountPrice = (price.multiply(new BigDecimal("0.85"))).multiply(new BigDecimal(quantity));
            // Arrotonda il prezzo scontato a due decimali
            BigDecimal roundedDiscountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
            return roundedDiscountPrice;
        }
        // Se la quantità non è uguale alla quantità massima, restituisce il prezzo originale
        return price;
    }
}
