package validations;

import models.entities.Servidor;

import java.util.List;

public class CpfValidator {

    public static String normalizar(String cpf) {
        if (cpf == null) {
            return "";
        }
        return cpf.replaceAll("[.\\-\\s]", "");
    }

    public static boolean isValid(String cpf) {
        String cpfLimpo = normalizar(cpf);
        return cpfLimpo.matches("\\d{11}");
    }

    public static boolean isValid(String cpf, List<Servidor> server) {
        return isValid(cpf);
    }

    public static boolean isUnique(String cpf, List<Servidor> server) {
        String cpfLimpo = normalizar(cpf);
        for (Servidor s : server) {
            if (s.getCpf() != null && normalizar(s.getCpf()).equals(cpfLimpo)) {
                return false;
            }
        }
        return true;
    }
}
