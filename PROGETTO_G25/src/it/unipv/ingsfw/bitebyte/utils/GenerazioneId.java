package it.unipv.ingsfw.bitebyte.utils;

import java.util.Random;

public class GenerazioneId {

    // Metodo statico per generare un ID casuale di 3 caratteri
    public static String generaIdCasuale() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        Random rand = new Random();

        // Genera un ID di 3 caratteri casuali
        for (int i = 0; i < 3; i++) {
            id.append(caratteri.charAt(rand.nextInt(caratteri.length())));
        }

        return id.toString();
    }
}
