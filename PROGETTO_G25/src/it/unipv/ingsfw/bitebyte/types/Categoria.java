package it.unipv.ingsfw.bitebyte.types;

/**
 * Enum  Categoria rappresenta le diverse categorie di prodotti disponibili.
 * 
 * @author Alessandro, Alice, Annamaria, Davide
 * @version 1.0
 */
public enum Categoria {
  
    BEVANDA_CALDA, BEVANDA_FREDDA, SNACK_SALATO, SNACK_DOLCE;



public static Categoria fromDatabaseValue(String dbValue) {
    switch (dbValue.toLowerCase()) {
        case "bevanda calda":
            return BEVANDA_CALDA;
        case "bevanda fredda":
            return BEVANDA_FREDDA;
        case "snack dolce":
            return SNACK_DOLCE;
        case "snack salato":
            return SNACK_SALATO;
        default:
            throw new IllegalArgumentException("Unknown category: " + dbValue);
    }
}
    
}