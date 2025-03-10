package com.example.demo.model;

public class Libro {

    private String id;
    private String titulo;
    private String autor;

    // Constructor vacío (requerido por Jackson para deserializar JSON)
    public Libro() {
    }

    // Constructor completo (opcional, útil en pruebas o para inicialización rápida)
    public Libro(String id, String titulo, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    // Getters y Setters necesarios para serialización y uso con @RequestBody

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
