package exceptions;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lançada quando o cadastro viola alguma regra de negócio.
 * Carrega TODOS os erros encontrados (campo -> mensagem), e não só o primeiro.
 */
public class ValidacaoException extends RuntimeException {

    private final Map<String, String> campos;

    public ValidacaoException(Map<String, String> campos) {
        super("Existem erros de validação no cadastro.");
        this.campos = Collections.unmodifiableMap(new LinkedHashMap<>(campos));
    }

    public ValidacaoException(String campo, String mensagem) {
        this(Map.of(campo, mensagem));
    }

    public Map<String, String> getCampos() {
        return campos;
    }
}
