package services;

import exceptions.ValidacaoException;
import models.entities.Projeto;
import org.springframework.stereotype.Service;
import repository.ProjetoRepository;
import validations.ProjetoValidator;

/**
 * Responsabilidade única (SRP): orquestração do fluxo de casos de uso de Projeto.
 * Respeita DIP (inversão de dependência) recebendo validador e repositório por injeção de dependência.
 */
@Service
public class ProjetoService {

    private final ProjetoRepository repository;
    private final ProjetoValidator validator;

    public ProjetoService(ProjetoRepository repository, ProjetoValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    /**
     * Regra de negócio central: Valida se o projeto pode ser editado.
     * Lança ValidacaoException caso o status seja diferente de "Rascunho" ou "Em Correção"
     * (bloqueando a edição de projetos já submetidos ou em outros estados).
     */
    public void editarProjeto(Projeto projeto) {
        if (validator == null) {
            throw new IllegalStateException("Validador não configurado no serviço.");
        }
        validator.validarPermissaoEdicao(projeto);
    }

    /**
     * Executa a edição dos dados do projeto após validar as regras de permissão.
     */
    public void editarProjeto(Projeto projeto, Projeto dadosAtualizados) {
        if (projeto == null) {
            throw new ValidacaoException("projeto", "Projeto não pode ser nulo.");
        }
        editarProjeto(projeto);

        if (dadosAtualizados != null) {
            if (dadosAtualizados.getTitulo() != null) {
                projeto.setTitulo(dadosAtualizados.getTitulo());
            }
            if (dadosAtualizados.getResumo() != null) {
                projeto.setResumo(dadosAtualizados.getResumo());
            }
            if (dadosAtualizados.getPalavrasChave() != null) {
                projeto.setPalavrasChave(dadosAtualizados.getPalavrasChave());
            }
            if (dadosAtualizados.getPublicoAlvo() != null) {
                projeto.setPublicoAlvo(dadosAtualizados.getPublicoAlvo());
            }
            if (dadosAtualizados.getAreaTematica() != null) {
                projeto.setAreaTematica(dadosAtualizados.getAreaTematica());
            }
            if (dadosAtualizados.getCampus() != null) {
                projeto.setCampus(dadosAtualizados.getCampus());
            }
            if (dadosAtualizados.getOds() != null) {
                projeto.setOds(dadosAtualizados.getOds());
            }
            projeto.setAceiteTermoCompromisso(dadosAtualizados.isAceiteTermoCompromisso());
        }

        if (repository != null) {
            repository.update(projeto);
        }
    }

    /**
     * Sobrecarga conveniente para edição direta de atributos.
     */
    public void editarProjeto(
            Projeto projeto,
            String titulo,
            String resumo,
            String palavrasChave,
            String publicoAlvo,
            String areaTematica,
            String campus,
            String ods,
            boolean aceiteTermoCompromisso
    ) {
        Projeto dadosAtualizados = new Projeto(
                titulo,
                resumo,
                palavrasChave,
                publicoAlvo,
                areaTematica,
                campus,
                ods,
                aceiteTermoCompromisso,
                projeto != null ? projeto.getStatus() : null
        );
        editarProjeto(projeto, dadosAtualizados);
    }
}
