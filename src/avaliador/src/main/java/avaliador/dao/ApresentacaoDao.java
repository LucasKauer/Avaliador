package avaliador.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import avaliador.model.Apresentacao;
import avaliador.model.Categoria;
import avaliador.model.Situacao;

@Component
public class ApresentacaoDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	private static final String COMANDO_SQL_SELECT = "SELECT Id_Apresentacao, Titulo, Resumo, Categoria, Data, Situacao FROM Apresentacao";
	private static final String COMANDO_SQL_INSERT = "INSERT INTO Apresentacao(Titulo, Resumo, Categoria, Data, Situacao) values (?, ?, ?, ?, ?)";
	private static final String COMANDO_SQL_UPDATE = "UPDATE Avaliacao SET Titulo = ?, Resumo = ?, Categoria = ?, Data = ?, Situacao = ?  WHERE Id_Apresentacao = ?";
	private static final String COMANDO_SQL_DELETE = "DELETE FROM Apresentacao WHERE Id_Apresentacao = ?";
	
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
	
	
	public List<Apresentacao> consultaApresentacoes() {
		List<Apresentacao> apresentacoes = jdbcTemplate.query(COMANDO_SQL_SELECT, (ResultSet results,
				int rowNum) -> {
					
			Apresentacao apresentacao = new Apresentacao();
			
			apresentacao.setId(results.getInt("Id_Apresentacao"));
			apresentacao.setTitulo(results.getString("Titulo"));
			apresentacao.setResumo(results.getString("Resumo"));
			apresentacao.setCategoria(Categoria.valueOf(results.getString("Categoria")));
			apresentacao.setData(results.getDate("Data"));
			apresentacao.setSituacao(Situacao.valueOf(results.getString("Situacao")));
			
			filme.setNome(results.getString("NOME"));
			filme.setGenero(Genero.valueOf(results.getString("GENERO")));
			filme.setAnoLancamento(results.getInt("ANO_LANCAMENTO"));
			filme.setDiretor(results.getString("DIRETOR"));
			filme.setSinopse(results.getString("SINOPSE"));
			filme.setImagem(results.getString("IMAGEM"));
			filme.setNota(results.getDouble("MEDIA"));
			return filme;
		});		
		
		return apresentacoes;

	}
	
	

}
