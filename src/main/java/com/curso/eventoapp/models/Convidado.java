package com.curso.eventoapp.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity //dizer q essa classe será uma entidade/tabela no banco de dados
public class Convidado {

    @Id //dizer q o atributo "rg" será um ID e consequentemente uma PK
    @NotEmpty //dizer q o campo do "rg" não poderá ficar vazio
    private String rg;

    @NotEmpty //dizer q o campo do "nome" não poderá ficar vazio
    private String nomeConvidado;

    //O relacionamento entre tabelas precisa ser feito nas 2 tabelas, ou seja, tbm fazer la na classe "Evento"
    @ManyToOne //"muitos convidados para um evento", seria essa a leitura da relação entre essas 2 tabelas usando essa annotation de "muitos pra um"
    private Evento evento; //criando uma variavel do tipo classe Evento, sendo ela a classe q é a tabela Evento, e o q será feito aqui é o relacionamento dessa tabela Convidado com essa tabela Evento usando a annotation acima.

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNomeConvidado() {
        return nomeConvidado;
    }

    public void setNomeConvidado(String nomeConvidado) {
        this.nomeConvidado = nomeConvidado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
