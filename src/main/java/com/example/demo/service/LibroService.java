package com.example.demo.service;

import com.example.demo.model.Libro;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LibroService {

    // Simulación de almacenamiento en memoria
    private final Map<String, Libro> libros = new ConcurrentHashMap<>();

    /**
     * Obtener todos los libros.
     */
    public Flux<Libro> obtenerTodos() {
        return Flux.fromIterable(libros.values());
    }

    /**
     * Obtener un libro por su ID.
     */
    public Mono<Libro> obtenerPorId(String id) {
        Libro libro = libros.get(id);
        return libro != null ? Mono.just(libro) : Mono.empty();
    }

    /**
     * Guardar un nuevo libro.
     */
    public Mono<Libro> guardar(Libro libro) {
        String id = UUID.randomUUID().toString();
        libro.setId(id);
        libros.put(id, libro);
        return Mono.just(libro);
    }

    /**
     * Actualizar un libro existente.
     */
    public Mono<Libro> actualizar(String id, Libro libroActualizado) {
        Libro existente = libros.get(id);
        if (existente != null) {
            libroActualizado.setId(id);
            libros.put(id, libroActualizado);
            return Mono.just(libroActualizado);
        } else {
            return Mono.empty();
        }
    }

    /**
     * Eliminar un libro por ID.
     */
    public Mono<Void> eliminar(String id) {
        libros.remove(id);
        return Mono.empty();
    }
}
