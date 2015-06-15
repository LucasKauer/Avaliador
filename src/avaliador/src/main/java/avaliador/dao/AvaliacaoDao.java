package avaliador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import avaliador.model.Avaliacao;

public class AvaliacaoDao {
	
	private final String COMANDO_SQL_INSERT = "INSERT INTO AVALIACAO (Nota_Conteudo, Nota_Inovacao, Nota_apresentacao, Restricao) VALUES (?, ?, ?, ?)";
	private final String COMANDO_SQL_UPDATE = "UPDATE AVALIACAO SET Nota_Conteudo = ?, Nota_Inovacao = ?, Nota_apresentacao = ?, Restricao = ? WHERE Id_Avaliacao = ?";
	private final String COMANDO_SQL_DELETE = "DELETE FROM AVALIACAO WHERE Id_Avaliacao = ?";
	private final String COMANDO_SQL_SELECT = "SELECT Id_Avaliacao, Nota_Conteudo, Nota_Inovacao, Nota_Apresentacao, Restricao FROM AVALIACAO";
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	/**
	*  Adiciona Avaliacao no banco
	* @param avaliacao Avaliacao a ser inserida no banco
	*/
	public void inserirAvaliacao(Avaliacao avaliacao) {
		jdbcTemplate.update(
				COMANDO_SQL_INSERT,
				avaliacao.getNotaConteudo(),
				avaliacao.getNotaInovacao(),
				avaliacao.getNotaApresentacao(),
				avaliacao.isRestricao());
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
}
