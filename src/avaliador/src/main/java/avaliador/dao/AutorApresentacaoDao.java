package avaliador.dao;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import avaliador.model.Autor;

@Component
public class AutorApresentacaoDao {
	
	@Inject
	JdbcTemplate jdbcTemplate;
	
	private static final String COMANDO_SQL_INSERT = "INSERT INTO Autor_Apresentacao(Apresentacao_Id, Autor_Id) values (?, ?)";
	private static final String COMANDO_SQL_UPDATE = "UPDATE Autor_Apresentacao SET Apresentacao_Id = ?, Autor_Id = ? WHERE Apresentacao_Id = ? AND Autor_Id = ?";
	private static final String COMANDO_SQL_DELETE = "DELETE FROM Autor_Apresentacao WHERE Apresentacao_Id = ? OR Autor_Id = ?";
	
	/**
	 * Adiciona um relacionamento de Autor/Aresentacao no banco
	 * @param apresentacaoId Id da apresentacao a ser inserida no banco
	 * @param autorId Id do autor a ser inserido no banco
	 */
	public void inserirAutor(int apresentacaoId, int autorId) {
		jdbcTemplate.update(COMANDO_SQL_INSERT,
				apresentacaoId,
				autorId);
	}
	
	/**
	 * Altera um relacionamento de Autor/Apresentacao no banco
	 * @param apresentacaoId Id da apresentacao a ser alterada no banco
	 * @param autorId Id do autor a ser alterada no banco
	 */
	public void alteraAutor(int apresentacaoId, int autorId) {
		jdbcTemplate.update(COMANDO_SQL_UPDATE,
				apresentacaoId,
				autorId,
				apresentacaoId,
				autorId);
	}
	
	/**
	 * Apaga um relacionamento de Autor/Apresentacao no banco
	 * @param apresentacaoId Id da apresentacao a ser apagado no banco
	 * @param autorId Id do autor a ser apagado no banco
	 */
	public void deletaAutor(int apresentacaoId, int autorId) {
		jdbcTemplate.update(COMANDO_SQL_DELETE,
				apresentacaoId,
				autorId);
	}
}
