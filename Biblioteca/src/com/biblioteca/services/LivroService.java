package com.biblioteca.services;

import com.biblioteca.models.Livro;
import com.biblioteca.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroService {

    public void adicionarLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, isbn, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getIsbn());
            stmt.setInt(4, livro.getQuantidade());
            stmt.executeUpdate();
        }
    }

    public List<Livro> listarLivros() throws SQLException {
    List<Livro> livros = new ArrayList<>();
    String sql = "SELECT id, titulo, autor, isbn, quantidade FROM livros";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Livro livro = new Livro();
            livro.setId(rs.getInt("id"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setIsbn(rs.getString("isbn"));
            livro.setQuantidade(rs.getInt("quantidade"));
            livros.add(livro);
        }
    }
    return livros;
}

    public Livro buscarLivroPorISBN(String isbn) throws SQLException {
        Livro livro = null;
        String sql = "SELECT * FROM livros WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setQuantidade(rs.getInt("quantidade"));
                }
            }
        }
        return livro;
    }

    public void removerLivroPorTitulo(String titulo, int quantidade) throws SQLException {
    String sql = "SELECT quantidade FROM livros WHERE titulo = ?";
    int quantidadeAtual = 0;
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, titulo);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                quantidadeAtual = rs.getInt("quantidade");
            } else {
                throw new SQLException("Livro não encontrado com o título: " + titulo);
            }
        }
    }

    if (quantidade > quantidadeAtual) {
        throw new SQLException("Não há " + quantidade + " exemplares disponíveis do livro: " + titulo);
    }

    sql = "UPDATE livros SET quantidade = quantidade - ? WHERE titulo = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, quantidade);
        stmt.setString(2, titulo);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Falha ao remover exemplares do livro: " + titulo);
        }
    }
}


    public void emprestarLivro(int livroId) throws SQLException {
        Livro livro = buscarLivroPorId(livroId);
        if (livro == null) {
            throw new SQLException("Livro não encontrado com o ID: " + livroId);
        }

        if (livro.getQuantidade() > 0) {
            String sql = "UPDATE livros SET quantidade = quantidade - 1 WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, livroId);
                stmt.executeUpdate();
            }
        } else {
            throw new SQLException("Não há exemplares disponíveis do livro: " + livro.getTitulo());
        }
    }

    public void devolverLivro(int livroId) throws SQLException {
        Livro livro = buscarLivroPorId(livroId);
        if (livro == null) {
            throw new SQLException("Livro não encontrado com o ID: " + livroId);
        }

        String sql = "UPDATE livros SET quantidade = quantidade + 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            stmt.executeUpdate();
        }
    }

    private Livro buscarLivroPorId(int livroId) throws SQLException {
        Livro livro = null;
        String sql = "SELECT * FROM livros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setQuantidade(rs.getInt("quantidade"));
                }
            }
        }
        return livro;
    }
}
