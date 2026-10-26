package dto;

import java.time.LocalDate;

public record EditalResponse(
        String titulo,
        String numero,
        int ano,
        LocalDate dataInicioSubmissao,
        LocalDate dataFimSubmissao,
        LocalDate dataInicioAvaliacao,
        LocalDate dataFimAvaliacao,
        boolean sucesso,
        String mensagem
) {
}
