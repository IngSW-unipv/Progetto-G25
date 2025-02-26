package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * La classe {@code Spedizione} rappresenta una spedizione di un prodotto.
 * Contiene le informazioni relative a un ordine effettuato da un amministratore del sistema, 
 * tra cui l'id della spedizione, l'id del prodotto,
 * la quantità ordinata, il prezzo totale e la data della spedizione.
 */

public class Spedizione {

	private String idSpedizione;
	private int idProdotto;
	private int qOrd;
	private BigDecimal prezzoTot;
	private Date dataSp;

	/**
	 * Costruisce una nuova spedizione con i parametri specificati.
	 *
	 * @param idSpedizione L'id univoco della spedizione.
	 * @param idProdotto L'id del prodotto associato alla spedizione.
	 * @param qOrd La quantità ordinata del prodotto.
	 * @param prezzoTot Il prezzo totale della spedizione.
	 * @param dataSp La data della spedizione.
	 */
	public Spedizione(String idSpedizione, int idProdotto, int qOrd, BigDecimal prezzoTot, Date dataSp) {
		super();
		this.idSpedizione = idSpedizione;
		this.idProdotto = idProdotto;
		this.qOrd = qOrd;
		this.prezzoTot = prezzoTot;
		this.dataSp = dataSp;
	}

	/**
	 * Restituisce la data della spedizione.
	 *
	 * @return La data della spedizione.
	 */
	public Date getDataSp() {
		return dataSp;
	}

	/**
	 * Imposta la data della spedizione.
	 *
	 * @param dataSp La nuova data della spedizione.
	 */
	public void setDataSp(Date dataSp) {
		this.dataSp = dataSp;
	}

	/**
	 * Restituisce l'id della spedizione.
	 *
	 * @return L'id della spedizione.
	 */
	public String getIdSpedizione() {
		return idSpedizione;
	}

	/**
	 * Restituisce l'id del prodotto associato alla spedizione.
	 *
	 * @return L'id del prodotto.
	 */
	public int getIdProdotto() {
		return idProdotto;
	}

	/**
	 * Restituisce la quantità ordinata del prodotto.
	 *
	 * @return La quantità ordinata.
	 */
	public int getqOrd() {
		return qOrd;
	}

	/**
	 * Restituisce il prezzo totale della spedizione.
	 *
	 * @return Il prezzo totale.
	 */
	public BigDecimal getPrezzoTot() {
		return prezzoTot;
	}

	/**
	 * Imposta l'id della spedizione.
	 *
	 * @param idSpedizione Il nuovo id della spedizione.
	 */
	public void setIdSpedizione(String idSpedizione) {
		this.idSpedizione = idSpedizione;
	}

	/**
	 * Imposta l'id del prodotto associato alla spedizione.
	 *
	 * @param idProdotto Il nuovo id del prodotto.
	 */
	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	/**
	 * Imposta la quantità ordinata del prodotto.
	 *
	 * @param qOrd La nuova quantità ordinata.
	 */
	public void setqOrd(int qOrd) {
		this.qOrd = qOrd;
	}

	/**
	 * Imposta il prezzo totale della spedizione.
	 *
	 * @param prezzoTot Il nuovo prezzo totale.
	 */
	public void setPrezzoTot(BigDecimal prezzoTot) {
		this.prezzoTot = prezzoTot;
	}
}
