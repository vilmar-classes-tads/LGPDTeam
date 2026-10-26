package services;

import dto.EditalRequest;
import dto.EditalResponse;
import exceptions.ValidacaoException;
import models.entities.Edital;
import org.springframework.stereotype.Service;
import repository.EditalRepository;
import validations.EditalValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EditalService {

    private final EditalRepository repository;

    public EditalService(EditalRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo edital validando campos obrigatórios, unicidade (número/ano) e cronograma de datas.
     */
    public EditalResponse cadastrar(EditalRequest req) {
        Map<String, String> erros = validarCamposBasicosEDatas(req);

        if (req != null && !vazio(req.numero()) && req.ano() != null) {
            if (!EditalValidator.isUnique(req.numero(), req.ano(), repository.readAll())) {
                erros.put("numero", "Já existe um edital cadastrado com este número e ano.");
            }
        }

        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }

        Edital edital = construirEntidade(req);
        repository.inserir(edital);

        return new EditalResponse(
                edital.getTitulo(),
                edital.getNumero(),
                edital.getAno(),
                edital.getDataInicioSubmissao(),
                edital.getDataFimSubmissao(),
                edital.getDataInicioAvaliacao(),
                edital.getDataFimAvaliacao(),
                true,
                "Edital cadastrado com sucesso."
        );
    }

    /**
     * Lista todos os editais armazenados em memória.
     */
    public List<Edital> listarTodos() {
        return repository.readAll();
    }

    /**
     * Busca um edital específico pelo número e ano.
     */
    public Edital buscarPorNumeroEAno(String numero, int ano) {
        return repository.read(numero, ano);
    }

    /**
     * Edita um edital existente validando todos os campos e impedindo inversão de períodos nas datas.
     */
    public EditalResponse editar(EditalRequest req) {
        Map<String, String> erros = validarCamposBasicosEDatas(req);

        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }

        Edital existente = repository.read(req.numero(), req.ano());
        if (existente == null) {
            throw new ValidacaoException("edital", "Edital não encontrado para o número e ano informados.");
        }

        Edital editalAtualizado = construirEntidade(req);
        repository.update(editalAtualizado);

        return new EditalResponse(
                editalAtualizado.getTitulo(),
                editalAtualizado.getNumero(),
                editalAtualizado.getAno(),
                editalAtualizado.getDataInicioSubmissao(),
                editalAtualizado.getDataFimSubmissao(),
                editalAtualizado.getDataInicioAvaliacao(),
                editalAtualizado.getDataFimAvaliacao(),
                true,
                "Edital atualizado com sucesso."
        );
    }

    private Map<String, String> validarCamposBasicosEDatas(EditalRequest req) {
        if (req == null) {
            throw new ValidacaoException("request", "Dados do edital não informados.");
        }

        Map<String, String> erros = new LinkedHashMap<>();

        if (vazio(req.titulo())) {
            erros.put("titulo", "Título do edital é obrigatório.");
        }
        if (vazio(req.numero())) {
            erros.put("numero", "Número do edital é obrigatório.");
        }
        if (req.ano() == null || req.ano() <= 0) {
            erros.put("ano", "Ano do edital é obrigatório e deve ser válido.");
        }

        erros.putAll(EditalValidator.validarDatas(
                req.dataInicioSubmissao(),
                req.dataFimSubmissao(),
                req.dataInicioAvaliacao(),
                req.dataFimAvaliacao()
        ));

        return erros;
    }

    private Edital construirEntidade(EditalRequest req) {
        return new Edital(
                req.titulo().trim(),
                req.numero().trim(),
                req.ano(),
                req.dataInicioSubmissao(),
                req.dataFimSubmissao(),
                req.dataInicioAvaliacao(),
                req.dataFimAvaliacao()
        );
    }

    private static boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
