package com.example.demo.service;

import com.example.demo.model.Usuario;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsuarioService {

    // Almacenamiento simulado en memoria
    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    /**
     * Obtener todos los usuarios.
     */
    public Flux<Usuario> obtenerTodos() {
        return Flux.fromIterable(usuarios.values());
    }

    /**
     * Obtener un usuario por ID.
     */
    public Mono<Usuario> obtenerPorId(String id) {
        Usuario usuario = usuarios.get(id);
        return usuario != null ? Mono.just(usuario) : Mono.empty();
    }

    /**
     * Guardar un nuevo usuario.
     */
    public Mono<Usuario> guardar(Usuario usuario) {
        String id = UUID.randomUUID().toString();
        usuario.setId(id);
        usuarios.put(id, usuario);
        return Mono.just(usuario);
    }

    /**
     * Actualizar un usuario existente.
     */
    public Mono<Usuario> actualizar(String id, Usuario usuarioActualizado) {
        Usuario existente = usuarios.get(id);
        if (existente != null) {
            usuarioActualizado.setId(id);
            usuarios.put(id, usuarioActualizado);
            return Mono.just(usuarioActualizado);
        } else {
            return Mono.empty();
        }
    }

    /**
     * Eliminar un usuario por ID.
     */
    public Mono<Void> eliminar(String id) {
        usuarios.remove(id);
        return Mono.empty();
    }
}
