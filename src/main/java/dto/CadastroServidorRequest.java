package dto;

import models.enums.Perfil;
import models.enums.Titulacao;

import java.util.List;
import java.util.Set;

public record CadastroServidorRequest(
        String nomeCompleto,
        String cpf,
        String email,
        String senhaHash,
        String campus,
        String areaFormacao,
        String titulacao,
        String perfis
)
{
    public CadastroServidorRequest(
            String nomeCompleto,
            String cpf,
            String email,
            String senhaHash,
            String campus,
            String areaFormacao,
            String titulacao,
            String perfis) {
        this.cpf = cpf;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.senhaHash = senhaHash;
        this.campus = campus;
        this.areaFormacao = areaFormacao;
        this.titulacao = titulacao;
        this.perfis = perfis;

    }
}
