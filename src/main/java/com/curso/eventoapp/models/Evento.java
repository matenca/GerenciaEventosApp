package com.curso.eventoapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity //annotation pra transformar essa classe em uma tabela/entidade no banco de dados
public class Evento implements Serializable { //não sei pra o q exatamente serve essa interface, mas ela diz no video q pra tudo funcionar certo com essa classe q virou entidade então precisa implementar essa interface

    private static final long serialVersionUID = 1L; //deve fazer parte dessa interface implementada, ela não explicou.

    @Id //annotation pra dizer q o atributo "codigo" do tipo long é um "ID", e assim tbm transformando ele em uma PK (chave primaria)
    @GeneratedValue(strategy = GenerationType.AUTO) //annotation com esse parametro pra realizar a geração automatica do ID, seria mais ou menos igual a constraint "AUTO_INCREMENT" do MySQL, q na vdd no MySQL vai se transformar nele.
    private long codigo; //atributo "ID" com auto_increment usando a annotation de cima, e com a annotation @Id tbm transforma esse atributo em uma PK.

    @NotEmpty //necessario repetir o msm processo feito pra "detalhesEvento", tanto na view qt no EventoController, pra q essas regras de validação tbm existam na criação de um evento.
    private String nome;
    @NotEmpty
    private String local;
    @NotEmpty
    private String data;
    @NotEmpty
    private String horario;

    @OneToMany //"um evento pra muitos convidados" annotation de relacionamento de "um pra muitos"
    private List<Convidado> convidados;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
