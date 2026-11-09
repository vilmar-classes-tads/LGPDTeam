package repository;

import models.entities.Projeto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsabilidade única (SRP): persistência e acesso a dados em memória de Projetos.
 */
@Repository
public class ProjetoRepository {

    private final List<Projeto> projetos = new ArrayList<>();

    public void inserir(Projeto projeto) {
        if (projeto != null) {
            projetos.add(projeto.selfReplicate());
        }
    }

    public Projeto read(String titulo) {
        if (titulo == null) {
            return null;
        }
        String limpo = titulo.trim();
        for (Projeto p : projetos) {
            if (p.getTitulo() != null && p.getTitulo().trim().equalsIgnoreCase(limpo)) {
                return p.selfReplicate();
            }
        }
        return null;
    }

    public boolean update(Projeto projetoAtualizado) {
        if (projetoAtualizado == null || projetoAtualizado.getTitulo() == null) {
            return false;
        }
        String limpo = projetoAtualizado.getTitulo().trim();
        for (int i = 0; i < projetos.size(); i++) {
            Projeto atual = projetos.get(i);
            if (atual.getTitulo() != null && atual.getTitulo().trim().equalsIgnoreCase(limpo)) {
                projetos.set(i, projetoAtualizado.selfReplicate());
                return true;
            }
        }
        return false;
    }

    public List<Projeto> readAll() {
        List<Projeto> copia = new ArrayList<>();
        for (Projeto p : projetos) {
            copia.add(p.selfReplicate());
        }
        return copia;
    }
}
