package com.curso.eventoapp.repository;

import com.curso.eventoapp.models.Convidado;
import com.curso.eventoapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> { //msm coisa feita pra entidade Evento, pra q seja possivel usar comandos CRUD no banco de dados de forma mais simples.
    Iterable<Convidado> findByEvento (Evento evento); //método de uma lista de convidados feita através do evento recebido por parametro, método usado na classe "EventoController"
    Convidado findByRg(String rg); //método pra realizar uma busca especifica por RG, pra ser usado no método "deletarConvidado" da classe "EventoControlller", assim buscando um convidado especifico pelo seu rg e podendo apagar ele da lista.
}
