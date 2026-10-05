package repository;

import models.entities.Servidor;
import java.util.*;

public class ServidorRepository {

        private static List<Servidor> servidores;

        static {
            servidores = new ArrayList<>();
        }

        /** Insere e devolve o ID gerado. Lança se CPF/e-mail já existirem (race condition). */
        public void inserir(Servidor s) {
            servidores.add(s);
        }

        public Servidor read (String cpf){
            for (Servidor s : servidores){
                if(s.getCpf().equals(cpf)){
                    return s.selfReplicate();
                }
            }
            return null;
        }

        public void update (Servidor serv){
            for (Servidor s : servidores){
                if (s.getCpf().equals(serv.getCpf())){
                    s.setEmail(serv.getEmail());
                    s.setNomeCompleto(serv.getNomeCompleto());
                    s.setSenhaHash(serv.getSenhaHash());
                    s.setCampus(serv.getCampus());
                    s.setAreaFormacao(serv.getAreaFormacao());
                    s.setTitulacao(serv.getTitulacao());
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
