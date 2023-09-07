package br.com.alura.leilao.unidade.service;

import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.repositories.LanceRepository;
import br.com.alura.leilao.repositories.LeilaoRepository;
import br.com.alura.leilao.repositories.UsuarioRepository;
import br.com.alura.leilao.service.LanceService;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LanceServiceTest {

    @InjectMocks
    private LanceService lanceService;

    @Mock
    private LanceRepository lanceRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LeilaoRepository leilaoRepository;

    @Mock
    private Leilao mockLeilao;

    private Usuario usuario;

    private NovoLanceDto lanceDto;

    @BeforeAll
    public static void setupOnce() {
        FixtureFactoryLoader.loadTemplates("br.com.alura.leilao.unidade.template");
    }

    @BeforeEach
    public void setup() {
        when(leilaoRepository.getOne(anyLong())).thenReturn(mockLeilao);

        lanceDto = new NovoLanceDto();
        lanceDto.setValor(BigDecimal.valueOf(100.0));
        lanceDto.setLeilaoId(1L);
    }

    @Nested
    @DisplayName("Testes de propostas de leilão")
    class PropoeLanceTests {

        @BeforeEach
        public void setup() {
            usuario = Fixture.from(Usuario.class).gimme("usuario");
        }

        @Test
        @DisplayName("Teste com lance válido")
        public void deveriarProporLance() {
            /*
            Poderia verificar mais detalhes, como se o objeto lance
            realmente foi criado para o usuário informado, com data e valor correto.
            Quanto mais assertivas, melhor!
            Porém, queremos apenas utilizar as tags para exemplificar,
            logo, analisar o comportamento é suficiente para isso.
            Além disso, pode ser verificado em outro cenário de teste específico.
             */
            when(usuarioRepository.getUserByUsername(usuario.getNome())).thenReturn(usuario);
            when(mockLeilao.propoe(any())).thenReturn(true);

            assertTrue(lanceService.propoeLance(lanceDto, usuario.getNome()));
            verify(lanceRepository, times(1)).save(any());
        }

        @Test
        @DisplayName("Teste com lance inválido")
        public void naoDeveriaProporLance() {
            when(usuarioRepository.getUserByUsername(usuario.getNome())).thenReturn(usuario);
            when(mockLeilao.propoe(any())).thenReturn(false);

            assertFalse(lanceService.propoeLance(lanceDto, usuario.getNome()));
            verify(lanceRepository, never()).save(any());
        }

        @ParameterizedTest
        @CsvSource({"150, nomeUsuario1", "200, nomeUsuario2"})
        @DisplayName("Teste com vários lances válidos")
        public void deveriarProporVariosLances(String valor, String nomeUsuario) {
            setarUsuario(nomeUsuario);

            when(usuarioRepository.getUserByUsername(usuario.getNome())).thenReturn(usuario);
            when(mockLeilao.propoe(any())).thenReturn(true);

            lanceDto.setValor(BigDecimal.valueOf(Long.parseLong(valor)));
            assertTrue(lanceService.propoeLance(lanceDto, usuario.getNome()));
            verify(lanceRepository, times(1)).save(any());
        }

        private void setarUsuario(String nomeUsuario) {
            usuario.setId(usuario.getId() + 1);
            usuario.setNome(nomeUsuario);
            usuario.setEmail(nomeUsuario + "@email.com");
        }
    }

    @Test
    @DisplayName("Chamadas para o repository")
    public void deveriaChamarMetodoGetOne() {
        Leilao leilao = lanceService.getLeilao(lanceDto.getLeilaoId());

        verify(leilaoRepository, times(1)).getOne(lanceDto.getLeilaoId());

        assertNotNull(leilao);
    }

}
