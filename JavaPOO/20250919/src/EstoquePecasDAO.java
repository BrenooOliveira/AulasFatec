import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstoquePecasDAO {
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS estoque_pecas (" +
                     "id_produto INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nome_peca TEXT NOT NULL," +
                     "qtd_estoque INTEGER," +
                     "qtd_prevista INTEGER" +
                     ");";

        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'estoque_pecas' criada/verificada.");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public static void dropTable(){
        String sql = "DROP TABLE IF EXISTS estoque_pecas";
        try (Connection conn = ConexaoDB.conectar();
            Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                System.out.println("Tabela 'estoque_pecas' excluida.");
            }
        catch (Exception e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
            }
    }

    public static void inserir(String nome_peca, int qtd_estoque,int qtd_prevista) {
        String sql = "INSERT INTO estoque_pecas(nome_peca, qtd_estoque,qtd_prevista) VALUES(?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome_peca);
            stmt.setInt(2, qtd_estoque);
            stmt.setInt(3, qtd_prevista);
            stmt.executeUpdate();

            System.out.println("Produto inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public static void listar() {
        String sql = "SELECT * FROM estoque_pecas";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id_produto") + " | " +
                    rs.getString("nome_peca") + " | R$ " +
                    rs.getDouble("qtd_estoque") + " | R$ " +
                    rs.getDouble("qtd_prevista")
                );
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }
}
