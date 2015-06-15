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

import avaliador.model.SenhaHash;
import avaliador.model.Usuario;

@Component
public class UsuarioDao {
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	// Adiciona um usuario no banco
	public void inserirUsuario(Usuario usuario) throws DataAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		String senhaCriptografada = SenhaHash.criaHash(usuario.getSenha());
		jdbcTemplate.update(
				"INSERT INTO USUARIO (LOGIN, SENHA) VALUES (?, ?)",
				usuario.getLogin(), senhaCriptografada);
	}

}
