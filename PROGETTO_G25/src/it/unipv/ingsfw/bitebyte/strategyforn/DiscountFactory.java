package it.unipv.ingsfw.bitebyte.strategyforn;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * Classe Factory per la creazione dinamica di strategie di sconto.
 * Utilizza un file di configurazione per determinare quale classe di strategia di sconto caricare e restituire.
 */
public class DiscountFactory {
    
    /**
     * Percorso del file di configurazione delle strategie di sconto.
     * Il file contiene le mappature tra le chiavi e le classi delle strategie di sconto.
     */
    private static final String PROPERTIES_PATH = "properties/discountfactory.properties";

    /**
     * Ottiene un'istanza della strategia di sconto corrispondente alla chiave specificata.
     * Il metodo carica dinamicamente la classe della strategia di sconto dal file di configurazione
     * e restituisce un'istanza di quella strategia.
     *
     * @param strategyKey La chiave della strategia di sconto da caricare.
     * @return Un'istanza di {@link IDiscountStrategy} corrispondente alla chiave, o null in caso di errore.
     * @throws IllegalArgumentException Se la chiave non corrisponde a nessuna strategia definita nel file di configurazione.
     */
    public static IDiscountStrategy getDiscountStrategy(String strategyKey) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_PATH)) {
            // Carica il file di configurazione
            properties.load(input);
            // Ottiene il nome della classe associata alla strategia di sconto dalla configurazione
            String className = properties.getProperty(strategyKey);
            if (className == null || className.isEmpty()) {
                throw new IllegalArgumentException("Nessuna strategia trovata per la chiave: " + strategyKey);
            }
            // Crea un'istanza della strategia di sconto tramite riflessione
            Constructor<?> constructor = Class.forName(className).getConstructor();
            return (IDiscountStrategy) constructor.newInstance();
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file di properties: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore nella creazione della strategia di sconto: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Ritorna null in caso di errore
    }
}
