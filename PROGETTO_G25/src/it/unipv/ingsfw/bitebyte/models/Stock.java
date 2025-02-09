
package it.unipv.ingsfw.bitebyte.models;
public class Stock {
    private int idInventario;          // Identificativo univoco dello stock
    private int quantitàDisp;          // Quantità disponibile
    private int qMaxInseribile;       // Quantità massima inseribile
    private int prodottoId;        // ID del prodotto (riferimento al prodotto nella tabella Prodotto)
       

    // Costruttore
    public Stock(int idInventario, int quantitàDisp, int qMaxInseribile, int prodottoId) {
        this.idInventario = idInventario;
        this.quantitàDisp = quantitàDisp;
        this.qMaxInseribile = qMaxInseribile;
        this.prodottoId = prodottoId;
    
    }

    // Getter e Setter per gli attributi
    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getQuantitàDisp() {
        return quantitàDisp;
    }

    public void setQuantitàDisp(int quantitàDisp) {
        this.quantitàDisp = quantitàDisp;
    }

    public int getqMaxInseribile() {
        return qMaxInseribile;
    }

    public void setqMaxInseribile(int qMaxInseribile) {
        this.qMaxInseribile = qMaxInseribile;
    }

    public int getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(int prodottoId) {
        this.prodottoId = prodottoId;
    }


}
