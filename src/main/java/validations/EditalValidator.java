package validations;

import models.entities.Edital;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EditalValidator {

    /**
     * Valida as datas de submissão e avaliação de um edital, impedindo inversão de períodos.
     * Retorna um mapa vazio se todas as datas forem válidas, ou (campo -> mensagem de erro).
     */
    public static Map<String, String> validarDatas(
            LocalDate dataInicioSubmissao,
            LocalDate dataFimSubmissao,
            LocalDate dataInicioAvaliacao,
            LocalDate dataFimAvaliacao
    ) {
        Map<String, String> erros = new LinkedHashMap<>();

        if (dataInicioSubmissao == null) {
            erros.put("dataInicioSubmissao", "Data de início da submissão é obrigatória.");
        }
        if (dataFimSubmissao == null) {
            erros.put("dataFimSubmissao", "Data de fim da submissão é obrigatória.");
        }
        if (dataInicioAvaliacao == null) {
            erros.put("dataInicioAvaliacao", "Data de início da avaliação é obrigatória.");
        }
        if (dataFimAvaliacao == null) {
            erros.put("dataFimAvaliacao", "Data de fim da avaliação é obrigatória.");
        }

        if (dataInicioSubmissao != null && dataFimSubmissao != null) {
            if (dataFimSubmissao.isBefore(dataInicioSubmissao)) {
                erros.put("dataFimSubmissao",
                        "Período de submissão inválido: a data de fim da submissão não pode ser anterior à data de início da submissão.");
            }
        }

        if (dataInicioAvaliacao != null) {
            if (dataFimSubmissao != null && dataInicioAvaliacao.isBefore(dataFimSubmissao)) {
                erros.put("dataInicioAvaliacao",
                        "Inversão de períodos: a data de início da avaliação não pode ser anterior à data de fim da submissão.");
            } else if (dataInicioSubmissao != null && dataInicioAvaliacao.isBefore(dataInicioSubmissao)) {
                erros.put("dataInicioAvaliacao",
                        "Inversão de períodos: a data de início da avaliação não pode ser anterior à data de início da submissão.");
            }
        }

        if (dataInicioAvaliacao != null && dataFimAvaliacao != null) {
            if (dataFimAvaliacao.isBefore(dataInicioAvaliacao)) {
                erros.put("dataFimAvaliacao",
                        "Período de avaliação inválido: a data de fim da avaliação não pode ser anterior à data de início da avaliação.");
            }
        }

        return erros;
    }

    public static boolean isDatasValidas(
            LocalDate dataInicioSubmissao,
            LocalDate dataFimSubmissao,
            LocalDate dataInicioAvaliacao,
            LocalDate dataFimAvaliacao
    ) {
        return validarDatas(
                dataInicioSubmissao,
                dataFimSubmissao,
                dataInicioAvaliacao,
                dataFimAvaliacao
        ).isEmpty();
    }

    public static boolean isDatasValidas(Edital edital) {
        if (edital == null) {
            return false;
        }
        return isDatasValidas(
                edital.getDataInicioSubmissao(),
                edital.getDataFimSubmissao(),
                edital.getDataInicioAvaliacao(),
                edital.getDataFimAvaliacao()
        );
    }

    public static boolean isUnique(String numero, int ano, List<Edital> editais) {
        if (numero == null || editais == null) {
            return true;
        }
        String numLimpo = numero.trim();
        for (Edital e : editais) {
            if (e.getNumero() != null
                    && e.getNumero().trim().equalsIgnoreCase(numLimpo)
                    && e.getAno() == ano) {
                return false;
            }
        }
        return true;
    }
}
