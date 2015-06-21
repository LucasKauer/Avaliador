package avaliador.model;

public class Avaliacao extends Entidade {
	private String comentarioGeral;
	private String criticaTecnica;
	private int notaConteudo;
	private int notaInovacao;
	private int notaApresentacao;
	private boolean restricao;
	private double mediaFinal;
	
	// FK
	private int avaliadorId;
	private int apresentacaoId;
	private String tituloApresentacao;
	private String resumoApresentacao;
	private String autor;

	public Avaliacao() {
		
	}
	
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

	public String getComentarioGeral() {
		return comentarioGeral;
	}

	public void setComentarioGeral(String comentarioGeral) {
		this.comentarioGeral = comentarioGeral;
	}

	public String getCriticaTecnica() {
		return criticaTecnica;
	}

	public void setCriticaTecnica(String criticaTecnica) {
		this.criticaTecnica = criticaTecnica;
	}

	public int getAvaliadorId() {
		return avaliadorId;
	}

	public void setAvaliadorId(int avaliadorId) {
		this.avaliadorId = avaliadorId;
	}

	public int getApresentacaoId() {
		return apresentacaoId;
	}

	public void setApresentacaoId(int apresentacaoId) {
		this.apresentacaoId = apresentacaoId;
	}
		
	public double getMediaFinal() {
		return mediaFinal;
	}

	public void setMediaFinal(double mediaFinal) {
		this.mediaFinal = mediaFinal;
	}

	public String getTituloApresentacao() {
		return tituloApresentacao;
	}

	public void setTituloApresentacao(String tituloApresentacao) {
		this.tituloApresentacao = tituloApresentacao;
	}

	public String getResumoApresentacao() {
		return resumoApresentacao;
	}

	public void setResumoApresentacao(String resumoApresentacao) {
		this.resumoApresentacao = resumoApresentacao;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
}
