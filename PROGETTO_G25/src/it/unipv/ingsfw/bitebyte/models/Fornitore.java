package it.unipv.ingsfw.bitebyte.models;

/**
 * Classe che rappresenta un fornitore. Ogni fornitore è identificato da un ID unico e ha informazioni
 * relative al nome, alla città, alla via e al numero civico.
 */
public class Fornitore {
    private int idFornitore; 
    private String nomeF;    
    private String cittaF;   
    private String viaF;     
    private String nCivicoF; 

    /**
     * Costruttore per creare un oggetto Fornitore con le informazioni specificate.
     * 
     * @param idFornitore L'ID univoco del fornitore.
     * @param nomeF Il nome del fornitore.
     * @param cittaF La città in cui si trova il fornitore.
     * @param viaF La via in cui si trova il fornitore.
     * @param nCivicoF Il numero civico dell'indirizzo del fornitore.
     */
    public Fornitore(int idFornitore, String nomeF, String cittaF, String viaF, String nCivicoF) {
        this.idFornitore = idFornitore;
        this.nomeF = nomeF;
        this.cittaF = cittaF;
        this.viaF = viaF;
        this.nCivicoF = nCivicoF;
    }

    /**
     * Restituisce l'ID univoco del fornitore.
     * 
     * @return L'ID del fornitore.
     */
    public int getIdFornitore() {
        return idFornitore;
    }

    /**
     * Restituisce il nome del fornitore.
     * 
     * @return Il nome del fornitore.
     */
    public String getNomeF() {
        return nomeF;
    }

    /**
     * Restituisce la città in cui si trova il fornitore.
     * 
     * @return La città del fornitore.
     */
    public String getCittaF() {
        return cittaF;
    }

    /**
     * Restituisce la via in cui si trova il fornitore.
     * 
     * @return La via del fornitore.
     */
    public String getViaF() {
        return viaF;
    }

    /**
     * Restituisce il numero civico dell'indirizzo del fornitore.
     * 
     * @return Il numero civico del fornitore.
     */
    public String getnCivicoF() {
        return nCivicoF;
    }

    /**
     * Imposta un nuovo ID per il fornitore.
     * 
     * @param idFornitore Il nuovo ID del fornitore.
     */
    public void setIdFornitore(int idFornitore) {
        this.idFornitore = idFornitore;
    }

    /**
     * Imposta un nuovo nome per il fornitore.
     * 
     * @param nomeF Il nuovo nome del fornitore.
     */
    public void setNomeF(String nomeF) {
        this.nomeF = nomeF;
    }

    /**
     * Imposta una nuova città per il fornitore.
     * 
     * @param cittaF La nuova città del fornitore.
     */
    public void setCittaF(String cittaF) {
        this.cittaF = cittaF;
    }

    /**
     * Imposta una nuova via per il fornitore.
     * 
     * @param viaF La nuova via del fornitore.
     */
    public void setViaF(String viaF) {
        this.viaF = viaF;
    }

    /**
     * Imposta un nuovo numero civico per il fornitore.
     * 
     * @param nCivicoF Il nuovo numero civico del fornitore.
     */
    public void setnCivicoF(String nCivicoF) {
        this.nCivicoF = nCivicoF;
    }
}
