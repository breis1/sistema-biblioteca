package com.biblioteca.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/biblioteca";
        String user = "postgres"; // Substitua pelo seu usuário do PostgreSQL
        String password = "Brunovisqui1@"; // Substitua pela sua senha do PostgreSQL

        try {
            // Carrega o driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Estabelece a conexão
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Conexão estabelecida com sucesso!");
                conn.close(); // Fecha a conexão após uso
            } else {
                System.out.println("Falha ao estabelecer a conexão.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado. Certifique-se de incluir o driver JDBC corretamente.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Falha ao conectar ao banco de dados PostgreSQL.");
            e.printStackTrace();
        }
    }
}
