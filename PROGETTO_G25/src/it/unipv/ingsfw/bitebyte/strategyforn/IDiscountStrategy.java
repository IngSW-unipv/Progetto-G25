package it.unipv.ingsfw.bitebyte.strategyforn;

import java.math.BigDecimal;
import it.unipv.ingsfw.bitebyte.models.Stock;

/**
 * Interfaccia per una strategia di sconto applicabile su un prodotto.
 * Ogni strategia di sconto deve implementare questo metodo per applicare uno sconto
 * in base al prezzo del prodotto, alla quantità e allo stock disponibile.
 */
public interface IDiscountStrategy {

    /**
     * Applica uno sconto al prezzo di un prodotto in base alla quantità e allo stock.
     * 
     * @param price Il prezzo originale del prodotto.
     * @param quantity La quantità del prodotto acquistato.
     * @param stock Le informazioni relative allo stock del prodotto.
     * @return Il prezzo scontato del prodotto dopo l'applicazione della strategia di sconto.
     */
    BigDecimal applyDiscount(BigDecimal price, int quantity, Stock stock);
}
