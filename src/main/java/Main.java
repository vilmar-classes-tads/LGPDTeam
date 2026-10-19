import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dto.CadastroServidorRequest;

@SpringBootApplication(scanBasePackages = {"dto", "models", "repository", "controllers", "services"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        var req = new CadastroServidorRequest(
                "Maria Silva", "529.982.247-25",
                "maria@if.edu.br",
                "senha123",
                "Recife",
                "TI",
                "MESTRADO",
                "EXATAS"
        );
        System.out.println("Academy Registry inicializado com sucesso! Exemplo criado: " + req.nomeCompleto());
    }
}
