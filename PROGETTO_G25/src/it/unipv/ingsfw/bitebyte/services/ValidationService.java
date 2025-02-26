package it.unipv.ingsfw.bitebyte.services;

import it.unipv.ingsfw.bitebyte.dao.ClienteDAO;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationService {

    private static final String EMAIL_SUFFIX = "@universitadipavia.it";
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$");

    public static boolean emailFormatoValido(String email) {
        return email != null && email.endsWith(EMAIL_SUFFIX);
    }

    public static boolean passwordValida(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static String controlloCampi(String cf, String nomeUtente, String email, String password, 
                                        String confirmPassword, String nome, String cognome, 
                                        LocalDate dataNascita, ClienteDAO clienteDAO) {
        if (cf.isEmpty() || nomeUtente.isEmpty() || email.isEmpty() || password.isEmpty() ||
            confirmPassword.isEmpty() || nome.isEmpty() || cognome.isEmpty() || dataNascita == null) {
            return "Tutti i campi devono essere compilati correttamente.";
        }

        if (!password.equals(confirmPassword)) {
            return "Le password non coincidono.";
        }

        if (!emailFormatoValido(email)) {
            return "L'email deve terminare con @universitadipavia.it.";
        }

        if (!passwordValida(password)) {
            return "La password deve avere almeno 8 caratteri, una lettera maiuscola, un numero e un carattere speciale.";
        }

        if (clienteDAO.esisteUsername(nomeUtente)) {
            return "Username già esistente.";
        }

        if (clienteDAO.esisteCliente(email)) {
            return "Email già registrata.";
        }

        return null; // Nessun errore, tutto OK
    }
}
