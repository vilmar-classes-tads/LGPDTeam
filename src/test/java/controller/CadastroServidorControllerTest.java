package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CadastroServidorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CadastroServidorControllerTest.TestApplication.class)
@AutoConfigureMockMvc
class CadastroServidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = {"controller", "dto", "exceptions", "models", "repository", "services"})
    static class TestApplication {
    }

    @Test
    void cadastroComSucessoRetornaCreatedEPerfisPadrao() throws Exception {
        CadastroServidorRequest request = request(
                "52998224725", "sucesso-" + System.nanoTime() + "@if.edu.br", "senha123");

        mockMvc.perform(post("/api/servidores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.perfis.length()").value(2))
                .andExpect(jsonPath("$.sucesso").value(true));
    }

    @Test
    void erroDeValidacaoRetornaCamposSemMudarParaSucesso() throws Exception {
        CadastroServidorRequest request = request(
                "11122233344", "invalido-" + System.nanoTime() + "@if.edu.br", "12345");

        mockMvc.perform(post("/api/servidores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.sucesso").value(false))
                .andExpect(jsonPath("$.campos.senha").value("A senha deve ter no mínimo 6 caracteres."));
    }

    @Test
    void loginServePaginaComMensagemDeSucesso() throws Exception {
        mockMvc.perform(get("/login").queryParam("cadastro", "sucesso"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/login.html"));
    }

    private static CadastroServidorRequest request(String cpf, String email, String senha) {
        return new CadastroServidorRequest(
                "Maria Silva", cpf, email, senha, "Recife", "TI", "MESTRADO",
                "Maria", "FEMININO", "", "");
    }
}
