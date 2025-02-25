package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che rappresenta il carrello degli acquisti. Gestisce gli articoli aggiunti al carrello, 
 * consente di aggiungere, rimuovere e calcolare il totale degli articoli presenti.
 * La classe segue il pattern Singleton per garantire che esista una sola istanza del carrello durante
 * l'intera durata dell'applicazione.
 */
public class Carrello {
    private static Carrello instance;
    private List<ItemCarrello> items;

    /**
     * Costruttore privato per inizializzare il carrello. Inizializza la lista di articoli del carrello.
     */
    private Carrello() {
        items = new ArrayList<>();
    }

    /**
     * Ottiene l'istanza unica del carrello (Singleton).
     * Se l'istanza non è ancora stata creata, viene creata una nuova istanza.
     * 
     * @return L'istanza del carrello.
     */
    public static Carrello getInstance() {
        if (instance == null) {
            instance = new Carrello();
        }
        return instance;
    }

    /**
     * Aggiunge un prodotto al carrello. Se il prodotto è già presente, viene aggiornata la quantità 
     * e il prezzo totale, altrimenti il prodotto viene aggiunto come nuovo articolo nel carrello.
     * 
     * @param fornitura La fornitura associata al prodotto da aggiungere.
     * @param quantita La quantità del prodotto da aggiungere.
     * @param prezzoTotale Il prezzo totale dell'articolo da aggiungere al carrello.
     */
    public void aggiungiItem(Fornitura fornitura, int quantita, BigDecimal prezzoTotale) {
        boolean prodottoEsistente = false;
        // Verifica se il prodotto è già presente nel carrello
        for (ItemCarrello item : items) {
            if (item.getFornitura().getProdotto().getIdProdotto() == fornitura.getProdotto().getIdProdotto() 
                && item.getFornitura().getFornitore().getIdFornitore() == fornitura.getFornitore().getIdFornitore()) {
                // Se il prodotto è già presente, aggiorna la quantità e il prezzo totale
                item.incrementaQuantita(quantita);
                prodottoEsistente = true;
                break;
            }
        }
        // Se il prodotto non è nel carrello, viene aggiunto
        if (!prodottoEsistente) {
            items.add(new ItemCarrello(fornitura, quantita, prezzoTotale));
        }
    }

    /**
     * Rimuove un articolo dal carrello.
     * 
     * @param item L'articolo da rimuovere dal carrello.
     */
    public void rimuoviItem(ItemCarrello item) {
        // Rimuove il primo elemento trovato che corrisponde all'item passato
        Iterator<ItemCarrello> iterator = items.iterator();
        while (iterator.hasNext()) {
            ItemCarrello currentItem = iterator.next();
            if (currentItem.equals(item)) {
                iterator.remove(); 
                break; 
            }
        }
    }

    /**
     * Restituisce la lista degli articoli nel carrello.
     * 
     * @return La lista degli articoli nel carrello.
     */
    public List<ItemCarrello> getItems() {
        return items;
    }

    /**
     * Calcola il totale del carrello sommando il prezzo totale di tutti gli articoli presenti.
     * 
     * @return Il totale del carrello.
     */
    public BigDecimal calcolaTotale() {
        BigDecimal totale = BigDecimal.ZERO;
        for (ItemCarrello item : items) {
            totale = totale.add(item.getPrezzoTotale()); // Somma il prezzo totale di ogni ordine
        }
        return totale;
    }

    /**
     * Svuota il carrello, rimuovendo tutti gli articoli presenti.
     */
    public void svuota() {
        items.clear();
    }
}
