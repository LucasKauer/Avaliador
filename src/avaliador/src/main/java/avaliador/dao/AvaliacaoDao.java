package avaliador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import avaliador.model.Avaliacao;

@Component
public class AvaliacaoDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	private final String COMANDO_SQL_INSERT = "INSERT INTO AVALIACAO (Comentario_Geral, Critica_Tecnica, Nota_Conteudo, Nota_Inovacao, Nota_apresentacao, Restricao, Avaliador_Id, Apresentacao_Id) VALUES (?, ?, ?, ?, ?, ?, 1, ?)";
	private final String COMANDO_SQL_UPDATE = "UPDATE AVALIACAO SET Comentario_Geral = ?, Critica_Tecnica = ?, Nota_Conteudo = ?, Nota_Inovacao = ?, Nota_apresentacao = ?, Restricao = ? WHERE Id_Avaliacao = ?";
	private final String COMANDO_SQL_DELETE = "DELETE FROM AVALIACAO WHERE Id_Avaliacao = ?";
	private final String COMANDO_SQL_SELECT = "SELECT Id_Avaliacao, Comentario_Geral, Critica_Tecnica, Nota_Conteudo, Nota_Inovacao, Nota_Apresentacao, Restricao FROM AVALIACAO";
	
	private static final String COMANDO_SQL_BUSCA_AVALIACAO = "SELECT Nota_Conteudo, Nota_Inovacao, Nota_Apresentacao, Avaliador_Id FROM Avaliacao WHERE Apresentacao_Id = ";

	/**
	*  Adiciona Avaliacao no banco
	* @param avaliacao Avaliacao a ser inserida no banco
	*/
	public void inserirAvaliacao(Avaliacao avaliacao) {
		jdbcTemplate.update(
				COMANDO_SQL_INSERT,
				avaliacao.getComentarioGeral(),
				avaliacao.getCriticaTecnica(),
				avaliacao.getNotaConteudo(),
				avaliacao.getNotaInovacao(),
				avaliacao.getNotaApresentacao(),
				avaliacao.isRestricao(),
				avaliacao.getApresentacaoId());
	}
	
	/**
	 * Consulta Avaliacao no banco
	 * */
	public List<Avaliacao> consultaAvaliacao() {
		return jdbcTemplate
				.query(COMANDO_SQL_SELECT,
						new RowMapper<Avaliacao>() {
							public Avaliacao mapRow(ResultSet rs, int arg1)
									throws SQLException {
								Avaliacao avaliacao = new Avaliacao();
								avaliacao.setId(rs.getInt("id"));
								avaliacao.setComentarioGeral(rs.getString("Comentario_Geral"));
								avaliacao.setCriticaTecnica(rs.getString("Critica_Tecnica"));
								avaliacao.setNotaConteudo(rs.getInt("Nota_Conteudo"));
								avaliacao.setNotaInovacao(rs.getInt("Nota_Inovacao"));
								avaliacao.setNotaApresentacao(rs.getInt("Nota_Apresentacao"));
								avaliacao.isRestricao();
								return avaliacao;
							}

						});
	}
	
	/**
	 * Edita Avaliacao no banco
	 * @param idAvaliacao Avaliacao a ser editada
	 * @param avaliacao Avaliacao com todos as informações
	 * */
	public void editarAvaliacao(int idAvaliacao, Avaliacao avaliacao){
		jdbcTemplate.update(COMANDO_SQL_UPDATE,
				idAvaliacao, avaliacao.getNotaConteudo(),
				avaliacao.getComentarioGeral(),
				avaliacao.getCriticaTecnica(),
				avaliacao.getNotaInovacao(),
				avaliacao.getNotaApresentacao(),
				avaliacao.isRestricao());
	}
	
	/**
	 * Excluir Avaliacao no banco
	 * @param idAvaliacao Avaliacao a ser editada
	 * */
	public void excluirAvaliacao(int idAvalicao) {
		jdbcTemplate.update(COMANDO_SQL_DELETE,
				idAvalicao);
	}
	
	public List<Avaliacao> buscaAvaliacaoPassandoIdApresentacao(int idApresentacao) {
		List<Avaliacao> avaliacoes = jdbcTemplate.query(COMANDO_SQL_BUSCA_AVALIACAO + idApresentacao, (ResultSet results,
				int rowNum) -> {
					
			Avaliacao avaliacao = new Avaliacao();
			
			avaliacao.setNotaApresentacao(results.getInt("Nota_Apresentacao"));
			avaliacao.setNotaConteudo(results.getInt("Nota_Conteudo"));
			avaliacao.setNotaInovacao(results.getInt("Nota_Inovacao"));
			avaliacao.setAvaliadorId(results.getInt("Avaliador_Id"));
			
			return avaliacao;
		});		
		
		return avaliacoes;
	}

}