package avaliador.model;

public class Usuario extends Entidade {
	private String login;
	private String senha;
	private NivelUsuario nivelUsuario;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public NivelUsuario getTipoUsuario() {
		return nivelUsuario;
	}

	public void setTipoUsuario(NivelUsuario tipoUsuario) {
		this.nivelUsuario = tipoUsuario;
	}
}
