package avaliador.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws Exception {

		String uri = request.getRequestURI();
		if (uri.endsWith("/") ||
			uri.endsWith("/entrar") || // metodo que chama a tela de login
			uri.endsWith("/cadastrar-usuario") || // metodo que chama a tela de cadastro de usuario
			uri.endsWith("/autenticar-usuario") || // metodo que autentica o usuario no sistema
			uri.endsWith("head") || // view que contem o HEAD padrao
			uri.endsWith("nav") || // view que contem a NAV padrao
			uri.endsWith("scripts") || // view que contem os scripts padrao
			uri.contains("resources")) { // pacote onde estao alocadas as views 
			return true;
		}

		if (request.getSession().getAttribute("ehAdministrador") != null) {
			return true;
		} else if (request.getSession().getAttribute("usuarioLogado") != null){
			return true;
		}
		
		response.sendRedirect("/entrar");
		return false;
	}
}