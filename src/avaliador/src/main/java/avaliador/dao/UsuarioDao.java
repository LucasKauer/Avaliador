package avaliador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import avaliador.model.UsuarioAvaliador;

@Component
public class UsuarioDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	private static final String COMANDO_SQL_INSERT_USUARIO = "INSERT INTO Avaliador (Nome, Idade, Genero, Instituicao, Login, Senha) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String COMANDO_SQL_SELECT_USUARIO = "SELECT Id_Avaliador, Nome, Login, Senha FROM Avaliador WHERE Login = ? AND Senha = ?";
	
	// Adiciona um usuario no banco
	public void inserirUsuario(UsuarioAvaliador usuario) {
		jdbcTemplate.update(
				COMANDO_SQL_INSERT_USUARIO,
				usuario.getNome(),
				usuario.getIdade(),
				usuario.getGenero(),
				usuario.getInstitucao(),
				usuario.getLogin(),
				usuario.getSenha());
	}
	
	// Valida o usuario quando ele faz o login
	public UsuarioAvaliador validarUsuario(String login, String senha) {
		List<UsuarioAvaliador> usuariosEncontrados = jdbcTemplate.query(COMANDO_SQL_SELECT_USUARIO, new RowMapper<UsuarioAvaliador>(){
			
			@Override
			public UsuarioAvaliador mapRow(ResultSet rs, int arg1) throws SQLException {
				UsuarioAvaliador usuario = new UsuarioAvaliador();
				usuario.setId(rs.getInt("Id_Avaliador"));
				usuario.setNome(rs.getString("Nome"));
				usuario.setLogin(rs.getString("Login"));
				usuario.setSenha(rs.getString("Senha"));
				return usuario;
			}		
		}, login, senha);
		
		return usuariosEncontrados.isEmpty() ? null : usuariosEncontrados.get(0); 
	}	
}