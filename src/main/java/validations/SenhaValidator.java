package validations;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class SenhaValidator {

    public static final int TAMANHO_MINIMO = 6;

    public static boolean isValid(String senha) {
        return senha != null && !senha.isBlank() && senha.length() >= TAMANHO_MINIMO;
    }

    public static String gerarHash(String senha) {
        if (senha == null) {
            throw new IllegalArgumentException("A senha não pode ser nula.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 não disponível.", e);
        }
    }

    public static boolean verificarSenha(String senhaTextoPuro, String senhaHash) {
        if (senhaTextoPuro == null || senhaHash == null) {
            return false;
        }
        return gerarHash(senhaTextoPuro).equals(senhaHash);
    }
}
