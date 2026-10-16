package controller;

import dto.CadastroServidorRequest;
import dto.CadastroServidorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.CadastroServidorService;

@RestController
@RequestMapping("/api/servidores")
public class CadastroServidorController {

    private final CadastroServidorService service;

    public CadastroServidorController(CadastroServidorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CadastroServidorResponse> cadastrar(@RequestBody CadastroServidorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }
}
