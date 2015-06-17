package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.UsuarioDao;
import avaliador.model.NivelUsuario;
import avaliador.model.Usuario;

@Controller
public class UsuarioController {
	
	@Inject
	UsuarioDao usuarioDao;
	
	@RequestMapping(value = "/entrar", method = RequestMethod.GET)
	public String entrar(Model model, HttpSession session) {
		return "login";
	}
	
	@RequestMapping(value = "/autenticar-usuario", method = RequestMethod.POST)
	public String autenticarUsuario(HttpSession session, Model model, Usuario usuarioRecebido) {
		Usuario usuarioRetornado = usuarioDao.validarUsuario(usuarioRecebido.getLogin(), usuarioRecebido.getSenha());
		if(usuarioRetornado != null) {	
			if (usuarioRetornado.getTipoUsuario() == NivelUsuario.ADMINISTRADOR) {
				session.setAttribute("ehAdministrador", usuarioRetornado);
				model.addAttribute("botaoSair", true);
				return "redirect:/index";
			}
			return "login";
		}
		return "login";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String telaIndex() {
		return "index";
	}
	
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.GET)
	public String cadastrarUsuario(Model model, HttpSession session) {
		return "cadastrar-usuario";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/";
	}

}