package repository;

import models.entities.Servidor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ServidorRepository {

    private final List<Servidor> servidores = new ArrayList<>();

    public synchronized void inserir(Servidor servidor) {
        Map<String, String> erros = inserirSeUnico(servidor);
        if (!erros.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", erros.values()));
        }
    }

    public synchronized Map<String, String> inserirSeUnico(Servidor servidor) {
        Map<String, String> erros = new LinkedHashMap<>();
        String cpf = normalizarCpf(servidor.getCpf());
        String email = normalizarEmail(servidor.getEmail());

        for (Servidor existente : servidores) {
            if (normalizarCpf(existente.getCpf()).equals(cpf)) {
                erros.put("cpf", "CPF já cadastrado.");
            }
            if (normalizarEmail(existente.getEmail()).equals(email)) {
                erros.put("email", "E-mail já cadastrado.");
            }
        }

        if (erros.isEmpty()) {
            servidores.add(servidor);
        }
        return erros;
    }

    public synchronized Servidor read(String cpf) {
        String chave = normalizarCpf(cpf);
        return servidores.stream()
                .filter(servidor -> normalizarCpf(servidor.getCpf()).equals(chave))
                .findFirst()
                .map(Servidor::selfReplicate)
                .orElse(null);
    }

    public synchronized void update(Servidor serv) {
        for (Servidor existente : servidores) {
            if (normalizarCpf(existente.getCpf()).equals(normalizarCpf(serv.getCpf()))) {
                existente.setEmail(serv.getEmail());
                existente.setNomeCompleto(serv.getNomeCompleto());
                existente.setSenhaHash(serv.getSenhaHash());
                existente.setCampus(serv.getCampus());
                existente.setAreaFormacao(serv.getAreaFormacao());
                existente.setTitulacao(serv.getTitulacao());
                existente.setNomeSocial(serv.getNomeSocial());
                existente.setSexo(serv.getSexo());
                existente.setLattes(serv.getLattes());
                existente.setTelefone(serv.getTelefone());
                existente.setPerfis(new HashSet<>(serv.getPerfis()));
                return;
            }
        }
    }

    public synchronized void delete(Servidor serv) {
        servidores.removeIf(
                existente -> normalizarCpf(existente.getCpf()).equals(normalizarCpf(serv.getCpf())));
    }

    public synchronized List<Servidor> readAll() {
        return servidores.stream().map(Servidor::selfReplicate).toList();
    }

    private static String normalizarCpf(String cpf) {
        return cpf == null ? "" : cpf.replaceAll("\\D", "");
    }

    private static String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
