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

	private static final String COMANDO_SQL_SELECT = "SELECT Id_Apresentacao, Titulo, Resumo, Categoria, DATE_FORMAT(Data, '%d/%m/%Y'), Situacao FROM Apresentacao";
	private static final String COMANDO_SQL_INSERT = "INSERT INTO Apresentacao(Titulo, Resumo, Categoria, Data, Situacao) values (?, ?, ?, ?, ?)";
	private static final String COMANDO_SQL_UPDATE = "UPDATE Avaliacao SET Titulo = ?, Resumo = ?, Categoria = ?, Data = ?, Situacao = ?  WHERE Id_Apresentacao = ?";
	private static final String COMANDO_SQL_DELETE = "DELETE FROM Apresentacao WHERE Id_Apresentacao = ?";
	private static final String COMANDO_SQL_SELECT_BUSCA_TITULO = "SELECT a.Id_Apresentacao,  a.Titulo, CONVERT((avg (av.Nota_Conteudo) * 0.6), DECIMAL(20,2)),"
			+ " CONVERT((avg(av.Nota_Inovacao) * 0.3), DECIMAL(20,2)), CONVERT((avg(av.Nota_Apresentacao) * 0.1), DECIMAL(20,2))"
			+ " FROM avaliacao AS av"
			+ " INNER JOIN apresentacao AS a ON av.Apresentacao_Id = a.Id_Apresentacao"
			+ " WHERE av.Restricao = 1"
			+ " AND a.Titulo LIKE ?"
			+ " GROUP BY a.Titulo";
	private static final String COMANDO_SQL_SELECT_BUSCA_RESUMO = "SELECT  a.Resumo, a.Titulo, CONVERT((avg (av.Nota_Conteudo) * 0.6), DECIMAL(20,2)),"
			+ " CONVERT((avg(av.Nota_Inovacao) * 0.3), DECIMAL(20,2)), CONVERT((avg(av.Nota_Apresentacao) * 0.1), DECIMAL(20,2))"
			+ " FROM avaliacao AS av"
			+ " INNER JOIN apresentacao AS a ON av.Apresentacao_Id = a.Id_Apresentacao"
			+ " WHERE av.Restricao = 1"
			+ " AND a.Resumo LIKE ?"
			+ " GROUP BY a.Resumo";
	private static final String COMANDO_SQL_SELECT_BUSCA_AUTOR = "SELECT autor.Nome, apre.Titulo, CONVERT((avg (av.Nota_Conteudo) * 0.6), DECIMAL(20,2)),"
			+ 	" CONVERT((avg(av.Nota_Inovacao) * 0.3), DECIMAL(20,2))," 
			+	" CONVERT((avg(av.Nota_Apresentacao) * 0.1), DECIMAL(20,2))"
			+	" FROM avaliacao AS av"
			+	" INNER JOIN autor_apresentacao AS a ON av.Apresentacao_Id = a.Apresentacao_Id"
			+	" INNER JOIN apresentacao AS apre ON apre.Id_Apresentacao = a.Apresentacao_Id"
			+	" INNER JOIN autor on autor.Id_Autor = a.Autor_Id"
			+   " WHERE av.Restricao = 1"
			+   " AND autor.Nome LIKE ?"
			+   " GROUP BY autor.Nome";
	
	
	/**
	 * Adiciona uma apresentacao no banco
	 * 
	 * @param apresentacao
	 *            Apresentacao a ser inserida no banco
	 */
	public void inserirApresentacao(Apresentacao apresentacao) {

		jdbcTemplate.update(COMANDO_SQL_INSERT, apresentacao.getTitulo(),
				apresentacao.getResumo(), apresentacao.getCategoria().name(),
				apresentacao.getData(), apresentacao.getSituacao().name());

		Integer idApresentacaoGerada = jdbcTemplate.queryForObject(
				"SELECT LAST_INSERT_ID()", Integer.class);
		apresentacao.setId(idApresentacaoGerada);
	}

	/**
	 * Altera uma apresentacao no banco
	 * 
	 * @param id
	 *            Id da apresentacao a ser alterada
	 * @param apresentacao
	 *            Apresentacao a ser substituida
	 */
	public void alteraApresentacao(int id, Apresentacao apresentacao) {
		jdbcTemplate.update(COMANDO_SQL_UPDATE, apresentacao.getTitulo(),
				apresentacao.getResumo(), apresentacao.getCategoria(),
				apresentacao.getData(), apresentacao.getSituacao(), id);
	}

	/**
	 * Apaga uma apresentacao do banco
	 * 
	 * @param id
	 *            Id da apresentacao a ser excluida
	 */
	public void deletaApresentacao(int id) {
		jdbcTemplate.update(COMANDO_SQL_DELETE, id);
	}

	/**
	 * Busca todas as apresentacoes do banco
	 * 
	 * @return apresentacoes
	 */
	public List<Apresentacao> consultaApresentacoes() {
		List<Apresentacao> apresentacoes = jdbcTemplate.query(
				COMANDO_SQL_SELECT, (ResultSet results, int rowNum) -> {

					Apresentacao apresentacao = new Apresentacao();

					apresentacao.setId(results.getInt("Id_Apresentacao"));
					apresentacao.setTitulo(results.getString("Titulo"));
					apresentacao.setResumo(results.getString("Resumo"));
					apresentacao.setCategoria(Categoria.valueOf(results
							.getString("Categoria")));
					apresentacao.setData(results.getString("DATE_FORMAT(Data, '%d/%m/%Y')"));
					apresentacao.setSituacao(Situacao.valueOf(results
							.getString("Situacao")));
					return apresentacao;
				});

		return apresentacoes;

	}

	public List<Avaliacao> buscaTitulo(String titulo) {
		return jdbcTemplate.query(
				COMANDO_SQL_SELECT_BUSCA_TITULO,
				new RowMapper<Avaliacao>() {
					public Avaliacao mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Avaliacao avaliacao = new Avaliacao();
						double mediaConteudo = rs.getDouble("CONVERT((avg (av.Nota_Conteudo) * 0.6), DECIMAL(20,2))");
						double mediaInovacao = rs.getDouble("CONVERT((avg(av.Nota_Inovacao) * 0.3), DECIMAL(20,2))");
						double mediaApresentacao = rs.getDouble("CONVERT((avg(av.Nota_Apresentacao) * 0.1), DECIMAL(20,2))");
						
						avaliacao.setTituloApresentacao(rs.getString("a.Titulo"));
						avaliacao.setMediaFinal(mediaConteudo + mediaInovacao + mediaApresentacao);
						return avaliacao;
					}
				},  "%" + titulo + "%");
	}
	
	public List<Avaliacao> buscaResumo(String resumo) {
		return jdbcTemplate.query(
				COMANDO_SQL_SELECT_BUSCA_RESUMO,
				new RowMapper<Avaliacao>() {
					public Avaliacao mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Avaliacao avaliacao = new Avaliacao();
						double mediaConteudo = rs.getDouble("CONVERT((avg (av.Nota_Conteudo) * 0.6), DECIMAL(20,2))");
						double mediaInovacao = rs.getDouble("CONVERT((avg(av.Nota_Inovacao) * 0.3), DECIMAL(20,2))");
						double mediaApresentacao = rs.getDouble("CONVERT((avg(av.Nota_Apresentacao) * 0.1), DECIMAL(20,2))");
						
						avaliacao.setResumoApresentacao(rs.getString("a.Resumo"));
						avaliacao.setTituloApresentacao(rs.getString("a.Titulo"));
						avaliacao.setMediaFinal(mediaConteudo + mediaInovacao + mediaApresentacao);
						return avaliacao;
					}
				},  "%" + resumo + "%");
	}
	
	public List<Avaliacao> buscaAutor(String autor) {
		return jdbcTemplate.query(
				COMANDO_SQL_SELECT_BUSCA_AUTOR,
				new RowMapper<Avaliacao>() {
					public Avaliacao mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Avaliacao avaliacao = new Avaliacao();
						double mediaConteudo = rs.getDouble("CONVERT((avg (av.Nota_Conteudo) * 0.6), DECIMAL(20,2))");
						double mediaInovacao = rs.getDouble("CONVERT((avg(av.Nota_Inovacao) * 0.3), DECIMAL(20,2))");
						double mediaApresentacao = rs.getDouble("CONVERT((avg(av.Nota_Apresentacao) * 0.1), DECIMAL(20,2))");
						
						avaliacao.setAutor(rs.getString("autor.Nome"));
						avaliacao.setTituloApresentacao(rs.getString("apre.Titulo"));
						avaliacao.setMediaFinal(mediaConteudo + mediaInovacao + mediaApresentacao);
						return avaliacao;
					}
				},  "%" + autor + "%");
	}

}
