package it.unipv.ingsfw.bitebyte.test;

public class BancomatService {
    public boolean effettuaPagamento(double amount) {
        System.out.println("Ricarica di " + amount + "€ effettuata tramite Bancomat.");
        return true;
    }

}
