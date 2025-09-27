import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsável pelas operações CRUD da tabela ordem_servico
 */
public class OrdemServicoDAO {

    /**
     * Cria a tabela ordem_servico se não existir
     */
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS ordem_servico (" +
                     "id_os INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "id_cliente INTEGER NOT NULL," +
                     "valor_total REAL," +
                     "status TEXT," +
                     "criado_em DATE," +
                     "prev_entrega DATE," +
                     "FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente)" +
                     ");";

        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'ordem_servico' criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui a tabela ordem_servico (DANGER: apaga tudo!)
     */
    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS ordem_servico";
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'ordem_servico' excluída.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir tabela: " + e.getMessage());
        }
    }

    /**
     * Insere uma nova ordem de serviço
     */
    public static int insertInto(int idCliente, double valorTotal, String status, String criadoEm, String prevEntrega) {
    String sql = "INSERT INTO ordem_servico(id_cliente, valor_total, status, criado_em, prev_entrega) " +
                 "VALUES(?, ?, ?, ?, ?)";

    try (Connection conn = ConexaoDB.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, idCliente);
        stmt.setDouble(2, valorTotal);
        stmt.setString(3, status);
        stmt.setString(4, criadoEm);
        stmt.setString(5, prevEntrega);

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // pega o id_os gerado
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao inserir ordem de serviço: " + e.getMessage());
    }

    return -1; // retorna -1 se deu erro
}


    /**
     * Lista todas as ordens de serviço
     */
    public static void listOs() {
        String sql = "SELECT * FROM ordem_servico";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                    "OS: " + rs.getInt("id_os") +
                    " | Cliente: " + rs.getInt("id_cliente") +
                    " | Valor: R$ " + rs.getDouble("valor_total") +
                    " | Status: " + rs.getString("status") +
                    " | Criado em: " + rs.getString("criado_em") +
                    " | Prev. Entrega: " + rs.getString("prev_entrega")
                );
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar ordens de serviço: " + e.getMessage());
        }
    }

    /**
     * Atualiza o status de uma OS
     */
    public static void updateStatus(int idOs, String novoStatus) {
        String sql = "UPDATE ordem_servico SET status = ? WHERE id_os = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idOs);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Status da ordem de serviço atualizado com sucesso!");
            } else {
                System.out.println("Ordem de serviço não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar ordem de serviço: " + e.getMessage());
        }
    }

    /**
     * Exclui uma OS pelo ID
     */
    public static void deleteOs(int idOs) {
        String sql = "DELETE FROM ordem_servico WHERE id_os = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOs);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Ordem de serviço excluída com sucesso!");
            } else {
                System.out.println("Ordem de serviço não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir ordem de serviço: " + e.getMessage());
        }
    }




}
