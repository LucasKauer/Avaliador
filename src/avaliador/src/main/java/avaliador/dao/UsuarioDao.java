package avaliador.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import avaliador.model.NivelUsuario;
import avaliador.model.Usuario;

@Component
public class UsuarioDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	private static final String COMANDO_SQL_INSERT_USUARIO = "INSERT INTO Usuario (Login, Senha, Nivel_Usuario) VALUES (?, ?, ?)";
	private static final String COMANDO_SQL_SELECT_USUARIO = "SELECT Id_Usuario,  FROM Usuario WHERE Login = (?) and Senha = (?)";
	
	// Adiciona um usuario no banco
	public void inserirUsuario(Usuario usuario) throws DataAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		jdbcTemplate.update(
				COMANDO_SQL_INSERT_USUARIO,
				usuario.getLogin(),
				usuario.getSenha(),
				usuario.getTipoUsuario());
	}
	
	// Valida o usuario quando ele faz o login
	public Usuario validarUsuario(String login, String senha) {
		List<Usuario> usuariosEncontrados = jdbcTemplate.query(COMANDO_SQL_SELECT_USUARIO, new RowMapper<Usuario>(){
			
			@Override
			public Usuario mapRow(ResultSet rs, int arg1) throws SQLException {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("Id_Usuario"));
				usuario.setLogin(rs.getString("Login"));
				usuario.setSenha(rs.getString("Senha"));
				usuario.setTipoUsuario(NivelUsuario.valueOf(rs.getString("Nivel_Usuario")));
				return usuario;
			}		
		}, login, senha);
		
		return usuariosEncontrados.isEmpty() ? null : usuariosEncontrados.get(0); 
	}	
}