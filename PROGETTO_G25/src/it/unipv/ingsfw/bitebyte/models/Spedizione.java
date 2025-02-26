package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class Spedizione {
	
	private String idSpedizione;
	private int idProdotto;
	private int qOrd;
	private BigDecimal prezzoTot;
	private Date dataSp;
	
	public Spedizione(String idSpedizione, int idProdotto, int qOrd, BigDecimal prezzoTot, Date dataSp) {
		super();
		this.idSpedizione = idSpedizione;
		this.idProdotto = idProdotto;
		this.qOrd = qOrd;
		this.prezzoTot = prezzoTot;
		this.dataSp = dataSp;
	}

	public Date getDataSp() {
		return dataSp;
	}

	public void setDataSp(Date dataSp) {
		this.dataSp = dataSp;
	}

	public String getIdSpedizione() {
		return idSpedizione;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public int getqOrd() {
		return qOrd;
	}

	public BigDecimal getPrezzoTot() {
		return prezzoTot;
	}

	public void setIdSpedizione(String idSpedizione) {
		this.idSpedizione = idSpedizione;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	public void setqOrd(int qOrd) {
		this.qOrd = qOrd;
	}

	public void setPrezzoTot(BigDecimal prezzoTot) {
		this.prezzoTot = prezzoTot;
	}
	
   
	
}
