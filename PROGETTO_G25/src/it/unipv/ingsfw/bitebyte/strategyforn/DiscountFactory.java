package it.unipv.ingsfw.bitebyte.strategyforn;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class DiscountFactory {
    
    private static final String PROPERTIES_PATH = "properties/discountfactory.properties"; //  Percorso file

    public static IDiscountStrategy getDiscountStrategy(String strategyKey) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(input);
            String className = properties.getProperty(strategyKey);

            if (className == null || className.isEmpty()) {
                throw new IllegalArgumentException("Nessuna strategia trovata per la chiave: " + strategyKey);
            }

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

