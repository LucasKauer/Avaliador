package avaliador.model;

public class UsuarioAvaliador extends Entidade {
	private String institucao;
	private String nome;
	private int idade;
	private String genero;
	private String login;
	private String senha;

	public String getInstitucao() {
		return institucao;
	}

	public void setInstitucao(String institucao) {
		this.institucao = institucao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

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
}
