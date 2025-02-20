package it.unipv.ingsfw.bitebyte.models;

public class Fornitore {
	private int idFornitore;
	private String nomeF;
	private String cittaF;
	private String viaF;
	private String nCivicoF;
	
	public Fornitore(int idFornitore, String nomeF, String cittaF, String viaF, String nCivicoF) {
		super();
		this.idFornitore = idFornitore;
		this.nomeF = nomeF;
		this.cittaF = cittaF;
		this.viaF = viaF;
		this.nCivicoF = nCivicoF;
	}
	
	public int getIdFornitore() {
		return idFornitore;
	}
	
	public String getNomeF() {
		return nomeF;
	}
	
	public String getCittaF() {
		return cittaF;
	}
	
	public String getViaF() {
		return viaF;
	}
	
	public String getnCivicoF() {
		return nCivicoF;
	}
	
	public void setIdFornitore(int idFornitore) {
		this.idFornitore = idFornitore;
	}
	
	public void setNomeF(String nomeF) {
		this.nomeF = nomeF;
	}
	
	public void setCittaF(String cittaF) {
		this.cittaF = cittaF;
	}
	
	public void setViaF(String viaF) {
		this.viaF = viaF;
	}
	
	public void setnCivicoF(String nCivicoF) {
		this.nCivicoF = nCivicoF;
	}
	
	

}
