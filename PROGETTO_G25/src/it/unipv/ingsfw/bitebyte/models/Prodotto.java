package it.unipv.ingsfw.bitebyte.models;
import java.math.BigDecimal;

import it.unipv.ingsfw.bitebyte.types.Categoria;


/**
 * La classe Prodotto rappresenta un prodotto disponibile nel sistema BiteByte.
 * Ogni prodotto ha un identificativo univoco, un nome, un prezzo e una categoria.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 * @version 1.0
 */
public class Prodotto {
    
    // ATTRIBUTI

    private String idProdotto;
    private String nome;
    private BigDecimal prezzo;
    private Categoria categoria;
    
    
    /**
     * Costruttore parametrizzato per creare un nuovo prodotto.
     * 
     * @param idProdotto Identificativo univoco del prodotto.
     * @param nome Nome del prodotto.
     * @param prezzo Prezzo del prodotto.
     * @param categoria Categoria del prodotto.
     */
    public Prodotto(String idProdotto, String nome, BigDecimal prezzo, Categoria categoria) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
    }

    /**
     * Restituisce l'identificativo del prodotto.
     * 
     * @return ID del prodotto.
     */
    public String getIdProdotto() {
        return idProdotto;
    }

    /**
     * Imposta l'identificativo del prodotto.
     * 
     * @param idProdotto Nuovo ID del prodotto.
     */
    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    /**
     * Restituisce il nome del prodotto.
     * 
     * @return Nome del prodotto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del prodotto.
     * 
     * @param nome Nuovo nome del prodotto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il prezzo del prodotto.
     * 
     * @return Prezzo del prodotto.
     */
    public BigDecimal getPrezzo() {
        return prezzo;
    }

    /**
     * Imposta il prezzo del prodotto.
     * 
     * @param prezzo Nuovo prezzo del prodotto.
     */
    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Restituisce la categoria del prodotto.
     * 
     * @return Categoria del prodotto.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Imposta la categoria del prodotto.
     * 
     * @param categoria Nuova categoria del prodotto.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}


