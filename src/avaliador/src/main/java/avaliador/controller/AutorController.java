package avaliador.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.AutorDao;
import avaliador.model.Autor;

@Controller
public class AutorController {

	@Inject
	AutorDao dao;
	
	@RequestMapping(value = "/salvar-autor", method = RequestMethod.POST)
	public String salvarAutor(Autor autor) {
		dao.inseriAutor(autor);
		return "cadastro-avaliacao";
	}
}
