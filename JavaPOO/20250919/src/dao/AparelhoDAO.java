import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsável pelas operações CRUD da tabela aparelhos
 */
public class AparelhoDAO {

    /**
     * Cria a tabela aparelhos se não existir
     */
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS aparelhos (" +
                     "id_aparelho INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "id_cliente INTEGER NOT NULL," +
                     "marca TEXT," +
                     "modelo TEXT," +
                     "categoria TEXT," +
                     "numero_serie TEXT," +
                     "status TEXT," +
                     "FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente)" +
                     ");";

        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'aparelhos' criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela aparelhos: " + e.getMessage());
        }
    }

    /**
     * Exclui a tabela aparelhos (DANGER: apaga tudo!)
     */
    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS aparelhos";
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'aparelhos' excluída.");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tabela aparelhos: " + e.getMessage());
        }
    }

    /**
     * Insere um novo aparelho
     */
    public static int insertInto(int idCliente, String marca, String modelo, String categoria,String numeroSerie, String status) {
        String sql = "INSERT INTO aparelhos(id_cliente, marca, modelo, categoria,numero_serie,status) VALUES (?, ?, ?, ? , ? , ?)";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idCliente);
            stmt.setString(2, marca);
            stmt.setString(3, modelo);
            stmt.setString(4, categoria);
            stmt.setString(5, numeroSerie);
            stmt.setString(6, status);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // retorna o id_aparelho gerado
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir aparelho: " + e.getMessage());
        }

        return -1; // se deu erro
    }

    /**
     * Lista todos os aparelhos
     */
    public static void listAparelhos() {
        String sql = "SELECT * FROM aparelhos";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== LISTA DE APARELHOS ===");
            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("id_aparelho") +
                    " | Cliente: " + rs.getInt("id_cliente") +
                    " | Marca: " + rs.getString("marca") +
                    " | Modelo: " + rs.getString("modelo") +
                    " | Categoria: " + rs.getString("categoria") +
                    " | Nº Série: " + rs.getString("numero_serie") +
                    " | Status: " + rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar aparelhos: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um aparelho
     */
    public static void updateAparelho(int idAparelho, String marca, String modelo, String categoria,String numeroSerie) {
        String sql = "UPDATE aparelhos SET marca = ?, modelo = ?,categoria = ?, numero_serie = ? WHERE id_aparelho = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca);
            stmt.setString(2, modelo);
            stmt.setString(3, categoria);
            stmt.setString(4, numeroSerie);
            stmt.setInt(5, idAparelho);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Aparelho atualizado com sucesso!");
            } else {
                System.out.println("Aparelho não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aparelho: " + e.getMessage());
        }
    }

    /**
     * Exclui um aparelho pelo ID
     */
    public static void deleteAparelho(int idAparelho) {
        String sql = "DELETE FROM aparelhos WHERE id_aparelho = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAparelho);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Aparelho excluído com sucesso!");
            } else {
                System.out.println("Aparelho não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir aparelho: " + e.getMessage());
        }
    }
}
