package it.unipv.ingsfw.bitebyte.models;

/**
 * La classe {@code Stock} rappresenta una voce di inventario per un prodotto,
 * contenente informazioni sulla disponibilità del prodotto, la quantità massima
 * inseribile nell'inventario, lo stato dell'inventario e il prodotto associato.
 */
public class Stock {

    
    private int idInventario;
    private int quantitaDisp;
    private int qMaxInseribile;
    private String stato;
    private Prodotto prodotto;

    /**
     * Costruisce un nuovo oggetto {@code Stock} con i parametri specificati.
     *
     * @param idInventario L'id univoco dell'inventario.
     * @param quantitaDisp La quantità disponibile del prodotto nell'inventario.
     * @param qMaxInseribile La quantità massima che può essere inserita nell'inventario.
     * @param stato Lo stato dell'inventario del prodotto.
     * @param prodotto Il prodotto associato all'inventario.
     */
    public Stock(int idInventario, int quantitaDisp, int qMaxInseribile, String stato, Prodotto prodotto) {
        this.idInventario = idInventario;
        this.quantitaDisp = quantitaDisp;
        this.qMaxInseribile = qMaxInseribile;
        this.stato = stato;
        this.prodotto = prodotto;
    }

    /**
     * Restituisce l'id dell'inventario.
     *
     * @return L'id dell'inventario.
     */
    public int getIdInventario() {
        return idInventario;
    }

    /**
     * Imposta l'id dell'inventario.
     *
     * @param idInventario Il nuovo id dell'inventario.
     */
    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    /**
     * Restituisce la quantità disponibile del prodotto nell'inventario.
     *
     * @return La quantità disponibile del prodotto.
     */
    public int getQuantitaDisp() {
        return quantitaDisp;
    }

    /**
     * Imposta la quantità disponibile del prodotto nell'inventario.
     *
     * @param quantitaDisp La nuova quantità disponibile.
     */
    public void setQuantitaDisp(int quantitaDisp) {
        this.quantitaDisp = quantitaDisp;
    }

    /**
     * Restituisce la quantità massima che può essere inserita nell'inventario per il prodotto.
     *
     * @return La quantità massima inseribile.
     */
    public int getQMaxInseribile() {
        return qMaxInseribile;
    }

    /**
     * Imposta la quantità massima che può essere inserita nell'inventario per il prodotto.
     *
     * @param qMaxInseribile La nuova quantità massima inseribile.
     */
    public void setQMaxInseribile(int qMaxInseribile) {
        this.qMaxInseribile = qMaxInseribile;
    }

    /**
     * Restituisce lo stato dell'inventario del prodotto.
     *
     * @return Lo stato dell'inventario.
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta lo stato dell'inventario del prodotto.
     *
     * @param stato Il nuovo stato dell'inventario.
     */
    public void setStato(String stato) {
        this.stato = stato;
    }

    /**
     * Restituisce il prodotto associato all'inventario.
     *
     * @return Il prodotto associato all'inventario.
     */
    public Prodotto getProdotto() {
        return prodotto;
    }

    /**
     * Imposta il prodotto associato all'inventario.
     *
     * @param prodotto Il nuovo prodotto associato.
     */
    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto {@code Stock}.
     *
     * @return Una stringa che rappresenta lo stato dell'oggetto {@code Stock}.
     */
    @Override
    public String toString() {
        return "Stock{" +
               "idInventario=" + idInventario +
               ", quantitaDisp=" + quantitaDisp +
               ", qMaxInseribile=" + qMaxInseribile +
               ", stato=" + stato +
               ", prodotto=" + prodotto +
               '}';
    }
}
