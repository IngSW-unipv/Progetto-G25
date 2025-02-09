package it.unipv.ingsfw.bitebyte.models;

/**
 * Classe che rappresenta un distributore automatico.
 * Contiene informazioni relative alla sua posizione e tipologia.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 */
public class Distributore {
    
 
    private int idDistr;
    private String tipo;
    private String citta;
    private String via;
    private short nCivico;
    private int idInventario;
    
    /**
     * Costruttore della classe Distributore.
     * 
     * @param idDistr Identificativo univoco del distributore
     * @param tipo Tipologia del distributore
     * @param citta Città in cui si trova il distributore
     * @param via Via in cui si trova il distributore
     * @param nCivico Numero civico del distributore
     * @param idInventario
     */
    public Distributore(int idDistr, String tipo, String citta, String via, short nCivico, int idInventario) {
        this.idDistr = idDistr;
        this.tipo = tipo;
        this.citta = citta;
        this.via = via;
        this.nCivico = nCivico;
        this.idInventario = idInventario;
    }
    
    /**
     * Restituisce l'identificativo univoco del distributore.
     * 
     * @return Identificativo del distributore
     */
    public int getIdDistr() {
        return idDistr;
    }

    /**
     * Imposta l'identificativo univoco del distributore.
     * 
     * @param idDistr Nuovo identificativo del distributore
     */
    public void setIdDistr(int idDistr) {
        this.idDistr = idDistr;
    }

    /**
     * Restituisce la tipologia del distributore.
     * 
     * @return Tipologia del distributore
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Imposta la tipologia del distributore.
     * 
     * @param tipo Nuova tipologia del distributore
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Restituisce la città in cui si trova il distributore.
     * 
     * @return Città del distributore
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Imposta la città in cui si trova il distributore.
     * 
     * @param citta Nuova città del distributore
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     * Restituisce la via in cui si trova il distributore.
     * 
     * @return Via del distributore
     */
    public String getVia() {
        return via;
    }

    /**
     * Imposta la via in cui si trova il distributore.
     * 
     * @param via Nuova via del distributore
     */
    public void setVia(String via) {
        this.via = via;
    }

    /**
     * Restituisce il numero civico del distributore.
     * 
     * @return Numero civico del distributore
     */
    public short getnCivico() {
        return nCivico;
    }

    /**
     * Imposta il numero civico del distributore.
     * 
     * @param nCivico Nuovo numero civico del distributore
     */
    public void setnCivico(short nCivico) {
        this.nCivico = nCivico;
    }

    /**
     * Restituisce l'idInventario del  distributore.
     * 
     * @return IdInventario
     */
	public int getIdInventario() {
		return idInventario;
	}

	 /**
     * Restituisce l'idInventario del  distributore.
     * 
     * @param IdInventario
     */
	public void setIdInventario(int idInventario) {
		this.idInventario = idInventario;
	}
    
    
}

