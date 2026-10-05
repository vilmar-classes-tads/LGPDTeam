package dto;

import models.enums.Perfil;

import java.util.Set;

public record CadastroServidorResponse(
        String nomeCompleto,
        String cpf,
        String email,
        Set<Perfil> perfis,
        boolean sucesso,
        String mensagem
) {
}
