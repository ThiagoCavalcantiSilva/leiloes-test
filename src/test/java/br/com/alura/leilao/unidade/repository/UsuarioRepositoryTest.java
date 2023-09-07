package br.com.alura.leilao.unidade.repository;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.repositories.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeAll
    public void setupOnce() {
        usuario = new Usuario("nomeUsuario", "emailUsuario@email.com", "1234");
        usuarioRepository.save(usuario);
    }

    @AfterAll
    public void cleanup() {
        usuarioRepository.delete(usuario);
    }

    @Test
    public void deveriaRetornarUsuarioPeloNome() {
        Usuario encontrado = usuarioRepository.getUserByUsername(usuario.getNome());
        assertEquals(usuario.getNome(), encontrado.getNome());
    }

    @Test
    @Disabled
    public void deveriaRetornarTodosUsuariosEmMenosDe150Milissegundos() {
        assertTimeoutPreemptively(Duration.ofMillis(150), () -> {
            List<Usuario> usuarios = usuarioRepository.findAll();
            assertFalse(usuarios.isEmpty());
            assertTrue(usuarios.contains(usuario));
        });
    }

}