package models.entities;

import models.enums.Perfil;
import models.enums.Sexo;
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
    private String nomeSocial;
    private Sexo sexo;
    private String lattes;
    private String telefone;
    private HashSet<Perfil> perfis = new HashSet<>();

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

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getLattes() {
        return lattes;
    }

    public void setLattes(String lattes) {
        this.lattes = lattes;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public HashSet<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(HashSet<Perfil> perfis) {
        this.perfis = perfis;
    }

    public Servidor selfReplicate() {
        Servidor r = new Servidor();
        r.setCpf(cpf);
        r.setNomeCompleto(nomeCompleto);
        r.setEmail(email);
        r.setSenhaHash(senhaHash);
        r.setCampus(campus);
        r.setAreaFormacao(areaFormacao);
        r.setTitulacao(titulacao);
        r.setNomeSocial(nomeSocial);
        r.setSexo(sexo);
        r.setLattes(lattes);
        r.setTelefone(telefone);
        r.setPerfis(perfis);
        return r;
    }
}