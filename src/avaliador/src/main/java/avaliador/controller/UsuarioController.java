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
	public String autenticarUsuario(Usuario usuarioRecebido, HttpSession session) {
		Usuario usuarioRetornado = usuarioDao.validarUsuario(usuarioRecebido.getLogin(), usuarioRecebido.getSenha());
		if(usuarioRetornado != null) {
			session.setAttribute("usuarioLogado", true);	
			if (usuarioRetornado.getTipoUsuario() == NivelUsuario.ADMINISTRADOR) {
				session.setAttribute("ehAdministrador", true);
			}
			return "redirect:/index";
		}	
		return "login";
	}
	
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.GET)
	public String cadastrarUsuario(Model model, HttpSession session) {
		return "cadastrar-usuario";
	}
}