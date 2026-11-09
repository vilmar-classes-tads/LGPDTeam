package models.entities;

import exceptions.ValidacaoException;
import validations.EditalValidator;

import java.time.LocalDate;
import java.util.Map;

public class Edital {
    private String titulo;
    private String numero;
    private int ano;
    private LocalDate dataInicioSubmissao;
    private LocalDate dataFimSubmissao;
    private LocalDate dataInicioAvaliacao;
    private LocalDate dataFimAvaliacao;

    public Edital() {
    }

    public Edital(
            String titulo,
            String numero,
            int ano,
            LocalDate dataInicioSubmissao,
            LocalDate dataFimSubmissao,
            LocalDate dataInicioAvaliacao,
            LocalDate dataFimAvaliacao
    ) {
        this.titulo = titulo;
        this.numero = numero;
        this.ano = ano;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.dataInicioAvaliacao = dataInicioAvaliacao;
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public LocalDate getDataInicioSubmissao() {
        return dataInicioSubmissao;
    }

    public void setDataInicioSubmissao(LocalDate dataInicioSubmissao) {
        this.dataInicioSubmissao = dataInicioSubmissao;
    }

    public LocalDate getDataFimSubmissao() {
        return dataFimSubmissao;
    }

    public void setDataFimSubmissao(LocalDate dataFimSubmissao) {
        this.dataFimSubmissao = dataFimSubmissao;
    }

    public LocalDate getDataInicioAvaliacao() {
        return dataInicioAvaliacao;
    }

    public void setDataInicioAvaliacao(LocalDate dataInicioAvaliacao) {
        this.dataInicioAvaliacao = dataInicioAvaliacao;
    }

    public LocalDate getDataFimAvaliacao() {
        return dataFimAvaliacao;
    }

    public void setDataFimAvaliacao(LocalDate dataFimAvaliacao) {
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    /**
     * Verifica se as datas de submissão e avaliação são válidas (sem inversão de períodos).
     */
    public boolean datasValidas() {
        return EditalValidator.isDatasValidas(
                dataInicioSubmissao,
                dataFimSubmissao,
                dataInicioAvaliacao,
                dataFimAvaliacao
        );
    }

    /**
     * Valida as datas do edital, lançando ValidacaoException caso haja inversão de períodos ou datas ausentes.
     */
    public void validarDatas() {
        Map<String, String> erros = EditalValidator.validarDatas(
                dataInicioSubmissao,
                dataFimSubmissao,
                dataInicioAvaliacao,
                dataFimAvaliacao
        );
        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }
    }

    public Edital selfReplicate() {
        Edital r = new Edital();
        r.setTitulo(titulo);
        r.setNumero(numero);
        r.setAno(ano);
        r.setDataInicioSubmissao(dataInicioSubmissao);
        r.setDataFimSubmissao(dataFimSubmissao);
        r.setDataInicioAvaliacao(dataInicioAvaliacao);
        r.setDataFimAvaliacao(dataFimAvaliacao);
        return r;
    }
}
