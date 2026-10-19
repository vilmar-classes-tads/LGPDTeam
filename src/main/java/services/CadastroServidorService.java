package services;

import dto.CadastroServidorRequest;
import dto.CadastroServidorResponse;
import exceptions.ValidacaoException;
import models.entities.Servidor;
import models.enums.Titulacao;
import org.springframework.stereotype.Service;
import repository.ServidorRepository;

import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CadastroServidorService {

    private final ServidorRepository repository;

    public CadastroServidorService(ServidorRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um servidor.
     * @throws ValidacaoException com todos os campos inválidos (campo -> mensagem)
     */
    public CadastroServidorResponse cadastrar(CadastroServidorRequest req) {
        if (req == null) {
            throw new ValidacaoException("request", "Dados de cadastro não informados.");
        }

        Map<String, String> erros = new LinkedHashMap<>();

        if (vazio(req.nomeCompleto())) erros.put("nomeCompleto", "Nome completo é obrigatório.");
        if (vazio(req.campus()))       erros.put("campus", "Campus é obrigatório.");
        if (vazio(req.areaFormacao())) erros.put("areaFormacao", "Área de formação é obrigatória.");

        if (vazio(req.cpf())) erros.put("cpf", "CPF é obrigatório.");

        if (vazio(req.email())) erros.put("email", "E-mail institucional é obrigatório.");

        if (vazio(req.senha())) erros.put("senha", "Senha é obrigatória.");

        Titulacao titulacao = null;
        if (vazio(req.titulacao())) {
            erros.put("titulacao", "Titulação é obrigatória.");
        } else {
            titulacao = converterEnum(Titulacao.class, req.titulacao());
            if (titulacao == null) erros.put("titulacao", "Titulação inválida.");
        }

        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }

        Servidor s = new Servidor();
        s.setNomeCompleto(req.nomeCompleto().trim());
        s.setCpf(req.cpf().trim());
        s.setEmail(req.email().trim());
        s.setSenhaHash(req.senha());
        s.setCampus(req.campus().trim());
        s.setAreaFormacao(req.areaFormacao().trim());
        s.setTitulacao(titulacao);

        repository.inserir(s);

        return new CadastroServidorResponse(
                s.getNomeCompleto(), s.getCpf(), s.getEmail(), s.getPerfis(), true, "Cadastro realizado.");
    }

    private static boolean vazio(String v) {
        return v == null || v.isBlank();
    }

    private static <E extends Enum<E>> E converterEnum(Class<E> tipo, String valor) {
        String chave = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .replaceAll("[\\s\\-]+", "_");
        try {
            return Enum.valueOf(tipo, chave);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
