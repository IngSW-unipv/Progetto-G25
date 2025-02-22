package it.unipv.ingsfw.bitebyte.test;

public class PayPalService {
    public boolean processaPagamento(double amount) {
        System.out.println("Ricarica di " + amount + "€ effettuata tramite PayPal.");
        return true;
    }

}
