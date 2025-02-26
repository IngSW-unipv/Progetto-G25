package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import it.unipv.ingsfw.bitebyte.models.Stock;

/**
 * Strategia di sconto basata sulla quantità acquistata.
 * Viene applicato uno sconto progressivo in base alla quantità acquistata rispetto alla quantità massima inseribile nello stock.
 * 
 * Se la quantità acquistata è:
 * - Almeno l'80% della quantità massima inseribile: sconto del 10%
 * - Almeno il 50% della quantità massima inseribile: sconto del 7%
 * - Almeno il 20% della quantità massima inseribile: sconto del 5%
 */
public class QuantityDiscount implements IDiscountStrategy {

    /**
     * Applica uno sconto in base alla quantità acquistata rispetto alla quantità massima inseribile nello stock.
     * 
     * - 10% di sconto per il 80% o più della quantità massima.
     * - 7% di sconto per il 50% o più della quantità massima.
     * - 5% di sconto per il 20% o più della quantità massima.
     * 
     * Il prezzo finale viene arrotondato a due decimali.
     * 
     * @param price Il prezzo unitario del prodotto.
     * @param quantity La quantità acquistata del prodotto.
     * @param stock L'oggetto `Stock` contenente le informazioni sul prodotto e sulla quantità massima inseribile.
     * @return Il prezzo totale scontato in base alla quantità acquistata.
     */
    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock) {
        // Ottieni la quantità massima inseribile per il prodotto
        int maxQuantity = stock.getQMaxInseribile();

        // Determina il tasso di sconto in base alla quantità acquistata
        BigDecimal discountRate = BigDecimal.ZERO;

        // Se la quantità acquistata è almeno l'80% della quantità massima, applica il 10% di sconto
        if (quantity >= maxQuantity * 0.8) {
            discountRate = new BigDecimal("0.10");
        }
        // Se la quantità acquistata è almeno il 50% della quantità massima, applica il 7% di sconto
        else if (quantity >= maxQuantity * 0.5) {
            discountRate = new BigDecimal("0.07");
        }
        // Se la quantità acquistata è almeno il 20% della quantità massima, applica il 5% di sconto
        else if (quantity >= maxQuantity * 0.2) {
            discountRate = new BigDecimal("0.05");
        }

        // Calcola il prezzo scontato e arrotondalo a due decimali
        BigDecimal discountPrice = price.subtract(price.multiply(discountRate)).multiply(new BigDecimal(quantity));
        BigDecimal roundedDiscountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
        
        return roundedDiscountPrice;
    }
}
