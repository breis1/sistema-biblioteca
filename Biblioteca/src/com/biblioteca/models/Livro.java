package com.biblioteca.models;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private int quantidade; // incluímos a quantidade de livros disponíveis

    public Livro() {}

    public Livro(String titulo, String autor, String isbn, int quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.quantidade = quantidade;
    }

    // getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
