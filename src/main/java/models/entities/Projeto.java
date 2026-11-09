package models.entities;

public class Projeto {

    public static final String STATUS_RASCUNHO = "Rascunho";
    public static final String STATUS_EM_CORRECAO = "Em Correção";
    public static final String STATUS_SUBMETIDO = "Submetido";

    private String titulo;
    private String resumo;
    private String palavrasChave;
    private String publicoAlvo;
    private String areaTematica;
    private String campus;
    private String ods;
    private boolean aceiteTermoCompromisso;
    private String status;

    public Projeto() {
        this.status = STATUS_RASCUNHO;
    }

    public Projeto(
            String titulo,
            String resumo,
            String palavrasChave,
            String publicoAlvo,
            String areaTematica,
            String campus,
            String ods,
            boolean aceiteTermoCompromisso
    ) {
        this(titulo, resumo, palavrasChave, publicoAlvo, areaTematica, campus, ods, aceiteTermoCompromisso, STATUS_RASCUNHO);
    }

    public Projeto(
            String titulo,
            String resumo,
            String palavrasChave,
            String publicoAlvo,
            String areaTematica,
            String campus,
            String ods,
            boolean aceiteTermoCompromisso,
            String status
    ) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.palavrasChave = palavrasChave;
        this.publicoAlvo = publicoAlvo;
        this.areaTematica = areaTematica;
        this.campus = campus;
        this.ods = ods;
        this.aceiteTermoCompromisso = aceiteTermoCompromisso;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getOds() {
        return ods;
    }

    public void setOds(String ods) {
        this.ods = ods;
    }

    public boolean isAceiteTermoCompromisso() {
        return aceiteTermoCompromisso;
    }

    public boolean getAceiteTermoCompromisso() {
        return aceiteTermoCompromisso;
    }

    public void setAceiteTermoCompromisso(boolean aceiteTermoCompromisso) {
        this.aceiteTermoCompromisso = aceiteTermoCompromisso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Projeto selfReplicate() {
        Projeto p = new Projeto();
        p.setTitulo(this.titulo);
        p.setResumo(this.resumo);
        p.setPalavrasChave(this.palavrasChave);
        p.setPublicoAlvo(this.publicoAlvo);
        p.setAreaTematica(this.areaTematica);
        p.setCampus(this.campus);
        p.setOds(this.ods);
        p.setAceiteTermoCompromisso(this.aceiteTermoCompromisso);
        p.setStatus(this.status);
        return p;
    }
}
