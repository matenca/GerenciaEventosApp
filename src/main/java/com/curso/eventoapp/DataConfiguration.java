package com.curso.eventoapp;

import org.hibernate.Hibernate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration //annotation usada pra qd será uma classe de configuração de algo
public class DataConfiguration {

    //bean de conexão com o banco de dados
    @Bean //explicação sobre bean na pasta do curso da michelli
    public DataSource dataSource() { //criação de um método q na implementação dele é instanciado uma classe externa pra a configuração do banco de dados, e por isso a utilização do @Bean ja q é uma classe externa.
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/eventosapp"); //URL do banco de dados
        dataSource.setUsername("root"); //usuario realmente utilizado MySQL pra q a conexão aconteça
        dataSource.setPassword(""); //msm coisa q usuario
        return dataSource; //retorna o objeto instanciado
    }

    //bean pra criação de uma conexão com o hibernate
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL); //qual banco de dados vai ser utilizado
        adapter.setShowSql(true); //se estiver true, na hora de realizar alguma ação no banco de dados sempre será mostrado o passo a passo no console daquilo q foi realizado.
        adapter.setGenerateDdl(true); //se estiver true, o hibernate vai criar as tabelas automaticamente
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect"); //colocado o dialeto q será utilizado para o hibernate, no caso MySQL5InnoDBDialect, no caso usando InnoDB é a engine mais atual sendo a q suporta "ACID" e q tbm suporta FK
        adapter.setPrepareConnection(true); //tando "true", o hibernate estará preparado para realizar uma conexão automaticamente
        return adapter; //retorna o objeto instanciado
    }
}
