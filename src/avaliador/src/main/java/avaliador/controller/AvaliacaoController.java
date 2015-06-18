package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import avaliador.dao.ApresentacaoDao;
import avaliador.dao.AvaliacaoDao;
import avaliador.model.Avaliacao;

@Controller
public class AvaliacaoController {
	
	@Inject
	AvaliacaoDao avaliacaoDao;
			
	@Inject
	ApresentacaoDao apresentacaoDao;
	
	@RequestMapping(value = "/cadastro-avaliacao", method = RequestMethod.GET)
	public String cadastrarAvaliacao(Model model, HttpSession session) {
		model.addAttribute("loginUsuario", session.getAttribute("usuarioLogado"));
		model.addAttribute("listaApresentacoes", apresentacaoDao.consultaApresentacoes());
		return "cadastroAvaliacao";
	}
	
	@RequestMapping(value = "/salvar-avaliacao", method = RequestMethod.POST)
	public String salvarAvaliacao(Avaliacao avaliacao) {
		avaliacaoDao.inserirAvaliacao(avaliacao);
		return "cadastro-avaliacao";
	}
	
	@RequestMapping(value = "/busca-avaliacao", method = RequestMethod.POST)
	public String buscaAvaliacao(@RequestParam String idApresentacao) {
		System.out.println(idApresentacao);
		//avaliacaoDao.buscaAvaliacaoPassandoIdApresentacao(idApresentacao);
		return "cadastroAvaliacao";
	}
}
