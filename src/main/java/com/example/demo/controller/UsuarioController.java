package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final MessageSource messageSource;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, MessageSource messageSource) {
        this.usuarioService = usuarioService;
        this.messageSource = messageSource;
    }

    // Obtener todos los usuarios
    @GetMapping
    public Flux<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Usuario>> obtenerPorId(@PathVariable String id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public Mono<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Usuario>> actualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> eliminarUsuario(@PathVariable String id, Locale locale) {
        return usuarioService.eliminar(id)
                .thenReturn(ResponseEntity.ok(
                        messageSource.getMessage("usuario.eliminado", null, "Usuario eliminado", locale != null ? locale : Locale.getDefault())
                ));
    }
}
