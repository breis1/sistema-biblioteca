package com.biblioteca.services;

import com.biblioteca.models.Usuario;
import com.biblioteca.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    public void adicionarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void removerUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Outros métodos conforme necessário
}
