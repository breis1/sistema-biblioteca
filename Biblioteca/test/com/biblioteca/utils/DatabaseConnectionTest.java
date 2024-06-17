import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/biblioteca";
        String user = "postgres"; // substitua pelo seu usuário do PostgreSQL
        String password = "Brunovisqui1@"; // substitua pela sua senha do PostgreSQL

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao estabelecer a conexão.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
