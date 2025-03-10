package com.example.demo.service;

import com.example.demo.model.Producto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ProductoService {

    private final Map<String, Producto> productos = new HashMap<>();

    public ProductoService() {
        // Productos de ejemplo
        productos.put("1", new Producto("1", "Teclado", 50.0));
        productos.put("2", new Producto("2", "Mouse", 30.0));
    }

    public Flux<Producto> obtenerTodos() {
        return Flux.fromIterable(productos.values());
    }

    public Mono<Producto> obtenerPorId(String id) {
        Producto producto = productos.get(id);
        return producto != null ? Mono.just(producto) : Mono.empty();
    }

    public Mono<Producto> guardar(Producto producto) {
        String id = UUID.randomUUID().toString();
        producto.setId(id);
        productos.put(id, producto);
        return Mono.just(producto);
    }

    public Mono<Producto> actualizar(String id, Producto productoActualizado) {
        Producto existente = productos.get(id);
        if (existente != null) {
            productoActualizado.setId(id);
            productos.put(id, productoActualizado);
            return Mono.just(productoActualizado);
        } else {
            return Mono.empty();
        }
    }

    public Mono<Void> eliminar(String id) {
        productos.remove(id);
        return Mono.empty();
    }
}
