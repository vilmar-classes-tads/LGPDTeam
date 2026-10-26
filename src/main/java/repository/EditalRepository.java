package repository;

import models.entities.Edital;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EditalRepository {

    private static final List<Edital> editais = new ArrayList<>();

    public void inserir(Edital edital) {
        editais.add(edital.selfReplicate());
    }

    public Edital read(String numero, int ano) {
        if (numero == null) {
            return null;
        }
        String numLimpo = numero.trim();
        for (Edital e : editais) {
            if (e.getNumero() != null
                    && e.getNumero().trim().equalsIgnoreCase(numLimpo)
                    && e.getAno() == ano) {
                return e.selfReplicate();
            }
        }
        return null;
    }

    public Edital read(String numero) {
        if (numero == null) {
            return null;
        }
        String numLimpo = numero.trim();
        for (Edital e : editais) {
            if (e.getNumero() != null && e.getNumero().trim().equalsIgnoreCase(numLimpo)) {
                return e.selfReplicate();
            }
        }
        return null;
    }

    public boolean update(Edital editalAtualizado) {
        if (editalAtualizado == null || editalAtualizado.getNumero() == null) {
            return false;
        }
        String numLimpo = editalAtualizado.getNumero().trim();
        for (Edital e : editais) {
            if (e.getNumero() != null
                    && e.getNumero().trim().equalsIgnoreCase(numLimpo)
                    && e.getAno() == editalAtualizado.getAno()) {
                e.setTitulo(editalAtualizado.getTitulo());
                e.setDataInicioSubmissao(editalAtualizado.getDataInicioSubmissao());
                e.setDataFimSubmissao(editalAtualizado.getDataFimSubmissao());
                e.setDataInicioAvaliacao(editalAtualizado.getDataInicioAvaliacao());
                e.setDataFimAvaliacao(editalAtualizado.getDataFimAvaliacao());
                return true;
            }
        }
        return false;
    }

    public boolean delete(Edital edital) {
        if (edital == null || edital.getNumero() == null) {
            return false;
        }
        return delete(edital.getNumero(), edital.getAno());
    }

    public boolean delete(String numero, int ano) {
        if (numero == null) {
            return false;
        }
        String numLimpo = numero.trim();
        return editais.removeIf(e ->
                e.getNumero() != null
                        && e.getNumero().trim().equalsIgnoreCase(numLimpo)
                        && e.getAno() == ano
        );
    }

    public List<Edital> readAll() {
        List<Edital> copia = new ArrayList<>(editais.size());
        for (Edital e : editais) {
            copia.add(e.selfReplicate());
        }
        return copia;
    }
}
