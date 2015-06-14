package avaliador.model;

public class UsuarioAvaliador extends Usuario {
	private String institucao;
	private String nome;
	private int idade;
	private String genero;

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
}
