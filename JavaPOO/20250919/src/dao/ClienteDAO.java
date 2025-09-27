import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsável pelas operações CRUD da tabela clientes
 */
public class ClienteDAO {

    /**
     * Cria a tabela clientes se não existir
     */
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                     "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nome TEXT NOT NULL," +
                     "telefone TEXT," +
                     "email TEXT" +
                     ");";

        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'clientes' criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela clientes: " + e.getMessage());
        }
    }

    /**
     * Exclui a tabela clientes (DANGER: apaga tudo!)
     */
    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS clientes";
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'clientes' excluída.");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tabela clientes: " + e.getMessage());
        }
    }

    /**
     * Insere um novo cliente
     */
    public static int insertInto(String nome, String telefone, String email) {
        String sql = "INSERT INTO clientes(nome, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, email);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // retorna o id_cliente gerado
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }

        return -1; // se deu erro
    }

    /**
     * Lista todos os clientes
     */
    public static void listClients() {
        String sql = "SELECT * FROM clientes";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== LISTA DE CLIENTES ===");
            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("id_cliente") +
                    " | Nome: " + rs.getString("nome") +
                    " | Telefone: " + rs.getString("telefone") +
                    " | Email: " + rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um cliente
     */
    public static void updateClient(int idCliente, String nome, String telefone, String email) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, email = ? WHERE id_cliente = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, email);
            stmt.setInt(4, idCliente);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Exclui um cliente pelo ID
     */
    public static void deleteClient(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Cliente excluído com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }


        /**
     * Lista um cliente específico pelo ID
     * @param idCliente ID do cliente a ser buscado
     */
    public static void listSpecificClient(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection conn = ConexaoDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o parâmetro do PreparedStatement
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n=== CLIENTE ESPECÍFICO ===");
                if (rs.next()) {
                    System.out.println(
                        "ID: " + rs.getInt("id_cliente") +
                        " | Nome: " + rs.getString("nome") +
                        " | Telefone: " + rs.getString("telefone") +
                        " | Email: " + rs.getString("email")
                    );
                } else {
                    System.out.println("Nenhum cliente encontrado com o ID " + idCliente);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cliente: " + e.getMessage());
        }
    }

    /**
     * Lista as OS específico pelo ID
     * @param idCliente ID do cliente a ser buscado
     */
    public static void getOSperClient(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection conn = ConexaoDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o parâmetro do PreparedStatement
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n=== CLIENTE ESPECÍFICO ===");
                if (rs.next()) {
                    System.out.println(
                        "ID: " + rs.getInt("id_cliente") +
                        " | Nome: " + rs.getString("nome") +
                        " | Telefone: " + rs.getString("telefone") +
                        " | Email: " + rs.getString("email")
                    );
                } else {
                    System.out.println("Nenhum cliente encontrado com o ID " + idCliente);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cliente: " + e.getMessage());
        }
    }

}
