package it.unipv.ingsfw.bitebyte.models;

import java.time.LocalDateTime;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;
import java.math.BigDecimal;

public class Ordine {
    
    // ATTRIBUTI 
    private String idOrdine;
    private LocalDateTime dataOrd;
    private StatoOrd statoOrd;
    private BigDecimal totale; 
    private Cliente cliente;

    // COSTRUTTORE PARAMETRIZZATO
    public Ordine(String idOrdine, LocalDateTime dataOrd, StatoOrd statoOrd, BigDecimal totale, Cliente cliente) {
        this.idOrdine = idOrdine;
        this.dataOrd = dataOrd;
        this.statoOrd = statoOrd;
        this.totale = totale;
        this.cliente = cliente;
    }

    // GETTERS AND SETTERS
    public String getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(String idOrdine) {
        this.idOrdine = idOrdine;
    }

    public LocalDateTime getDataOrd() {
        return dataOrd;
    }

    public void setDataOrd(LocalDateTime dataOrd) {
        this.dataOrd = dataOrd;
    }

    public StatoOrd getStatoOrd() {
        return statoOrd;
    }

    public void setStatoOrd(StatoOrd statoOrd) {
        this.statoOrd = statoOrd;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // NUOVO METODO per ottenere il codice fiscale del cliente
    public String getCodiceFiscaleCliente() {
        return cliente != null ? cliente.getCf() : null;
    }
}
