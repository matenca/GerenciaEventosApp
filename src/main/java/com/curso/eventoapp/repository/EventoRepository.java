package com.curso.eventoapp.repository;

import com.curso.eventoapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

// interface estendendo a classe CrudRepository (Create, Read, Update, Delete), ela vai servir pra ser instanciada e então fazer a persistencia dos dados do formulario no banco de dados usando métodos prontos como salvar, insert into, create, update, delete e etc no banco de dados, facilita mt a manipulação dos dados, no parametro é definido a entidade/tabela q será utilizada.
public interface EventoRepository extends CrudRepository<Evento, String> {
    Evento findByCodigo(long codigo); //método para realizar a busca no banco de dados de um evento especifico e não de todos eventos como ja foi feito antes usando iterable (iterable por precisar pegar todos) e findAll,
}
