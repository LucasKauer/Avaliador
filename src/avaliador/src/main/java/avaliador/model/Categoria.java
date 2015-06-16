package avaliador.model;

public enum Categoria {
	TRABALHO_CIENTIFICO("Trabalho Científico"), POWER_POINT("Power Point"), TRABALHO_DE_CONCLUSAO("Trabalho de Conclusão");
	
	private String descricao;
	
	private Categoria(String descricao) {
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
