package avaliador.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsuarioController {
	
	@RequestMapping(value = "/entrar", method = RequestMethod.GET)
	public String entrar(Model model, HttpSession session) {
		return "login";
	}
	
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.GET)
	public String cadastrarusuario(Model model, HttpSession session) {
		return "cadastrar-usuario";
	}
}