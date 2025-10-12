package com.fatec.dao;

import java.util.List;
import com.fatec.model.Produto;

public interface ProdutoDAO {
    public void salvar(Produto produto);
    public List<Produto> buscarTodos();
    public void transferirEstoque(int idProdutoOrigem, int idProdutoDestino, int quantidade);
    /*
    public static void criarTabela(){
        String sql = "CREATE TABLE IF NOT EXISTS produto (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nome VARCHAR(100) NOT NULL," +
                     "preco DECIMAL(10,2) NOT NULL," +
                     "estoque INTEGER NOT NULL" +
                     ");";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'produto' criada com sucesso ou já existia.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela 'produto': " + e.getMessage());
        }
    }

    public static void inserirProduto(String nome, double preco, int estoque) {
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?);";
        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.setDouble(2,preco);
            stmt.setInt(3, estoque);

            int linhasNovas = stmt.executeUpdate();

            if (linhasNovas > 0) {
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        int id = rs.getInt(1);
                        System.out.println("Produto inserido com ID: " + id);
                    }
                }
            }

            System.out.println("Produto inserido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o produto: " + e.getMessage());
        }
    }

    public static void listarProdutos() {
        String sql = "SELECT * FROM produto;";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Lista de Produtos:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                int estoque = rs.getInt("estoque");
                System.out.printf("ID: %d, Nome: %s, Preço: %.2f, Estoque: %d%n", id, nome, preco, estoque);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os produtos: " + e.getMessage());
        }
    }

    public static void acharProduto(int idProduto) {
            String sql = "SELECT * FROM produto WHERE id = ?";
            try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idProduto);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String nome = rs.getString("nome");
                        double preco = rs.getDouble("preco");
                        int estoque = rs.getInt("estoque");
                        System.out.printf("Produto encontrado - Nome: %s, Preço: %.2f, Estoque: %d%n", nome, preco, estoque);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erro ao procurar o produto: " + e.getMessage());
            }
        }
     */
}
