package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import avaliador.dao.AutorApresentacaoDao;
import avaliador.dao.AutorDao;
import avaliador.model.Autor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.ApresentacaoDao;
import avaliador.model.Apresentacao;
import avaliador.model.Categoria;
import avaliador.model.Situacao;

@Controller
public class ApresentacaoController {
	
	@Inject
	ApresentacaoDao apresentacaoDao;


    @Inject
    AutorDao autorDao;

    @Inject
    AutorApresentacaoDao autorApresentacaoDao;
	
	@RequestMapping(value = "/cadastro-apresentacao", method = RequestMethod.GET)
	public String cadastrarApresentacao(Model model, HttpSession session) {
		model.addAttribute("categoria", Categoria.values());
		model.addAttribute("situacao", Situacao.values());
		return "cadastroApresentacao";
	}
	
	@RequestMapping(value = "/salvar-apresentacao", method = RequestMethod.POST)
	public String salvaApresentacao(Apresentacao apresentacao) {
        apresentacaoDao.inserirApresentacao(apresentacao);
        for (Autor autor : apresentacao.getAutor()) {
            autor.setApresentacao(apresentacao);
            autorDao.inseriAutor(autor);
        }

		return "redirect:/lista-apresentacao";
	}
	
	@RequestMapping(value = "/lista-apresentacao", method = RequestMethod.GET)
	public String listarApresentacao(Model model) {
		model.addAttribute("apresentacao", apresentacaoDao.consultaApresentacoes());
		return "listaApresentacao";
	}
}
