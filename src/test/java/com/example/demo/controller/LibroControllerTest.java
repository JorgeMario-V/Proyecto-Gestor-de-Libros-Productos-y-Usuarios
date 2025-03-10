package com.example.demo.controller;

import com.example.demo.model.Libro;
import com.example.demo.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class LibroControllerTest {

    private LibroService libroService;
    private MessageSource messageSource;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        libroService = mock(LibroService.class);
        messageSource = mock(MessageSource.class);

        LibroController libroController = new LibroController(libroService, messageSource);

        webTestClient = WebTestClient.bindToController(libroController).build();
    }

    @Test
    void testObtenerTodosLibros() {
        Libro libro1 = new Libro("1", "Libro A", "Autor A");
        Libro libro2 = new Libro("2", "Libro B", "Autor B");

        when(libroService.obtenerTodos()).thenReturn(Flux.just(libro1, libro2));

        webTestClient.get()
                .uri("/api/libros")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Libro.class)
                .hasSize(2)
                .contains(libro1, libro2);
    }

    @Test
    void testObtenerLibroPorId() {
        Libro libro = new Libro("123", "Libro Prueba", "Autor Prueba");

        when(libroService.obtenerPorId("123")).thenReturn(Mono.just(libro));

        webTestClient.get()
                .uri("/api/libros/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Libro.class)
                .isEqualTo(libro);
    }

    @Test
    void testGuardarLibro() {
        Libro libro = new Libro(null, "Nuevo Libro", "Nuevo Autor");
        Libro libroGuardado = new Libro(UUID.randomUUID().toString(), libro.getTitulo(), libro.getAutor());

        when(libroService.guardar(any())).thenReturn(Mono.just(libroGuardado));

        webTestClient.post()
                .uri("/api/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(libro)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Libro.class)
                .value(l -> {
                    assert l.getId() != null;
                    assert l.getTitulo().equals(libro.getTitulo());
                    assert l.getAutor().equals(libro.getAutor());
                });
    }

    @Test
    void testActualizarLibro() {
        Libro libroActualizado = new Libro("999", "Actualizado", "Autor Actualizado");

        when(libroService.actualizar(eq("999"), any(Libro.class))).thenReturn(Mono.just(libroActualizado));

        webTestClient.put()
                .uri("/api/libros/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(libroActualizado)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Libro.class)
                .isEqualTo(libroActualizado);
    }

    @Test
    void testEliminarLibro() {
        when(libroService.eliminar("abc123")).thenReturn(Mono.empty());
        when(messageSource.getMessage(eq("libro.eliminado"), any(), anyString(), any(Locale.class)))
                .thenReturn("Libro eliminado correctamente");

        webTestClient.delete()
                .uri("/api/libros/abc123")
                .header("Accept-Language", "es")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Libro eliminado correctamente");
    }
}
