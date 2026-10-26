package validations;

import models.entities.Servidor;

import java.util.List;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static String normalizar(String email) {
        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase();
    }

    public static boolean isValid(String email) {
        String emailLimpo = normalizar(email);
        return !emailLimpo.isEmpty() && EMAIL_PATTERN.matcher(emailLimpo).matches();
    }

    public static boolean isValid(String email, List<Servidor> server) {
        return isValid(email);
    }

    public static boolean isUnique(String email, List<Servidor> server) {
        String emailLimpo = normalizar(email);
        for (Servidor s : server) {
            if (s.getEmail() != null && normalizar(s.getEmail()).equals(emailLimpo)) {
                return false;
            }
        }
        return true;
    }
}
