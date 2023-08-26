package br.com.alura.leilao.lance;

import br.com.alura.leilao.leiloes.LeiloesPage;
import br.com.alura.leilao.login.LoginPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Test
	public void naoDeveriaDarLanceMenorQue10Centavos() {
		paginaDeLances.preencherValor("0");
		paginaDeLances.submeterLance();

		assertTrue(paginaDeLances.isErroLanceMenorQue10Centavos());
		assertTrue(paginaDeLances.isPaginaAtual()); // Falha: fez requisição para /lances e não houve redirect para retornar pro leilao
	}

}
