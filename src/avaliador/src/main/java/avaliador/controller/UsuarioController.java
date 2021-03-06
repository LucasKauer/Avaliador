package avaliador.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import avaliador.dao.UsuarioDao;
import avaliador.model.UsuarioAvaliador;

@Controller
public class UsuarioController {
	
	@Inject
	UsuarioDao usuarioDao;
	
	@RequestMapping(value = "/entrar", method = RequestMethod.GET)
	public String entrar(Model model, HttpSession session) {
		UsuarioAvaliador usuario = (UsuarioAvaliador) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}	
		return "FINAL_ENTRAR";
	}
	
	@RequestMapping(value = "/autenticar-usuario", method = RequestMethod.POST)
	public String autenticarUsuario(HttpSession session, Model model, UsuarioAvaliador usuarioRecebido) {
		UsuarioAvaliador usuarioRetornado = usuarioDao.validarUsuario(usuarioRecebido.getLogin(), usuarioRecebido.getSenha());
		if(usuarioRetornado != null) {
				session.setAttribute("usuarioLogado", usuarioRetornado);
				model.addAttribute("login", true);
				return "redirect:/home";
			}
			
		return "FINAL_ENTRAR";
	}
	
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.GET)
	public String cadastrarUsuario(Model model, HttpSession session) {
		UsuarioAvaliador usuario = (UsuarioAvaliador) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			model.addAttribute("exibeAutenticacao", false);
			model.addAttribute("exibeSair", true);
		} else {
			model.addAttribute("exibeAutenticacao", true);
			model.addAttribute("exibeSair", false);
		}
		return "FINAL_CADASTRAR_USUARIO";
	}
	
	@RequestMapping(value = "/salvar-usuario", method = RequestMethod.POST)
	public String salvarUsuario(HttpSession session, Model model, UsuarioAvaliador usuario) {
		usuarioDao.inserirUsuario(usuario);
		return "FINAL_ENTRAR";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/";
	}
}