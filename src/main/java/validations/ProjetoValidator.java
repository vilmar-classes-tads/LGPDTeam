package validations;

import exceptions.ValidacaoException;
import models.entities.Projeto;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Responsabilidade única (SRP): validação de regras de negócio de Projeto.
 * Permite testagem independente sem acoplamento a serviços ou entidades.
 */
@Component
public class ProjetoValidator {

    public static final String STATUS_RASCUNHO = "Rascunho";
    public static final String STATUS_EM_CORRECAO = "Em Correção";

    /**
     * Valida se o status informado permite a edição do projeto.
     * Retorna um mapa com erros ou vazio se estiver válido.
     */
    public Map<String, String> validarEdicao(Projeto projeto) {
        Map<String, String> erros = new LinkedHashMap<>();

        if (projeto == null) {
            erros.put("projeto", "Projeto não pode ser nulo.");
            return erros;
        }

        if (!isStatusPermiteEdicao(projeto.getStatus())) {
            erros.put("status", "Não é permitido editar projetos com status diferente de 'Rascunho' ou 'Em Correção'. Status atual: " + projeto.getStatus());
        }

        return erros;
    }

    /**
     * Verifica se uma string de status permite edição ("Rascunho" ou "Em Correção").
     */
    public boolean isStatusPermiteEdicao(String status) {
        if (status == null) {
            return false;
        }
        String s = status.trim();
        return s.equalsIgnoreCase(STATUS_RASCUNHO)
                || s.equalsIgnoreCase(STATUS_EM_CORRECAO)
                || s.equalsIgnoreCase("Em Correcao");
    }

    /**
     * Lança ValidacaoException caso o projeto não esteja em estado que permita edição.
     */
    public void validarPermissaoEdicao(Projeto projeto) {
        Map<String, String> erros = validarEdicao(projeto);
        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }
    }
}
