package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
	ApresentacaoDao dao;
	
	@RequestMapping(value = "/cadastro-apresentacao", method = RequestMethod.GET)
	public String cadastrarApresentacao(Model model, HttpSession session) {
		model.addAttribute("categoria", Categoria.values());
		model.addAttribute("situacao", Situacao.values());
		return "cadastro-apresentacao";
	}
	
	@RequestMapping(value = "/salvar-apresentacao", method = RequestMethod.POST)
	public String salvaApresentacao(Apresentacao apresentacao) {
		dao.inserirApresentacao(apresentacao);
		return "cadastro-apresentacao";
	}
	
	@RequestMapping(value = "/listar-apresentacao", method = RequestMethod.GET)
	public String listarApresentacao(Model model) {
		model.addAttribute("apresentacao", dao.consultaApresentacoes());
		return "lista-apresentacao";
	}
}
