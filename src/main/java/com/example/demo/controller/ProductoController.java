package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final MessageSource messageSource;

    @Autowired
    public ProductoController(ProductoService productoService, MessageSource messageSource) {
        this.productoService = productoService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public Flux<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Mono<Object> obtenerPorId(@PathVariable String id, Locale locale) {
        return productoService.obtenerPorId(id)
                .map(producto -> (Object) producto)
                .switchIfEmpty(Mono.just(messageSource.getMessage("producto.no.encontrado", null, locale)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")
    public Mono<Object> actualizar(@PathVariable String id, @RequestBody Producto producto, Locale locale) {
        return productoService.actualizar(id, producto)
                .map(prod -> (Object) prod)
                .switchIfEmpty(Mono.just(messageSource.getMessage("producto.no.encontrado", null, locale)));
    }

    @DeleteMapping("/{id}")
    public Mono<String> eliminar(@PathVariable String id, Locale locale) {
        return productoService.eliminar(id)
                .thenReturn(messageSource.getMessage("producto.eliminado", null, locale));
    }
}
