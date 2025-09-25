import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:sqlite:consertoCelular.db"; // cria o banco se nao existir

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
