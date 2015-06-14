package avaliador.model;

public enum TipoUsuario {
	COMUM("Comum"), AVALIADOR("Avaliador"), ADMINISTRADOR("Administrador");
	
	private String descricao;
	
	private TipoUsuario(String descricao) {
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
