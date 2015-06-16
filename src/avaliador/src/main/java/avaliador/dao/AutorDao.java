package avaliador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import avaliador.model.Autor;


@Component
public class AutorDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	AutorApresentacaoDao apresentacaoAutor  = new AutorApresentacaoDao();
	
	private static final String COMANDO_SQL_SELECT = "SELECT Id_Autor, Nome, Genero, Email FROM Autor";
	private static final String COMANDO_SQL_INSERT = "INSERT INTO Autor(Nome, Genero, Email) values (?, ?, ?)";
	private static final String COMANDO_SQL_UPDATE = "UPDATE Autor SET Nome = ?, Genero = ?, Email = ?  WHERE Id_Autor = ?";
	private static final String COMANDO_SQL_DELETE = "DELETE FROM Autor WHERE Id_Autor = ?";
	private static final String COMANDO_SQL_VERIFICA_AUTOR = "SELECT Id_Autor, Nome FROM Autor WHERE Id_Autor = ? OR Nome = ?";
	
	/**
	 * Adiciona um autor no banco
	 * @param autor Autor a ser inserida no banco
	 */
	public void inseriAutor(Autor autor) {
		List<Autor> autores = verificaAutor(autor.getId(), autor.getNome());
		if (autores.size() == 0) {
			jdbcTemplate.update(COMANDO_SQL_INSERT,
					autor.getNome(),
					autor.getGenero(),
					autor.getEmail());
		apresentacaoAutor.inseriAutorApresentacao(autor.getApresentacao(), autor);
		}
	}
	
	/**
	 * Verifica se já existe um autor no banco
	 * @param idAutor para buscar no banco
	 * @param nomeAutor para buscar no banco
	 */
	private List<Autor> verificaAutor(int idAutor, String nomeAutor){
		List<Autor> autores = jdbcTemplate.query(COMANDO_SQL_VERIFICA_AUTOR, new RowMapper<Autor>(){
			@Override
			public Autor mapRow(ResultSet rs, int arg1) throws SQLException{
				Autor autor = new Autor();
				autor.setId(rs.getInt("Id_Autor"));
				autor.setNome(rs.getString("Nome"));
				return autor; 	
			}
		},idAutor, nomeAutor);
		
		return autores;

	}
	
	/**
	 * Altera um autor no banco
	 * @param id Id do autor a ser alterada
	 * @param autor Autor a ser substituida
	 */
	public void alteraAutor(Autor autor) {
		jdbcTemplate.update(COMANDO_SQL_UPDATE,
				autor.getNome(),
				autor.getGenero(),
				autor.getEmail(),
				autor.getId());
		apresentacaoAutor.alteraAutorApresentacao(autor.getApresentacao(), autor);
	}
	
	/**
	 * Apaga um autor do banco
	 * @param id Id do autor a ser excluido
	 */
	public void deletaAutor(Autor autor) {
		jdbcTemplate.update(COMANDO_SQL_DELETE,
				autor.getId());
		apresentacaoAutor.deletaAutorApresentacao(autor.getApresentacao(), autor);
	}
	
	/**
	 * Busca todos os autores
	 * @return autores
	 */
	public List<Autor> consultaAutor() {
		List<Autor> autores = jdbcTemplate.query(COMANDO_SQL_SELECT, (ResultSet results,
				int rowNum) -> {
					
			Autor autor = new Autor();
			
			autor.setId(results.getInt("Id_Autor"));
			autor.setNome(results.getString("Nome"));
			autor.setGenero(results.getString("Genero"));
			autor.setEmail(results.getString("Email"));
			return autor;
		});		
		
		return autores;

	}
}
