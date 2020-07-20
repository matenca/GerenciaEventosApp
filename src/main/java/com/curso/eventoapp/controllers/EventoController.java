package com.curso.eventoapp.controllers;

import com.curso.eventoapp.models.Convidado;
import com.curso.eventoapp.models.Evento;
import com.curso.eventoapp.repository.ConvidadoRepository;
import com.curso.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    //annotation q realiza a injeção de dependencias, ou seja, toda vez q a gente precisar dessa interface será criado uma nova instancia dela automaticamente, sendo gerenciada e destruida tbm pelo spring
    private EventoRepository er; //atributo do tipo "Interface", sendo ela a interface EventoRepository

    //injeção de dependencias é necessaria sempre q criado uma interface estendendo CrudRepository, assim criando uma variavel dessa interface
    @Autowired
    private ConvidadoRepository cr;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET) //adicionado um novo parametro "method" então precisou especificar dando nome aos 2 parametros sendo eles "value" q é o endereço da URL q é necessario digitar no localhost, e "method" q significa q esse método será um GET
    public String form() {
        return "formEvento"; //retorna o arquivo HTML "formEvento"
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST) //msm coisa do de cima, porem aqui agora sendo um POST
    public String form( @Valid Evento evento, BindingResult result, RedirectAttributes attributes) { //método para o POST
        if(result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "veriffique os campos!"); //msm processo feito pra "detalhesEvento" tanto aqui na request POST dele qt na view, pra assim aplicar regras de validação antes de salvar no banco de dados tanto na adição de convidados qt na criação de um novo evento.
            return "redirect:/cadastrarEvento";
        }

        er.save(evento); //usando a variavel criada com o tipo EventoRepository, com esse método de "save(evento)" irá salvar o evento no banco de dados
        attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
        return "redirect:/cadastrarEvento"; //retorna redirecionando novamente pra URL "/cadastrarEvento"
    }
    //requisição/método pra realizar a busca de todos os eventos existentes no banco de dados e mostrar todos em uma lista na tela.
    @RequestMapping("/eventos") //requisição para realizar busca no banco de dados de todos eventos e mostrar na View
    public ModelAndView listaEventos() {
        ModelAndView mv = new ModelAndView("index"); //com esse objeto é passado por parametro a tela q ele vai renderizar de acordo com os dados do evento
        Iterable<Evento> eventos = er.findAll(); //utilização do "Iterable" por ser uma lista de eventos, assim o Iterable recebe o método "findAll" da variavel "er" da interface EventoRepository, onde esse método irá buscar todos os eventos pra lista de eventos
        mv.addObject("eventos", eventos); //utilização desse objeto "ModelAndView" criado acima com seu método "addObject" para mostrar a lista de eventos na view, onde o "attributeName" é o nome colocado no index.html "<div th:each="evento : ${eventos}">" dentro da chaves com o "$", e o attributeValue é o nome dado pro Iterable, no caso "eventos"
        return mv; //retorna o objeto "mv" da classe ModelAndView q é o q vai renderizar a tela da view com a lista de eventos, por isso o retorno após o "public" tem q ser tbm "ModelAndView" pelo return ser mv.
    }

    //requisição/método pra realizar a busca de um unico evento especifico no banco de dados e mostrar os detalhes dele na tela.
    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET) // aqui será retornado o codigo do evento, então qd for clicado em cima do nome do evento vai ser redirecionado pro respectivo codigo do evento e então mostrará os detalhes dele.
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) { //annotation @PathVariable pelo q entendi serve pra obter o valor q é passado diretamente na URL q está determinado na Request entre "{", nesse caso servindo pra obter o codigo, alem disso o codigo será recebido por parametro sendo um "long" e através desse codigo será feito uma busca no banco de dados, método necessario pra essa busca por evento especifico ta na interface "EventoRepository".
        Evento evento = er.findByCodigo(codigo); //instanciando um objeto da classe Evento/criando uma variavel do tipo Evento, e então sendo aplicado nele o método do atributo "er" q vem da Interface "EventoRepository" onde foi criado esse método "findByCodigo" pra realizar a busca por um evento especifico no banco de dados e então armazenar na variavel evento, o codigo recebido por parametro é o q é passado e recebido no parametro do findByCodigo.
        ModelAndView mv = new ModelAndView("detalhesEvento"); // msm explicação da utilização do ModelAndView no método acima.
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = cr.findByEvento(evento); //fazendo uma lista de convidados utilizando o método criado na interface "ConvidadoRepository" pra então fazer a busca no banco de dados por evento, e o parametro passado aqui é o "evento" ja existente dentro desse método que recebeu o codigo, assim especificando qual evento será.
        mv.addObject("convidados", convidados); //enviando a lista de convidados pra view.

        return mv;
    }

    //deletar evento
    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo) { //método para deletar um evento existente.
        Evento evento = er.findByCodigo(codigo); // instanciando um objeto da classe Evento/criando uma variavel do tipo Evento, e então sendo aplicado nele o método do atributo "er" q vem da Interface "EventoRepository" onde foi criado esse método "findByCodigo" pra realizar a busca por um evento especifico no banco de dados e então armazenar na variavel evento, o codigo recebido por parametro é o q é passado e recebido no parametro do findByCodigo.
        er.delete(evento); //método para deletar o evento encontrado pelo método acima utilizando o codigo passado por parametro.
        return "redirect:/eventos";
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST) //método para salvar convidados em um evento existente
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) { //nome do método necessario ser diferente do de cima (diferença entre o get e o set) nesse método então ficou "detalhesEventoPost", nome igual da erro. annotation @Valid, interface BindingResult e RedirectAttributes são parametros recebidos pra realizar a validação, utilizados na linha a baixo.
        if(result.hasErrors()) { // método da interface BindingResult "hasErrors", caso de algum erro na validação entrará nesse if.
            attributes.addFlashAttribute("mensagem", "Verifique os campos!"); //método da interface RedirectAttributes "addFlashAttribute" serve pra mostrar alguma mensagem na tela(view), nesse caso se tiver algum erro e entre nesse if então com esse método será mostrado uma mensagem de erro pra verificar os campos.
            return "redirect:/{codigo}"; //após a mensagem de erro será redirecionado pra msm pagina novamente.
        }
        Evento evento = er.findByCodigo(codigo); // criação de um objeto "Evento" e ele vai buscar o codigo q ta sendo recebido por parametro
        convidado.setEvento(evento); //passando o evento q foi encontrado de acordo com o codigo q foi passado como parametro
        cr.save(convidado); //apartir daqui o convidado ja pd estar sendo salvo no banco de dados, e é o q ta fazendo aqui.
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!"); //msm método usado dentro do if acima, porem aqui agora depois de conseguido salvar um convidado no banco de dados será mostrado essa mensagem de sucesso
        return "redirect:/{codigo}";
    }

    //deletar convidado
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg) {  //método pra apagar convidados de um evento,  identificando ele através do "rg"
        Convidado convidado = cr.findByRg(rg); //variavel de "Convidado" recebendo o método "findByRg(rg)" do atributo "cr" q veio da interface "ConvidadoRepository", nela foi criado esse método pra fazer a busca por um determinado convidado através do seu rg, e colocando esse convidado na variavel convidado.
        cr.delete(convidado); // usando o método "delete(convidado)" do atributo "cr" da interface "ConvidadoRepository", e com ele deletando o convidado q foi colocado na variavel convidado.

        //apartir daqui é pra poder retornar a lista de convidados atualizada
        Evento evento = convidado.getEvento(); //variavel da classe "Evento" recebendo o evento q determinado convidado faz parte, através do método "getEvento()" pegando o evento daquele convidado.
        long codigoLong = evento.getCodigo(); //variavel criada aqui "codigoLong" (por estar recebendo um codigo q é do tipo long), recebendo o codigo do evento q foi identificado no método acima, através do método "getCodigo()" pegando o codigo daquele evento.
        String codigo = "" + codigoLong; //necessario passar o codigo de long para String antes de poder dar return, aqui a variavel codigo do tipo String recebeu "" vazias + codigoLong e assim foi passado long pra String (acredito q seja um tipo de gambiarra rapida pra converter pra String).
        return "redirect:/" + codigo; // retorna o redirecionamento
    }
}
