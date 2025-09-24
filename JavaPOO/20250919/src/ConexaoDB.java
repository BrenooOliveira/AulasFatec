import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    // O banco será criado como um arquivo chamado "meubanco.db" na pasta do projeto
    private static final String URL = "jdbc:sqlite:meubanco.db";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Conexão estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
