package com.biblioteca.services;

import com.biblioteca.models.Emprestimo;
import com.biblioteca.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimoService {

    public void registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimos (livro_id, usuario_id, data_emprestimo) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getLivroId());
            stmt.setInt(2, emprestimo.getUsuarioId());
            stmt.setDate(3, new java.sql.Date(emprestimo.getDataEmprestimo().getTime()));
            stmt.executeUpdate();
        }
    }

    public void registrarDevolucao(int emprestimoId) throws SQLException {
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(2, emprestimoId);
            stmt.executeUpdate();
        }
    }

    public List<Emprestimo> listarEmprestimosPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM emprestimos WHERE usuario_id = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return emprestimos;
    }

    // Outros métodos conforme necessário
}
