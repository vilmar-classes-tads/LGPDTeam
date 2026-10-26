package dto;

public record CadastroServidorRequest(
        String nomeCompleto,
        String cpf,
        String email,
        String senha,
        String campus,
        String areaFormacao,
        String titulacao,
        String nomeSocial,
        String sexo,
        String lattes,
        String telefone
) {
}