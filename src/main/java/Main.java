import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dto.CadastroServidorRequest;

@SpringBootApplication(scanBasePackages = {"controller", "dto", "exceptions", "models", "repository", "services"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        var req = new CadastroServidorRequest(
                "Maria Silva",
                "529.982.247-25",
                "maria@if.edu.br",
                "senha123",
                "Recife",
                "TI",
                "MESTRADO",
                "Maria",
                "FEMININO",
                "http://lattes.cnpq.br/1234567890123456",
                "(81) 98765-4321"
        );
        System.out.println("Academy Registry inicializado com sucesso! Exemplo criado: " + req.nomeCompleto());
    }
}
