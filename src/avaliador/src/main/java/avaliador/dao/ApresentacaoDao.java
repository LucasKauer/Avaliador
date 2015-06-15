package avaliador.dao;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import avaliador.model.Apresentacao;

@Controller
public class ApresentacaoDao {
	
	private final String COMANDO_SQL_INSERT = "INSERT INTO Apresentacao(TITULO, RESUMO, CATEGORIA, DATA, SITUACAO) values (?, ?, ?, ?, ?)";
	private final String COMANDO_SQL_UPDATE = "UPDATE Avaliacao SET TITULO = ?, RESUMO = ?, CATEGORIA = ?, DATA = ?, SITUACAO = ?  WHERE id = ?";
	private final String COMANDO_SQL_DELETE = "DELETE FROM Apresentacao WHERE Id_Apresentacao = ?";
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Adiciona uma apresentacao no banco
	 * @param apresentacao Apresentacao a ser inserida no banco
	 */
	public void inserirApresentacao(Apresentacao apresentacao) {
		jdbcTemplate.update(COMANDO_SQL_INSERT,
				apresentacao.getTitulo(),
				apresentacao.getResumo(),
				apresentacao.getCategoria(),
				apresentacao.getData(),
				apresentacao.getSituacao());
	}
	
	/**
	 * Altera uma apresentacao no banco
	 * @param id Id da apresentacao a ser alterada
	 * @param apresentacao Apresentacao a ser substituida
	 */
	public void alteraApresentacao(int id, Apresentacao apresentacao) {
		jdbcTemplate.update(COMANDO_SQL_UPDATE,
				apresentacao.getTitulo(),
				apresentacao.getResumo(),
				apresentacao.getCategoria(),
				apresentacao.getData(),
				apresentacao.getSituacao(),
				id);
	}
	
	
	/**
	 * Apaga uma apresentacao do banco
	 * @param id Id da apresentacao a ser excluida
	 */
	public void deletarApresentacao(int id) {
		jdbcTemplate.update(COMANDO_SQL_DELETE,
				id);
	}
}
