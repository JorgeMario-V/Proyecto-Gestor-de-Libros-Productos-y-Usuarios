package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

@WebFluxTest(controllers = ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @TestConfiguration
    static class Config {

        @Bean
        public ProductoService productoService() {
            ProductoService mockService = mock(ProductoService.class);

            when(mockService.obtenerTodos()).thenReturn(
                    Flux.just(new Producto("1", "Teclado", 25.0))
            );

            when(mockService.obtenerPorId("1")).thenReturn(
                    Mono.just(new Producto("1", "Teclado", 25.0))
            );

            when(mockService.guardar(any(Producto.class))).thenAnswer(invocation -> {
                Producto producto = invocation.getArgument(0);
                return Mono.just(producto);
            });

            when(mockService.actualizar(eq("1"), any(Producto.class))).thenAnswer(invocation -> {
                Producto producto = invocation.getArgument(1);
                return Mono.just(producto);
            });

            when(mockService.eliminar("1")).thenReturn(Mono.empty());

            return mockService;
        }

        @Bean
        public ResourceBundleMessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("messages");
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }
    }

    @Test
    void testObtenerTodosLosProductos() {
        webTestClient.get()
                .uri("/api/productos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Producto.class);
    }

    @Test
    void testObtenerProductoPorId() {
        webTestClient.get()
                .uri("/api/productos/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class);
    }

    @Test
    void testCrearProducto() {
        Producto nuevoProducto = new Producto("2", "Mouse", 18.0);

        webTestClient.post()
                .uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(nuevoProducto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class);
    }

    @Test
    void testActualizarProducto() {
        Producto productoActualizado = new Producto("1", "Teclado Mecánico", 30.0);

        webTestClient.put()
                .uri("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productoActualizado)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class);
    }

    @Test
    void testEliminarProducto() {
        webTestClient.delete()
                .uri("/api/productos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }
}

