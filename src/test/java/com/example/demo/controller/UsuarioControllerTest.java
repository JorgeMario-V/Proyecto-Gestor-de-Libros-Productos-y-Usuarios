package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private MessageSource messageSource;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        messageSource = mock(MessageSource.class);
        UsuarioController usuarioController = new UsuarioController(usuarioService, messageSource);
        webTestClient = WebTestClient.bindToController(usuarioController).build();
    }

    @Test
    void crearYLeerUsuario_deberiaRetornarUsuarioGuardado() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Juan");
        usuario.setCorreo("juan@example.com");

        when(usuarioService.guardar(any(Usuario.class))).thenReturn(Mono.just(usuario));

        webTestClient.post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(usuario)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Usuario.class)
                .consumeWith(response -> {
                    Usuario resultado = response.getResponseBody();
                    assert resultado != null;
                    assert "1".equals(resultado.getId());
                    assert "Juan".equals(resultado.getNombre());
                    assert "juan@example.com".equals(resultado.getCorreo());
                });

        verify(usuarioService, times(1)).guardar(any(Usuario.class));
    }

    @Test
    void obtenerTodosUsuarios_deberiaRetornarListaUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setId("1");
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId("2");
        usuario2.setNombre("Ana");

        when(usuarioService.obtenerTodos()).thenReturn(Flux.just(usuario1, usuario2));

        webTestClient.get()
                .uri("/api/usuarios")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Usuario.class)
                .hasSize(2)
                .contains(usuario1, usuario2);

        verify(usuarioService, times(1)).obtenerTodos();
    }

    @Test
    void eliminarUsuario_deberiaMostrarMensajeConfirmacion() {
        when(usuarioService.eliminar("1")).thenReturn(Mono.empty());
        when(messageSource.getMessage(eq("usuario.eliminado"), any(), anyString(), any(Locale.class)))
                .thenReturn("Usuario eliminado correctamente");

        webTestClient.delete()
                .uri("/api/usuarios/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Usuario eliminado correctamente");

        verify(usuarioService, times(1)).eliminar("1");
    }
}
