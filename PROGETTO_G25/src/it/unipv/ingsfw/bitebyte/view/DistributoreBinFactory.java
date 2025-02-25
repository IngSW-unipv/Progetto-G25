package it.unipv.ingsfw.bitebyte.view;

import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;
import it.unipv.ingsfw.bitebyte.view.DistributoreBin;
import javafx.scene.control.Button;
import java.util.function.Consumer;

public class DistributoreBinFactory {

    /**
     * Crea un DistributoreBin configurato per il distributore passato.
     *
     * @param d Il distributore per il quale creare la view
     * @param current Il distributore corrente (usato per calcolare la distanza)
     * @param searchQuery Testo di ricerca (se necessario)
     * @param onVai Callback per l'azione "Vai" (ad es. aprire Google Maps)
     * @param onVisualizzaProdotto Callback per l'azione "Visualizza prodotto"
     * @return Un oggetto DistributoreBin contenente i dati e i bottoni configurati
     */
    public static DistributoreBin createDistributoreBin(Distributore d, Distributore current, String searchQuery,
                                                          Consumer<Distributore> onVai,
                                                          Consumer<Distributore> onVisualizzaProdotto) {
        double distanza = CalcolaDistanza.calcolaDistanza(current, d);

        // Creazione del bottone "Vai"
        Button btnVai = new Button("Vai");
        btnVai.getStyleClass().add("action-button"); // classe CSS
        btnVai.setOnAction(e -> onVai.accept(d));

        // Creazione del bottone "Visualizza prodotto"
        Button btnVisualizzaProdotto = new Button("Visualizza prodotto");
        btnVisualizzaProdotto.getStyleClass().add("action-button");
        btnVisualizzaProdotto.setOnAction(e -> onVisualizzaProdotto.accept(d));

        // Restituisce il modello DistributoreBin (che rappresenta una riga nella tabella)
        return new DistributoreBin(
                String.valueOf(d.getIdDistr()),
                d.getVia() + " " + d.getNCivico(),
                distanza,
                btnVai,
                btnVisualizzaProdotto
        );
    }
}
