package services;

import dto.CadastroServidorRequest;
import dto.CadastroServidorResponse;
import exceptions.ValidacaoException;
import models.entities.Servidor;
import models.enums.Sexo;
import models.enums.Titulacao;
import org.springframework.stereotype.Service;
import repository.ServidorRepository;
import validations.CpfValidator;
import validations.EmailValidator;
import validations.SenhaValidator;

import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.List;
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

        if (vazio(req.cpf())) {
            erros.put("cpf", "CPF é obrigatório.");
        } else if (!CpfValidator.isValid(req.cpf())) {
            erros.put("cpf", "CPF inválido. Deve conter 11 dígitos numéricos.");
        } else if (!CpfValidator.isUnique(req.cpf(), repository.readAll())) {
            erros.put("cpf", "CPF já cadastrado no sistema.");
        }

        if (vazio(req.email())) {
            erros.put("email", "E-mail institucional é obrigatório.");
        } else if (!EmailValidator.isValid(req.email())) {
            erros.put("email", "E-mail institucional inválido.");
        } else if (!EmailValidator.isUnique(req.email(), repository.readAll())) {
            erros.put("email", "E-mail institucional já cadastrado no sistema.");
        }

        if (vazio(req.senha())) {
            erros.put("senha", "Senha é obrigatória.");
        } else if (!SenhaValidator.isValid(req.senha())) {
            erros.put("senha", "A senha deve ter no mínimo 6 caracteres.");
        }

        Titulacao titulacao = null;
        if (vazio(req.titulacao())) {
            erros.put("titulacao", "Titulação é obrigatória.");
        } else {
            titulacao = converterEnum(Titulacao.class, req.titulacao());
            if (titulacao == null) erros.put("titulacao", "Titulação inválida.");
        }

        //Precisa verificar se o ajuste de Titulação para Lista modifica algo em como o dto lida com o dado na persistência

        Sexo sexo = null;
        if (!vazio(req.sexo())) {
            sexo = converterEnum(Sexo.class, req.sexo());
            if (sexo == null) erros.put("sexo", "Sexo inválido.");
        }

        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }

        Servidor s = new Servidor();
        s.setNomeCompleto(req.nomeCompleto().trim());
        s.setCpf(CpfValidator.normalizar(req.cpf()));
        s.setEmail(EmailValidator.normalizar(req.email()));
        s.setSenhaHash(SenhaValidator.gerarHash(req.senha()));
        s.setCampus(req.campus().trim());
        s.setAreaFormacao(req.areaFormacao().trim());
        if (titulacao != null) {
            s.setTitulacao(List.of(titulacao));
        }
        if (!vazio(req.nomeSocial())) {
            s.setNomeSocial(req.nomeSocial().trim());
        }
        s.setSexo(sexo);
        if (!vazio(req.lattes())) {
            s.setLattes(req.lattes().trim());
        }
        if (!vazio(req.telefone())) {
            s.setTelefone(req.telefone().trim());
        }

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
