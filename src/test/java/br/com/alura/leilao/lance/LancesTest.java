package br.com.alura.leilao.lance;

import br.com.alura.leilao.leiloes.LeiloesPage;
import br.com.alura.leilao.login.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LancesTest {

	private LeiloesPage paginaDeLeiloes;
	private LancesPage paginaDeLances;

	@BeforeEach
	public void beforeEach() {
		LoginPage paginaDeLogin = new LoginPage();
		this.paginaDeLeiloes = paginaDeLogin.efetuarLogin("fulano", "pass");
		this.paginaDeLances = paginaDeLeiloes.darLance();
	}

	@AfterEach
	public void afterEach() {
		this.paginaDeLeiloes.fechar();
		this.paginaDeLances.fechar();
	}

}
