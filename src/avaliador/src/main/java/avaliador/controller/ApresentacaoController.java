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
		model.addAttribute("loginUsuario", session.getAttribute("usuarioLogado"));
		model.addAttribute("categoria", Categoria.values());
		model.addAttribute("situacao", Situacao.values());
		return "FINAL_CADASTRO_APRESENTACAO";
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
	public String listarApresentacao(Model model, HttpSession session) {
		model.addAttribute("loginUsuario", session.getAttribute("usuarioLogado"));
		model.addAttribute("apresentacao", apresentacaoDao.consultaApresentacoes());
		return "FINAL_LISTA_APRESENTACAO";
	}
}
