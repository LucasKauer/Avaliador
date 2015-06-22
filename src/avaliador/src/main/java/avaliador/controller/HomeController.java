package avaliador.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import avaliador.dao.ApresentacaoDao;
import avaliador.model.Avaliacao;
import avaliador.model.UsuarioAvaliador;

@Controller
public class HomeController {
	
	@Inject
	ApresentacaoDao apresentacaoDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		UsuarioAvaliador usuario = (UsuarioAvaliador) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
			model.addAttribute("loginUsuario", session.getAttribute("usuarioLogado"));
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}
		return "FINAL_INDEX";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeLogado(Model model, HttpSession session) {
		UsuarioAvaliador usuario = (UsuarioAvaliador) session.getAttribute("usuarioLogado");
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
	
	@ResponseBody
	@RequestMapping(value = "/buscar-titulo", method = RequestMethod.POST)
	public List<Avaliacao> buscarTitulo(Model model,  @RequestParam String titulo) {
		return apresentacaoDao.buscaTitulo(titulo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscar-resumo", method = RequestMethod.POST)
	public List<Avaliacao> buscarResumo(Model model,  @RequestParam String resumo) {
		return apresentacaoDao.buscaResumo(resumo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/buscar-autor", method = RequestMethod.POST)
	public List<Avaliacao> buscarAutor(Model model,  @RequestParam String autor) {
		return apresentacaoDao.buscaAutor(autor);
	}
}