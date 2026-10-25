package repository;

import models.entities.Servidor;
import org.springframework.stereotype.Repository;
import validations.CpfValidator;

import java.util.*;

@Repository
public class ServidorRepository {

        private static List<Servidor> servidores;

        static {
            servidores = new ArrayList<>();
        }

        public void inserir(Servidor s) {
            servidores.add(s);
        }

        public Servidor read (String cpf){
            String cpfLimpo = CpfValidator.normalizar(cpf);
            for (Servidor s : servidores){
                if(s.getCpf() != null && CpfValidator.normalizar(s.getCpf()).equals(cpfLimpo)){
                    return s.selfReplicate();
                }
            }
            return null;
        }

        public void update (Servidor serv){
            String cpfLimpo = CpfValidator.normalizar(serv.getCpf());
            for (Servidor s : servidores){
                if (s.getCpf() != null && CpfValidator.normalizar(s.getCpf()).equals(cpfLimpo)){
                    s.setEmail(serv.getEmail());
                    s.setNomeCompleto(serv.getNomeCompleto());
                    s.setSenhaHash(serv.getSenhaHash());
                    s.setCampus(serv.getCampus());
                    s.setAreaFormacao(serv.getAreaFormacao());
                    s.setTitulacao(serv.getTitulacao());
                    s.setNomeSocial(serv.getNomeSocial());
                    s.setSexo(serv.getSexo());
                    s.setLattes(serv.getLattes());
                    s.setTelefone(serv.getTelefone());
                    s.setPerfis(serv.getPerfis());
                }
            }
        }

        public void delete (Servidor serv){
            servidores.remove(serv);
        }

        public List<Servidor> readAll (){
            return servidores;
        }
}
