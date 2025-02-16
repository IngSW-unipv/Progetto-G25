
package it.unipv.ingsfw.bitebyte.models;
public class Stock {
    private int idInventario;
    private int quantitaDisp;
    private int qMaxInseribile;
    private String stato;
    private Prodotto prodotto;

    public Stock(int idInventario, int quantitaDisp, int qMaxInseribile,  String stato, Prodotto prodotto) {
        this.idInventario = idInventario;
        this.quantitaDisp = quantitaDisp;
        this.qMaxInseribile = qMaxInseribile;
        this.stato = stato;
        this.prodotto = prodotto;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getQuantitaDisp() {
        return quantitaDisp;
    }

    public void setQuantitaDisp(int quantitaDisp) {
        this.quantitaDisp = quantitaDisp;
    }

    public int getQMaxInseribile() {
        return qMaxInseribile;
    }

    public void setQMaxInseribile(int qMaxInseribile) {
        this.qMaxInseribile = qMaxInseribile;
    }
    
    
  
    public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    
    
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