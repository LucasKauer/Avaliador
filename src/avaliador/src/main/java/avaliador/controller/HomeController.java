package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.ApresentacaoDao;
import avaliador.model.Apresentacao;
import avaliador.model.Usuario;

@Controller
public class HomeController {
	
	@Inject
	ApresentacaoDao apresentacaoDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}
		return "FINAL_INDEX";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeLogado(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
			model.addAttribute("loginUsuario", session.getAttribute("usuarioLogado"));
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}
		return "FINAL_HOME";
	}
	
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public String buscar(Model model, Apresentacao apresentacao) {
		model.addAttribute("resposta", apresentacaoDao.buscaHome(apresentacao.getTitulo()));
		return "lista-busca";
	}
	
	@RequestMapping(value = "/exibir-resultado", method = RequestMethod.GET)
	public String exibirResultado(Model model, Apresentacao apresentacao) {
		model.addAttribute("resposta", apresentacaoDao.buscaApresentacaoIndividual(apresentacao.getTitulo()));
		return "lista-resultado-pesquisa";
	}
}