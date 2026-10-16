package models.enums;

public record Perfil(
        int id,
        String nome
) {
    public static final Perfil ROLE_COORDENADOR = new Perfil(1, "ROLE_COORDENADOR");
    public static final Perfil ROLE_AVALIADOR = new Perfil(2, "ROLE_AVALIADOR");
}
