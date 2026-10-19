package validations;

import models.entities.Servidor;

import java.util.List;

public class CpfValidator {
    public static boolean isValid(String cpf, List<Servidor> server) {
        return cpf.length() == 11 && !cpf.contains(".") && !cpf.contains("-") && !cpf.contains(" ");
    }

    public static boolean isUnique(String cpf, List<Servidor> server) {
        for (Servidor s : server) {
            if (s.getCpf().equals(cpf)){
                return false;
            }
        }
        return true;
    }
}
