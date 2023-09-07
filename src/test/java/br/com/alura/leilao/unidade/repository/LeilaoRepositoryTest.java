package br.com.alura.leilao.unidade.repository;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.repositories.LeilaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.RepeatedTest.SHORT_DISPLAY_NAME;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class LeilaoRepositoryTest {

    @Autowired
    private LeilaoRepository leilaoRepository;

    @RepeatedTest(value = 100, name = SHORT_DISPLAY_NAME)
    @DisplayName("Teste de estresse para retornar todos os leilões")
    public void deveriaRetornarTodosLeiloes() {
        List<Leilao> leiloes = leilaoRepository.findAll();
        assertFalse(leiloes.isEmpty());
    }

    @Test
    public void deveriarRetornarTodosLeiloesEmMenosDe15Milissegundos() {
        assertTimeout(Duration.ofMillis(15), () -> {
            List<Leilao> leiloes = leilaoRepository.findAll();
            assertFalse(leiloes.isEmpty());
        });
    }

}