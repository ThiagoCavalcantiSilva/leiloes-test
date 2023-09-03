package br.com.alura.leilao.aceitacao.lance;

import br.com.alura.leilao.aceitacao.leiloes.LeiloesPage;
import br.com.alura.leilao.aceitacao.login.LoginPage;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
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
	@Order(1)
	public void naoDeveriaDarLanceMenorQue10Centavos() {
		paginaDeLances.preencherValor("0");
		paginaDeLances.submeterLance();

		assertTrue(paginaDeLances.isErroLanceMenorQue10Centavos());
		assertTrue(paginaDeLances.isPaginaAtual()); // Falha: fez requisição para /lances e não houve redirect para retornar pro leilao
	}

	@Test
	@Order(2) // Adicionei a ordem, pois o JUnit executava esse por último, logo, dava lance inválido pois era consecutivo (visto que o 3 insere lances)
	public void naoDeveriaDarLanceMenorQueOValorMinimo() {
		BigDecimal lanceMenos5Centavos = paginaDeLances.obterValorInicial().subtract(new BigDecimal("0.05"));

		paginaDeLances.preencherValor(lanceMenos5Centavos.toString());
		paginaDeLances.submeterLance();

		assertTrue(paginaDeLances.isLanceInvalido()); // Falha: está aceitando valores menores que o mínimo
		assertTrue(paginaDeLances.isPaginaAtual());
	}

	/*
	O ideal seria realizar mais dois testes, um com valor igual ao mínimo e outro com valor superior.
	Entretanto, como o usuário não pode dar dois lances repetidos, dificulta o teste pois tem que limpar a base,
	logar com outro usuário ou mockar o banco (separar as sessões).
	*/

	@Test
	@Order(3)
	public void naoDeveriaDarLancesConsecutivosNoMesmoLeilao() {
		BigDecimal valorInicial = paginaDeLances.obterValorInicial();

		paginaDeLances.preencherValor(valorInicial.toString());
		paginaDeLances.submeterLance();

		paginaDeLances.preencherValor(valorInicial.add(TEN).toString());
		paginaDeLances.submeterLance();

		assertTrue(paginaDeLances.isLanceInvalido());
		assertTrue(paginaDeLances.isPaginaAtual());
	}

}
