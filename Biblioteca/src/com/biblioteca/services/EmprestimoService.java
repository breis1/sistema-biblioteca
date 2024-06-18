package com.biblioteca.services;

import com.biblioteca.models.Emprestimo;
import com.biblioteca.models.Livro;
import com.biblioteca.models.Usuario;
import com.biblioteca.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimoService {

    private final UsuarioService usuarioService = new UsuarioService();

    public void registrarEmprestimo(int livroId, String nomeUsuario, Date dataDevolucao) throws SQLException {
        Usuario usuario = usuarioService.buscarOuCadastrarUsuario(nomeUsuario, "default@email.com");

        String sql = "INSERT INTO emprestimos (livro_id, usuario_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            stmt.setInt(2, usuario.getId());
            stmt.setTimestamp(3, new java.sql.Timestamp(new Date().getTime())); // Data atual
            stmt.setTimestamp(4, new java.sql.Timestamp(dataDevolucao.getTime()));
            stmt.executeUpdate();
        }
    }

    public List<Emprestimo> listarEmprestimos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("id"),
                        rs.getInt("livro_id"),
                        rs.getInt("usuario_id"),
                        rs.getDate("data_emprestimo"),
                        rs.getDate("data_devolucao")
                );
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }

    public void devolverEmprestimo(int emprestimoId) throws SQLException {
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime())); // Data atual
            stmt.setInt(2, emprestimoId);
            stmt.executeUpdate();
        }
    }
}
