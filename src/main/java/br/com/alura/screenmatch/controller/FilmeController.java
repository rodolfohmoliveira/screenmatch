package br.com.alura.screenmatch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.screenmatch.domain.filme.DadosAlteraFilme;
import br.com.alura.screenmatch.domain.filme.DadosCadastraFilme;
import br.com.alura.screenmatch.domain.filme.Filme;
import br.com.alura.screenmatch.domain.filme.FilmeRepository;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    @GetMapping("/formulario")
    public String carregaPaginaFormulario(Long id, Model model) {
        if (id != null) {
            var filme = repository.getReferenceById(id);
            model.addAttribute("filme", filme);
        }
        return "filmes/formulario";

    }

    @GetMapping
    public String carregaPaginaListagem(Model model) {
        model.addAttribute("lista", repository.findAll());
        return "filmes/listagem";

    }

    @PostMapping
    @Transactional
    public String cadastraFilm(DadosCadastraFilme dados /* ,Model model */) {
        var filme = new Filme(dados);
        repository.save(filme);
        // model.addAttribute("lista", filmes); no Spring utilizar o redirect para
        // aproveitar o metodo sem precisar repetir
        return "redirect:/filmes";

    }

    @PutMapping
    @Transactional
    public String alteraFilm(DadosAlteraFilme dados ) {
        var filme = repository.getReferenceById(dados.id());
        filme.atualizaDados(dados);

        return "redirect:/filmes";

    }

    @DeleteMapping
    @Transactional
    public String removeFilme(Long id) {

        repository.deleteById(id);
        return "redirect:/filmes";
    }

}
