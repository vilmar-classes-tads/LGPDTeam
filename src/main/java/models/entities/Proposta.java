package models.entities;

public class Proposta extends Projeto {

    public Proposta() {
        super();
    }

    public Proposta(
            String titulo,
            String resumo,
            String palavrasChave,
            String publicoAlvo,
            String areaTematica,
            String campus,
            String ods,
            boolean aceiteTermoCompromisso
    ) {
        super(titulo, resumo, palavrasChave, publicoAlvo, areaTematica, campus, ods, aceiteTermoCompromisso);
    }

    public Proposta(
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
        super(titulo, resumo, palavrasChave, publicoAlvo, areaTematica, campus, ods, aceiteTermoCompromisso, status);
    }
}
