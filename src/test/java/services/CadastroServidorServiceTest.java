package services;

import dto.CadastroServidorRequest;
import exceptions.ValidacaoException;
import models.entities.Servidor;
import models.enums.Perfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.ServidorRepository;

import static org.junit.jupiter.api.Assertions.*;

class CadastroServidorServiceTest {

    private ServidorRepository repository;
    private CadastroServidorService service;

    @BeforeEach
    void setUp() {
        repository = new ServidorRepository();
        service = new CadastroServidorService(repository);
    }

    @Test
    void registraSenhaHashEPerfisPadrao() {
        service.cadastrar(request("52998224725", "maria@if.edu.br", "senha123"));

        Servidor servidor = repository.read("529.982.247-25");
        assertNotNull(servidor);
        assertNotEquals("senha123", servidor.getSenhaHash());
        assertTrue(new BCryptPasswordEncoder().matches("senha123", servidor.getSenhaHash()));
        assertEquals(2, servidor.getPerfis().size());
        assertTrue(servidor.getPerfis().contains(Perfil.ROLE_COORDENADOR));
        assertTrue(servidor.getPerfis().contains(Perfil.ROLE_AVALIADOR));
    }

    @Test
    void rejeitaCpfDuplicadoMesmoComFormatacaoDiferente() {
        service.cadastrar(request("52998224725", "maria@if.edu.br", "senha123"));

        ValidacaoException exception = assertThrows(
                ValidacaoException.class,
                () -> service.cadastrar(request("529.982.247-25", "outra@if.edu.br", "senha123")));

        assertEquals("CPF já cadastrado.", exception.getCampos().get("cpf"));
    }

    @Test
    void rejeitaEmailDuplicadoSemDiferenciarMaiusculas() {
        service.cadastrar(request("52998224725", "Maria@if.edu.br", "senha123"));

        ValidacaoException exception = assertThrows(
                ValidacaoException.class,
                () -> service.cadastrar(request("11122233344", " maria@IF.edu.br ", "senha123")));

        assertEquals("E-mail já cadastrado.", exception.getCampos().get("email"));
    }

    @Test
    void rejeitaSenhaComMenosDeSeisCaracteres() {
        ValidacaoException exception = assertThrows(
                ValidacaoException.class,
                () -> service.cadastrar(request("52998224725", "maria@if.edu.br", "12345")));

        assertTrue(exception.getCampos().containsKey("senha"));
        assertTrue(repository.readAll().isEmpty());
    }

    private static CadastroServidorRequest request(String cpf, String email, String senha) {
        return new CadastroServidorRequest(
                "Maria Silva",
                cpf,
                email,
                senha,
                "Recife",
                "Tecnologia da Informação",
                "MESTRADO",
                "Maria",
                "FEMININO",
                "https://lattes.cnpq.br/1234567890123456",
                "(81) 98765-4321");
    }
}
