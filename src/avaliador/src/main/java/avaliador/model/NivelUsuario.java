package avaliador.model;

public enum NivelUsuario {
	COMUM("Comum"), AVALIADOR("Avaliador"), ADMINISTRADOR("Administrador");
	
	private String descricao;
	
	private NivelUsuario(String descricao) {
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
