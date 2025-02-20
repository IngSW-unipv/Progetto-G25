package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;

public class Fornitura {
	private Prodotto prodotto;
	private Fornitore fornitore;
	private BigDecimal ppu;
	
	public Fornitura(Prodotto prodotto, Fornitore fornitore, BigDecimal ppu) {
		super();
		this.prodotto = prodotto;
		this.fornitore = fornitore;
		this.ppu = ppu;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}
	
	public Fornitore getFornitore() {
		return fornitore;
	}
	
	public BigDecimal getPpu() {
		return ppu;
	}
	
	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}
	
	public void setFornitore(Fornitore fornitore) {
		this.fornitore = fornitore;
	}
	
	public void setPpu(BigDecimal ppu) {
		this.ppu = ppu;
	}
	
	

}
