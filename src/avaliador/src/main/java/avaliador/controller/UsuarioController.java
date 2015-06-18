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
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}	
		return "login";
	}
	
	@RequestMapping(value = "/autenticar-usuario", method = RequestMethod.POST)
	public String autenticarUsuario(HttpSession session, Model model, Usuario usuarioRecebido) {
		Usuario usuarioRetornado = usuarioDao.validarUsuario(usuarioRecebido.getLogin(), usuarioRecebido.getSenha());
		if(usuarioRetornado != null) {
			session.setAttribute("usuarioLogado", usuarioRetornado);
			if (usuarioRetornado.getTipoUsuario() == NivelUsuario.ADMINISTRADOR) {
				session.setAttribute("ehAdministrador", usuarioRetornado);
			}
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.GET)
	public String cadastrarUsuario(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}
		return "cadastrar-usuario";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/";
	}
}