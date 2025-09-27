import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsável pelas operações CRUD da tabela itens_ordem_servico
 */
public class ItemOrdemServicoDAO{

    /**
     * Cria a tabela itens_ordem_servico se não existir
     */
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS itens_ordem_servico (" +
                     "id_item INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "id_os INTEGER NOT NULL," +
                     "id_produto INTEGER NOT NULL," +
                     "qtde INTEGER NOT NULL," +
                     "FOREIGN KEY(id_os) REFERENCES ordem_servico(id_os)," +
                     "FOREIGN KEY(id_produto) REFERENCES produtos(id_produto)" +
                     ");";

        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'itens_ordem_servico' criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui a tabela itens_ordem_servico
     */
    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS itens_ordem_servico";
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'itens_ordem_servico' excluída.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir tabela: " + e.getMessage());
        }
    }

    /**
     * Insere um item de ordem de serviço
     */
    public static int insertInto(int idOs, int idProduto, int qtde) {
        String sql = "INSERT INTO itens_ordem_servico(id_os, id_produto, qtde) VALUES(?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idOs);
            stmt.setInt(2, idProduto);
            stmt.setInt(3, qtde);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // retorna o id_item gerado
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item: " + e.getMessage());
        }

        return -1; // retorna -1 se deu erro
    }
}
