package avaliador.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.AvaliacaoDao;
import avaliador.model.Avaliacao;

@Controller
public class AvaliacaoController {
	
	@Inject
	AvaliacaoDao dao;
	
	@RequestMapping(value = "/cadastro-avaliacao", method = RequestMethod.GET)
	public String cadastrarAvaliacao() {
		return "cadastro-avaliacao";
	}
	
	@RequestMapping(value = "/salvar-avaliacao", method = RequestMethod.POST)
	public String salvarAvaliacao(Avaliacao avaliacao) {
		dao.inserirAvaliacao(avaliacao);
		return "cadastro-avaliacao";
	}

}
