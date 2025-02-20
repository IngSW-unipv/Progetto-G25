package it.unipv.ingsfw.bitebyte.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrello {
    private static Carrello instance;
    private List<ItemOrdine> items;

    private Carrello() {
        items = new ArrayList<>();
    }

    // Singleton per ottenere l'unica istanza del carrello
    public static Carrello getInstance() {
        if (instance == null) {
            instance = new Carrello();
        }
        return instance;
    }

    // Aggiungi un prodotto al carrello (sommando la quantità se il prodotto è già presente)
    public void aggiungiItem(Fornitura fornitura, int quantita, BigDecimal prezzoTotale) {
        boolean prodottoEsistente = false;
        
        // Verifica se il prodotto è già presente nel carrello
        for (ItemOrdine item : items) {
            if (item.getFornitura().getProdotto().getIdProdotto() == fornitura.getProdotto().getIdProdotto() && item.getFornitura().getFornitore() == fornitura.getFornitore()) {
                // Se il prodotto è già presente, aggiorna la quantità e il prezzo totale
                item.incrementaQuantita(quantita);
                prodottoEsistente = true;
                break;
            }
        }

        // Se il prodotto non è nel carrello, aggiungilo come nuovo
        if (!prodottoEsistente) {
            items.add(new ItemOrdine(fornitura, quantita, prezzoTotale));
        }
    }
    
    public void rimuoviItem(ItemOrdine item) {
        // Rimuove il primo elemento trovato che corrisponde all'item passato
        Iterator<ItemOrdine> iterator = items.iterator();
        while (iterator.hasNext()) {
            ItemOrdine currentItem = iterator.next();
            if (currentItem.equals(item)) {
                iterator.remove(); // Rimuove l'item
                break; // Uscita dal ciclo dopo aver rimosso l'item
            }
        }
    }

    // Restituisci gli articoli nel carrello
    public List<ItemOrdine> getItems() {
        return items;
    }

    // Calcola il totale del carrello
    public BigDecimal calcolaTotale() {
        BigDecimal totale = BigDecimal.ZERO;
        for (ItemOrdine item : items) {
            totale = totale.add(item.getPrezzoTotale()); // Somma il prezzo totale di ogni ordine
        }
        return totale;
    }

    // Svuota il carrello
    public void svuota() {
        items.clear();
    }
}
