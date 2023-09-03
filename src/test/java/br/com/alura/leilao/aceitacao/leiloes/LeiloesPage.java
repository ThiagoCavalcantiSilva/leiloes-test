package br.com.alura.leilao.aceitacao.leiloes;

import br.com.alura.leilao.aceitacao.lance.LancesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.alura.leilao.aceitacao.PageObject;

public class LeiloesPage extends PageObject {

	private static final String URL_LIST = "http://localhost:8080/leiloes";
	private static final String URL_FORM = "http://localhost:8080/leiloes/new";

	public LeiloesPage(WebDriver browser) {
		super(browser);
	}

	public CadastroLeilaoPage carregarFormulario() {
		this.browser.navigate().to(URL_FORM);
		return new CadastroLeilaoPage(browser);
	}

	public boolean isLeilaoCadastrado(String nome, String valorInicial, String dataAbertura) {
		WebElement linhaDaTabela = this.browser.findElement(By.cssSelector("#tabela-leiloes tbody tr:last-child"));
		WebElement colunaNome = linhaDaTabela.findElement(By.cssSelector("td:nth-child(1)"));
		WebElement colunaDataAbertura = linhaDaTabela.findElement(By.cssSelector("td:nth-child(2)"));
		WebElement colunaValorInicial = linhaDaTabela.findElement(By.cssSelector("td:nth-child(3)"));
		
		return colunaNome.getText().equals(nome) && colunaDataAbertura.getText().equals(dataAbertura) && colunaValorInicial.getText().equals(valorInicial);
	}

	public boolean isPaginaAtual() {
		return this.browser.getCurrentUrl().equals(URL_LIST);
	}

	public LancesPage darLance(){
		// Constrói o seletor para o botão "dar lance" com base no ID do leilão 2
		String botaoDarLanceSelector = String.format("a[href*='/leiloes/%s']", 2);

		// Clica no botão, redirecionando para a página de dar lances no leilão
		browser.findElement(By.cssSelector(botaoDarLanceSelector)).click();
		return new LancesPage(browser);
	}

}
