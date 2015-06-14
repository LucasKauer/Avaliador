package avaliador.model;

public class Avaliacao extends Entidade {
	private int notaConteudo;
	private int notaInovacao;
	private int notaApresentacao;
	private boolean restricao;

	public int getNotaConteudo() {
		return notaConteudo;
	}

	public void setNotaConteudo(int notaConteudo) {
		this.notaConteudo = notaConteudo;
	}

	public int getNotaInovacao() {
		return notaInovacao;
	}

	public void setNotaInovacao(int notaInovacao) {
		this.notaInovacao = notaInovacao;
	}

	public int getNotaApresentacao() {
		return notaApresentacao;
	}

	public void setNotaApresentacao(int notaApresentacao) {
		this.notaApresentacao = notaApresentacao;
	}

	public boolean isRestricao() {
		return restricao;
	}

	public void setRestricao(boolean restricao) {
		this.restricao = restricao;
	}
}
