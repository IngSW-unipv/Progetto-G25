package it.unipv.ingsfw.bitebyte.utils;

import it.unipv.ingsfw.bitebyte.models.Distributore;

public class CalcolaDistanza {

    private static final double EARTH_RADIUS_KM = 6371.0; // Raggio della Terra in km

    public static double calcolaDistanza(Distributore d1, Distributore d2) {
        double lat1 = Math.toRadians(d1.getLat());
        double lon1 = Math.toRadians(d1.getLon());
        double lat2 = Math.toRadians(d2.getLat());
        double lon2 = Math.toRadians(d2.getLon());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c; // Distanza in km
    }
}
