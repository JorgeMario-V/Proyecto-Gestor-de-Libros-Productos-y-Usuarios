package com.example.demo.controller;

import com.example.demo.model.Libro;
import com.example.demo.service.LibroService;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;
    private final MessageSource messageSource;

    // Inyección por constructor (buena práctica)
    public LibroController(LibroService libroService, MessageSource messageSource) {
        this.libroService = libroService;
        this.messageSource = messageSource;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Libro> obtenerPorId(@PathVariable String id) {
        return libroService.obtenerPorId(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Libro> guardar(@RequestBody Libro libro) {
        libro.setId(UUID.randomUUID().toString());
        return libroService.guardar(libro);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Libro> actualizar(@PathVariable String id, @RequestBody Libro libro) {
        return libroService.actualizar(id, libro);
    }

    @DeleteMapping("/{id}")
    public Mono<String> eliminar(@PathVariable String id,
                                 @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        Locale actualLocale = (locale != null) ? locale : Locale.getDefault();
        return libroService.eliminar(id)
                .then(Mono.fromSupplier(() ->
                        messageSource.getMessage("libro.eliminado", null, "Libro eliminado", actualLocale)));
    }
}
