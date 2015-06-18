package avaliador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import avaliador.model.Apresentacao;
import avaliador.model.Avaliacao;
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
	private static final String COMANDO_SQL_SELECT_BUSCA = "SELECT Titulo, Resumo FROM apresentacao WHERE Titulo LIKE ?";
	private static final String COMANDO_SQL_SELECT_BUSCA_AVALIACAO_INDIVIDUAL =
	"SELECT av.Comentario_Geral, av.Critica_Tecnica, av.Nota_Apresentacao, av.Nota_Conteudo, av.Nota_Inovacao"
	+ " FROM avaliacao AS av INNER JOIN apresentacao AS a ON av.Apresentacao_Id = a.Id_Apresentacao"
	+ " WHERE a.Titulo = ?";
	
	/**
	 * Adiciona uma apresentacao no banco
	 * @param apresentacao Apresentacao a ser inserida no banco
	 */
	public void inserirApresentacao(Apresentacao apresentacao) {


		jdbcTemplate.update(COMANDO_SQL_INSERT,
				apresentacao.getTitulo(),
				apresentacao.getResumo(),
				apresentacao.getCategoria().name(),
				apresentacao.getData(),
				apresentacao.getSituacao().name());


        Integer idApresentacaoGerada = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        apresentacao.setId(idApresentacaoGerada);
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
	public void deletaApresentacao(int id) {
		jdbcTemplate.update(COMANDO_SQL_DELETE,
				id);
	}
	
	/**
	 * Busca todas as apresentacoes do banco
	 * @return apresentacoes
	 */
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
			return apresentacao;
		});		
		
		return apresentacoes;

	}
	
	public List<Apresentacao> buscaHome(String titulo) {
		return jdbcTemplate.query(COMANDO_SQL_SELECT_BUSCA,
				new RowMapper<Apresentacao>() {
					public Apresentacao mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Apresentacao apresentacao = new Apresentacao();
						apresentacao.setTitulo(rs.getString("Titulo"));
						return apresentacao;
					}
				}, '%' + titulo + '%');
	}

	public List<Avaliacao> buscaApresentacaoIndividual(String titulo) {
		return jdbcTemplate.query(COMANDO_SQL_SELECT_BUSCA_AVALIACAO_INDIVIDUAL,
				new RowMapper<Avaliacao>() {
					public Avaliacao mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Avaliacao avaliacao = new Avaliacao();
						avaliacao.setComentarioGeral(rs.getString("Comentario_Geral"));
						avaliacao.setCriticaTecnica(rs.getString("Critica_Tecnica"));
						avaliacao.setNotaConteudo(rs.getInt("Nota_Conteudo"));
						avaliacao.setNotaInovacao(rs.getInt("Nota_Inovacao"));
						avaliacao.setNotaApresentacao(rs.getInt("Nota_Apresentacao"));
						return avaliacao;
					}
				}, titulo);
	}
	
}
