package br.com.alura.leilao.lance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LancesPage {

	private static final String URL_LANCES = "http://localhost:8080/leiloes/2";

	private WebDriver browser;

	public LancesPage() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		this.browser = new ChromeDriver();
		this.browser.navigate().to(URL_LANCES);
	}

	public LancesPage(WebDriver browser) {
		this.browser = browser;
	}

	public boolean isPaginaAtual() {
		return browser.getCurrentUrl().contains(URL_LANCES);
	}

	public boolean isTituloLeilaoVisivel() {
		return browser.getPageSource().contains("Dados do Leilão");
	}

	public void fechar() {
		this.browser.quit();
	}

	public void preencherValor(String valor){
		this.browser.findElement(By.id("valor")).sendKeys(valor);
	}

	public void submeterLance(){
		this.browser.findElement(By.id("btnDarLance")).click();
	}

	public boolean isErroLanceMenorQue10Centavos() {
		String pageSource = this.browser.getPageSource();
		return pageSource.contains("deve ser maior que ou igual a 0.1");
	}

}
