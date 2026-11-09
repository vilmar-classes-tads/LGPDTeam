package dto;

import java.time.LocalDate;

public record EditalRequest(
        String titulo,
        String numero,
        Integer ano,
        LocalDate dataInicioSubmissao,
        LocalDate dataFimSubmissao,
        LocalDate dataInicioAvaliacao,
        LocalDate dataFimAvaliacao
) {
}
