package avaliador.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SenhaHashTeste {
	@Test
	public void validaSenha() throws Exception {
		try {
			boolean naoFalhou = true;

			for (int i = 0; i < 100; i++) {
				String senha = "senha" + i;
				String hash = SenhaHash.criaHash(senha);
				String segundoHash = SenhaHash.criaHash(senha);
				if (hash.equals(segundoHash)) {
					// System.out.println("FALHA: OS DOIS HASH SÃO IGUAIS!");
					naoFalhou = false;
				}
				String senhasDiferentes = "senha" + (i + 1);
				if (SenhaHash.validaSenha(senhasDiferentes, hash)) {
					// System.out.println("FALHA: DUAS SENHAS DIFERENTES FORAM ACEITAS");
					naoFalhou = false;
				}
				if (!SenhaHash.validaSenha(senha, hash)) {
					// System.out.println("FALHA: SENHA CORRETA NÃO FOI ACEITA!");
					naoFalhou = false;
				}
			}

			assertTrue(naoFalhou);
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
	}
}
