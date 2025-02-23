package it.unipv.ingsfw.bitebyte.models;

import java.time.YearMonth;

// Alice
public class Bancomat {
	private String numCarta;
	private String titolare;
	private YearMonth dataScadenza;
	private String circuito;
	private int codice;

	// Costruttore
	public Bancomat(String numCarta, String titolare, YearMonth dataScadenza, String circuito, int codice) {
		super();
		this.numCarta = numCarta;
		this.titolare = titolare;
		this.dataScadenza = dataScadenza;
		this.circuito = circuito;
		this.codice = codice;
	}

	// Getter and Setter
	public String getNumCarta() {
		return numCarta;
	}

	public void setNumCarta(String numCarta) {
		this.numCarta = numCarta;
	}

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}

	public YearMonth getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(YearMonth dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

}
