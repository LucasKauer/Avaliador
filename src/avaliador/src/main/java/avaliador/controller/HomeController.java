package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.ApresentacaoDao;
import avaliador.model.Apresentacao;



@Controller
public class HomeController {
	
	@Inject
	ApresentacaoDao apresentacaoDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		return "index";
	}
	
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public String buscar(Model model, Apresentacao apresentacao) {
		model.addAttribute("resposta", apresentacaoDao.buscaHome(apresentacao.getTitulo(), apresentacao.getResumo()));
		return "lista-busca";
	}
}