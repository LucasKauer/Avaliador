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
	UsuarioDao dao;
	
	@RequestMapping(value = "/entrar", method = RequestMethod.GET)
	public String entrar(Model model, HttpSession session) {
		return "login";
	}
	
	@RequestMapping(value = "/valida", method = RequestMethod.POST)
	public String validarUsuario(Usuario usuarioRecebido) {
		Usuario usuarioRetornado = dao.validarUsuario(usuarioRecebido.getLogin(), usuarioRecebido.getSenha());
		if(usuarioRetornado!=null){
			if (usuarioRetornado.getTipoUsuario() == NivelUsuario.ADMINISTRADOR){
				return "";
			}
			else{
				return "";
			}
		}else{
			return "login";
		}
	}
	
	@RequestMapping(value = "/cadastrar-usuario", method = RequestMethod.GET)
	public String cadastrarusuario(Model model, HttpSession session) {
		return "cadastrar-usuario";
	}
}