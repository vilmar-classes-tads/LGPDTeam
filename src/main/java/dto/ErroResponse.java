package dto;

import java.util.LinkedHashMap;
import java.util.Map;

public record ErroResponse(
        boolean sucesso,
        String mensagem,
        Map<String, String> campos,
        Map<String, String> dadosPreenchidos
) {
    public ErroResponse(String mensagem) {
        this(false, mensagem, Map.of(), Map.of());
    }

    public ErroResponse(String mensagem, Map<String, String> campos) {
        this(false, mensagem, campos, Map.of());
    }

    public ErroResponse(String mensagem, Map<String, String> campos, CadastroServidorRequest req) {
        this(false, mensagem, campos, extrairDadosPreenchidos(req));
    }

    public static Map<String, String> extrairDadosPreenchidos(CadastroServidorRequest req) {
        if (req == null) {
            return Map.of();
        }
        Map<String, String> dados = new LinkedHashMap<>();
        dados.put("nomeCompleto", valorOuVazio(req.nomeCompleto()));
        dados.put("cpf", valorOuVazio(req.cpf()));
        dados.put("email", valorOuVazio(req.email()));
        dados.put("campus", valorOuVazio(req.campus()));
        dados.put("areaFormacao", valorOuVazio(req.areaFormacao()));
        dados.put("titulacao", valorOuVazio(req.titulacao()));
        dados.put("nomeSocial", valorOuVazio(req.nomeSocial()));
        dados.put("sexo", valorOuVazio(req.sexo()));
        dados.put("lattes", valorOuVazio(req.lattes()));
        dados.put("telefone", valorOuVazio(req.telefone()));
        return Map.copyOf(dados);
    }

    private static String valorOuVazio(String valor) {
        return valor == null ? "" : valor;
    }
}
