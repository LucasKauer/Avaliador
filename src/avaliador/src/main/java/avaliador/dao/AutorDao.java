package avaliador.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import avaliador.model.Apresentacao;
import avaliador.model.Autor;
import avaliador.model.Categoria;
import avaliador.model.Situacao;

@Component
public class AutorDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	private static final String COMANDO_SQL_SELECT = "SELECT Id_Autor, Nome, Genero, Email FROM Autor";
	private static final String COMANDO_SQL_INSERT = "INSERT INTO Autor(Nome, Genero, Email) values (?, ?, ?)";
	private static final String COMANDO_SQL_UPDATE = "UPDATE Autor SET Nome = ?, Genero = ?, Email = ?  WHERE Id_Autor = ?";
	private static final String COMANDO_SQL_DELETE = "DELETE FROM Autor WHERE Id_Autor = ?";
	
	/**
	 * Adiciona um autor no banco
	 * @param autor Autor a ser inserida no banco
	 */
	public void inserirAutor(Autor autor) {
		jdbcTemplate.update(COMANDO_SQL_INSERT,
				autor.getNome(),
				autor.getGenero(),
				autor.getEmail());
	}
	
	/**
	 * Altera um autor no banco
	 * @param id Id do autor a ser alterada
	 * @param autor Autor a ser substituida
	 */
	public void alteraAutor(int id, Autor autor) {
		jdbcTemplate.update(COMANDO_SQL_UPDATE,
				autor.getNome(),
				autor.getGenero(),
				autor.getEmail(),
				id);
	}
	
	/**
	 * Apaga um autor do banco
	 * @param id Id do autor a ser excluido
	 */
	public void deletaAutor(int id) {
		jdbcTemplate.update(COMANDO_SQL_DELETE,
				id);
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
