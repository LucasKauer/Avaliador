package avaliador.model;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SenhaHash {
	private static final String ALGORITMO_PBKDF2 = "PBKDF2WithHmacSHA1";

	// As seguintes constantes podem ser alteradas sem quebrar hash existentes.
	private static final int SALT_BYTE_SIZE = 24;
	private static final int HASH_BYTE_SIZE = 24;
	private static final int INTERACOES_PBKDF2 = 1000;

	private static final int INDICE_DE_INTERACOES = 0;
	private static final int INDICE_DE_SALT = 1;
	private static final int INDICE_DE_PBKDF2 = 2;

	/**
	 * Retorna um Hash PBKDF2 salgado da senha
	 *
	 * @param	senha	senha para o hash
	 * @return	Hash	PBKDF2 salgado da senha
	 */
	public static String criaHash(String senha)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return criaHash(senha.toCharArray());
	}

	/**
	 * Retorna um Hash PBKDF2 salgado da senha
	 *
	 * @param	senha	senha para o hash
	 * @return	Hash	PBKDF2 salgado da senha
	 */
	public static String criaHash(char[] senha)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Gera um salt aleatório
		SecureRandom aleatorio = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		aleatorio.nextBytes(salt);

		// Hash da senha
		byte[] hash = pbkdf2(senha, salt, INTERACOES_PBKDF2, HASH_BYTE_SIZE);
		// formato_das_interacoes:salt:hash
		return INTERACOES_PBKDF2 + ":" + deMatrizParaHex(salt) + ":" + deMatrizParaHex(hash);
	}

	/**
	 * Valida uma senha usando Hash
	 *
	 * @param senha			Senha a ser verificada
	 * @param hashCorreto	Hash da senha valida
	 * @return true se a senha está correta, false se não
	 */
	public static boolean validaSenha(String senha, String hashCorreto)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return validaSenha(senha.toCharArray(), hashCorreto);
	}

	/**
	 * Valida uma senha usando Hash.
	 *
	 * @param senha			Senha a ser verificada
	 * @param hashCorreto	Hash da senha valida
	 * @return true se a senha está correta, false se não
	 */
	public static boolean validaSenha(char[] senha, String hashCorreto)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Decodifica o hash atraves do seus parametros
		String[] parametros = hashCorreto.split(":");
		int interacoes = Integer.parseInt(parametros[INDICE_DE_INTERACOES]);
		byte[] salt = deHexParaMatriz(parametros[INDICE_DE_SALT]);
		byte[] hash = deHexParaMatriz(parametros[INDICE_DE_PBKDF2]);
		// Calcula o hash da senha fornecida, utilizando a mesma contagem da
		// interação,
		// e comprimento de hash
		byte[] hashTeste = pbkdf2(senha, salt, interacoes, hash.length);
		// Compara os hash num tempo constante. A senha está correta se ambos os
		// hashes combinarem.
		return comparaMatriz(hash, hashTeste);
	}
	
	/**
	 * Compara duas matriz de byte num determinado tamanho. Este metodo de
	 * comparacao eh usado para que o hash da senha não possa ser extraido a
	 * partir de uma conexao on-line do sistema usando um ataque de temporizacao
	 * e, em seguida, ataque off-line
	 * 
	 * @param a	primeira matriz de byte
	 * @param b segunda matriz de byte
	 * @return true se ambas as matrizes estiverem iguais, false se não
	 */
	private static boolean comparaMatriz(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	/**
	 * Computa a hash PBKDF2 da senha.
	 *
	 * @param senha 	 Senha para o hash.
	 * @param salt 		 Salt
	 * @param interacoes Contador de interacoes
	 * @param bytes 	 Tamanho do hash a ser computador em bytes
	 * @return Hash PBDKF2 da senha
	 */
	private static byte[] pbkdf2(char[] senha, byte[] salt, int interacoes,
			int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec esperado = new PBEKeySpec(senha, salt, interacoes, bytes * 8);
		SecretKeyFactory senhaSecretaFatorada = SecretKeyFactory
				.getInstance(ALGORITMO_PBKDF2);
		return senhaSecretaFatorada.generateSecret(esperado).getEncoded();
	}

	/**
	 * Converte uma string de caracteres hexadecimal em uma matriz de byte
	 *
	 * @param hex String hexadecimal
	 * @return the String hexadecimal decodificada em uma matriz de byte
	 */
	private static byte[] deHexParaMatriz(String hex) {
		byte[] binario = new byte[hex.length() / 2];
		for (int i = 0; i < binario.length; i++) {
			binario[i] = (byte) Integer.parseInt(
					hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binario;
	}

	/**
	 * Converte uma matriz de byte em uma string hexadecimal
	 * 
	 * @param array Matriz de byte a ser convertida
	 * @return a tamanho da string de caracteres vezes 2 (*2) codificada numa matriz de byte
	 */
	private static String deMatrizParaHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}

}