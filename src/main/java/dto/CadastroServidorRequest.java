package dto;

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
}