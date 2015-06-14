package avaliador.model;

public enum Situacao {
	AGENDADA("Agendada"), AVALIADA("Avaliada"), CANCELADA("Cancelada");
	
	private String descricao;
	
	private Situacao(String descricao) {
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
