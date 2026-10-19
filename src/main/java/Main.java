import dto.CadastroServidorRequest;

public class Main {
    public static void main(String[] args) {

        var req = new CadastroServidorRequest(
                "Maria Silva", "529.982.247-25",
                "maria@if.edu.br",
                "senha123",
                "Recife",
                "TI",
                "MESTRADO",
                "EXATAS"
        );
    }
}
