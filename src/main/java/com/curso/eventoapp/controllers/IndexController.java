package com.curso.eventoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //necessario indicar com essa annotation q essa classe é um controller, onde sendo assim a utilização dessa annotation transforma a classe em um "bean", explicação sobre bean na pasta do curso
public class IndexController {

    @RequestMapping("/") // annotation de requisição, o parametro dela é como será a URL usada, ela com esse metodo q vai chamar o formulario e mandar pro browser, sendo ele uma View
    public String index() {
        return "index";
    }
}
