package br.com.alura.leilao.unidade.model;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Usuario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(MockitoExtension.class)
public class LanceTest {
    private Usuario usuario;

    @BeforeAll
    public static void setupOnce() {
        FixtureFactoryLoader.loadTemplates("br.com.alura.leilao.unidade.template");
    }

    @BeforeEach
    public void setup() {
        usuario = Fixture.from(Usuario.class).gimme("usuario");
    }

    private static final List<BigDecimal> valoresValidos = Arrays.asList(
            new BigDecimal("0.10"),
            new BigDecimal("0.11"),
            new BigDecimal("100.00"),
            new BigDecimal("300.00")
    );

    private static final List<BigDecimal> valoresInvalidos = Arrays.asList(
            BigDecimal.ZERO,
            new BigDecimal("-0.01"),
            new BigDecimal("-100.00")
    );

    @TestFactory
    Stream<DynamicTest> deveriaCriarVariosLancesValidos() {
        return valoresValidos.stream().map(valor -> dynamicTest("Criar lance com valor: " + valor, () -> {
            Lance lance = new Lance(usuario, valor);
            assertAll(
                    () -> assertEquals(usuario, lance.getUsuario()),
                    () -> assertEquals(valor, lance.getValor()),
                    () -> assertNotNull(lance.getData())
            );
        }));
    }

    @TestFactory
    Stream<DynamicTest> deveriaRetornarExceptionAoReceberValorInvalido() {
        return valoresInvalidos.stream().map(valor -> dynamicTest("Retornar exception do valor: " + valor, () -> {
            assertThrows(IllegalArgumentException.class, () -> new Lance(usuario, valor));
        }));
    }


}
