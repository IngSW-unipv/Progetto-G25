/**
 * La classe {@code CalcolaDistanza} fornisce un metodo statico per calcolare la distanza
 * tra due distributori sulla superficie terrestre, utilizzando la formula di Haversine.
 *  
 * @author Anna
 */
package it.unipv.ingsfw.bitebyte.utils;

import it.unipv.ingsfw.bitebyte.models.Distributore;


public class CalcolaDistanza {

    private static final double EARTH_RADIUS_KM = 6371.0; // Raggio della Terra in km

    //Converte le latitudini e longitudini dei due distributori da gradi a radianti.
    //La formula di Haversine (e le funzioni trigonometriche di Java) lavorano con i radianti, non con i gradi.
    public static double calcolaDistanza(Distributore d1, Distributore d2) {
        double lat1 = Math.toRadians(d1.getLat());
        double lon1 = Math.toRadians(d1.getLon());
        double lat2 = Math.toRadians(d2.getLat());
        double lon2 = Math.toRadians(d2.getLon());
   //Calcola la differenza in radianti tra le latitudini (dLat) e le longitudini (dLon) dei due punti.
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
   //Calcola il valore "a" secondo la formula di Haversine.
   // a = sin^2(dLat/2) + cos(lat1)*cos(lat2)*sin^2(dLon/2)
   //Questo valore "a" è una misura della variazione angolare tra i due punti sulla superficie di una sfera.
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.pow(Math.sin(dLon / 2), 2);
        
    //Calcola l'angolo centrale "c" (in radianti) tra i due punti.
    // c = 2*atan2(sqrt(a), sqrt(1-a))
    //Math.atan2(y, x) restituisce l'arcotangente di y/x, 
    //tenendo conto del quadrante corretto. Moltiplicando per 2, otteniamo l'angolo al centro della Terra tra i due punti.
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    //Moltiplica l'angolo centrale (c) per il raggio della Terra, definito dalla costante EARTH_RADIUS_KM (6371 km),
    //ottenendo così la distanza lungo la superficie terrestre
        return EARTH_RADIUS_KM * c; // Distanza in km
    }
}
