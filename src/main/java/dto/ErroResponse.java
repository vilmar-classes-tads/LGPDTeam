package dto;

import java.util.Map;

public record ErroResponse(
        boolean sucesso,
        String mensagem,
        Map<String, String> campos
) {
    public ErroResponse(String mensagem) {
        this(false, mensagem, Map.of());
    }
    public ErroResponse(String mensagem, Map<String, String> campos) {
        this(false, mensagem, campos);
    }
}