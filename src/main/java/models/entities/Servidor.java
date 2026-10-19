package models.entities;

import models.enums.Perfil;
import models.enums.Titulacao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Servidor {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String senhaHash;
    private String campus;
    private String areaFormacao;
    private List<Titulacao> titulacao;
    private Set<Perfil> perfis = new HashSet<>();

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getAreaFormacao() {
        return areaFormacao;
    }

    public void setAreaFormacao(String areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public List<Titulacao> getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(List<Titulacao> titulacao) {
        this.titulacao = titulacao;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public Servidor selfReplicate(){

        Servidor replica = new Servidor();

        replica.setCpf(this.getCpf());
        replica.setNomeCompleto(this.getNomeCompleto());
        replica.setEmail(this.getEmail());
        replica.setSenhaHash(this.getSenhaHash());
        replica.setAreaFormacao(this.getAreaFormacao());
        replica.setCampus(this.getCampus());
        replica.setTitulacao(this.getTitulacao());

        return replica;
    }
}
