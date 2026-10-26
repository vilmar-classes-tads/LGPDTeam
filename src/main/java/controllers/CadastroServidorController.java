package controllers;

import dto.CadastroServidorRequest;
import dto.CadastroServidorResponse;
import dto.ErroResponse;
import exceptions.ValidacaoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import services.CadastroServidorService;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class CadastroServidorController {

    private final CadastroServidorService service;

    public CadastroServidorController(CadastroServidorService service) {
        this.service = service;
    }

    @GetMapping(value = "/cadastro", produces = MediaType.TEXT_HTML_VALUE)
    public String exibirFormularioCadastro() {
        return renderizarFormularioCadastro(null, null);
    }

    @PostMapping(
            value = "/cadastro",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public ResponseEntity<String> cadastrarViaFormulario(@ModelAttribute CadastroServidorRequest req) {
        try {
            CadastroServidorResponse resp = service.cadastrar(req);
            String msgEncoded = URLEncoder.encode(resp.mensagem(), StandardCharsets.UTF_8);
            URI destino = URI.create(resp.redirectUrl() + "?sucesso=true&mensagem=" + msgEncoded);
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(destino)
                    .build();
        } catch (ValidacaoException e) {
            ErroResponse erro = new ErroResponse(e.getMessage(), e.getCampos(), req);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_HTML)
                    .body(renderizarFormularioCadastro(req, erro));
        }
    }

    @PostMapping(
            value = "/cadastro",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> cadastrarViaJson(@RequestBody CadastroServidorRequest req) {
        try {
            CadastroServidorResponse resp = service.cadastrar(req);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, resp.redirectUrl())
                    .body(resp);
        } catch (ValidacaoException e) {
            ErroResponse erro = new ErroResponse(e.getMessage(), e.getCampos(), req);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(erro);
        }
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public String exibirTelaLogin(
            @RequestParam(name = "sucesso", required = false, defaultValue = "false") boolean sucesso,
            @RequestParam(name = "mensagem", required = false) String mensagem
    ) {
        String bannerSucesso = "";
        if (sucesso || (mensagem != null && !mensagem.isBlank())) {
            String texto = (mensagem != null && !mensagem.isBlank())
                    ? HtmlUtils.htmlEscape(mensagem)
                    : HtmlUtils.htmlEscape(CadastroServidorService.MENSAGEM_SUCESSO_CADASTRO);
            bannerSucesso = "<div class=\"alert-success\" role=\"alert\">" + texto + "</div>";
        }

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Login - Academy Registry</title>
                </head>
                <body>
                    <h1>Login</h1>
                    %s
                    <form method="post" action="/login">
                        <label>CPF ou E-mail: <input type="text" name="login" required></label><br>
                        <label>Senha: <input type="password" name="senha" required></label><br>
                        <button type="submit">Entrar</button>
                    </form>
                </body>
                </html>
                """.formatted(bannerSucesso);
    }

    public String renderizarFormularioCadastro(CadastroServidorRequest dados, ErroResponse erro) {
        Map<String, String> camposErro = (erro != null && erro.campos() != null) ? erro.campos() : Map.of();

        StringBuilder bannerErro = new StringBuilder();
        if (erro != null) {
            bannerErro.append("<div class=\"alert-error\" role=\"alert\">")
                    .append("<strong>").append(escapar(erro.mensagem())).append("</strong>");
            if (!camposErro.isEmpty()) {
                bannerErro.append("<ul>");
                for (String msg : camposErro.values()) {
                    bannerErro.append("<li>").append(escapar(msg)).append("</li>");
                }
                bannerErro.append("</ul>");
            }
            bannerErro.append("</div>");
        }

        String nomeCompleto = dados != null ? escapar(dados.nomeCompleto()) : "";
        String cpf = dados != null ? escapar(dados.cpf()) : "";
        String email = dados != null ? escapar(dados.email()) : "";
        String campus = dados != null ? escapar(dados.campus()) : "";
        String areaFormacao = dados != null ? escapar(dados.areaFormacao()) : "";
        String titulacao = dados != null && dados.titulacao() != null ? dados.titulacao().trim() : "";
        String nomeSocial = dados != null ? escapar(dados.nomeSocial()) : "";
        String sexo = dados != null && dados.sexo() != null ? dados.sexo().trim() : "";
        String lattes = dados != null ? escapar(dados.lattes()) : "";
        String telefone = dados != null ? escapar(dados.telefone()) : "";

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Cadastro de Servidor - Academy Registry</title>
                </head>
                <body>
                    <h1>Cadastro de Novo Usuário</h1>
                    %s
                    <form method="post" action="/cadastro">
                        <label>Nome Completo*:
                            <input type="text" name="nomeCompleto" value="%s" required>
                        </label>%s<br>
                        <label>CPF*:
                            <input type="text" name="cpf" value="%s" required>
                        </label>%s<br>
                        <label>E-mail Institucional*:
                            <input type="email" name="email" value="%s" required>
                        </label>%s<br>
                        <label>Senha*:
                            <input type="password" name="senha" required>
                        </label>%s<br>
                        <label>Campus*:
                            <input type="text" name="campus" value="%s" required>
                        </label>%s<br>
                        <label>Área de Formação*:
                            <input type="text" name="areaFormacao" value="%s" required>
                        </label>%s<br>
                        <label>Titulação*:
                            <select name="titulacao" required>
                                <option value="">Selecione...</option>
                                <option value="GRADUACAO"%s>Graduação</option>
                                <option value="ESPECIALIZACAO"%s>Especialização</option>
                                <option value="MESTRADO"%s>Mestrado</option>
                                <option value="DOUTORADO"%s>Doutorado</option>
                                <option value="POS_DOUTORADO"%s>Pós-Doutorado</option>
                            </select>
                        </label>%s<br>
                        <label>Nome Social:
                            <input type="text" name="nomeSocial" value="%s">
                        </label>%s<br>
                        <label>Sexo:
                            <select name="sexo">
                                <option value="">Selecione...</option>
                                <option value="MASCULINO"%s>Masculino</option>
                                <option value="FEMININO"%s>Feminino</option>
                                <option value="OUTRO"%s>Outro</option>
                            </select>
                        </label>%s<br>
                        <label>Link do Lattes:
                            <input type="url" name="lattes" value="%s">
                        </label>%s<br>
                        <label>Telefone:
                            <input type="text" name="telefone" value="%s">
                        </label>%s<br>
                        <button type="submit">Cadastrar</button>
                    </form>
                </body>
                </html>
                """.formatted(
                bannerErro.toString(),
                nomeCompleto, renderizarErroCampo(camposErro, "nomeCompleto"),
                cpf, renderizarErroCampo(camposErro, "cpf"),
                email, renderizarErroCampo(camposErro, "email"),
                renderizarErroCampo(camposErro, "senha"),
                campus, renderizarErroCampo(camposErro, "campus"),
                areaFormacao, renderizarErroCampo(camposErro, "areaFormacao"),
                selecionado(titulacao, "GRADUACAO"),
                selecionado(titulacao, "ESPECIALIZACAO"),
                selecionado(titulacao, "MESTRADO"),
                selecionado(titulacao, "DOUTORADO"),
                selecionado(titulacao, "POS_DOUTORADO"),
                renderizarErroCampo(camposErro, "titulacao"),
                nomeSocial, renderizarErroCampo(camposErro, "nomeSocial"),
                selecionado(sexo, "MASCULINO"),
                selecionado(sexo, "FEMININO"),
                selecionado(sexo, "OUTRO"),
                renderizarErroCampo(camposErro, "sexo"),
                lattes, renderizarErroCampo(camposErro, "lattes"),
                telefone, renderizarErroCampo(camposErro, "telefone")
        );
    }

    private static String renderizarErroCampo(Map<String, String> camposErro, String campo) {
        String msg = camposErro.get(campo);
        if (msg == null || msg.isBlank()) {
            return "";
        }
        return " <span class=\"field-error\" data-field=\"" + escapar(campo) + "\">" + escapar(msg) + "</span>";
    }

    private static String selecionado(String valorAtual, String opcao) {
        return opcao.equalsIgnoreCase(valorAtual) ? " selected" : "";
    }

    private static String escapar(String valor) {
        return valor == null ? "" : HtmlUtils.htmlEscape(valor);
    }
}
