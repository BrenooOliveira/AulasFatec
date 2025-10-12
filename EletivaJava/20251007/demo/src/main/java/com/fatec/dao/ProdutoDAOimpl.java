package com.fatec.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import com.fatec.model.Produto;
import com.fatec.utils.Conexao;

public class ProdutoDAOimpl implements ProdutoDAO {
    private Connection conn;
    public ProdutoDAOimpl() {
        this.conn = Conexao.conectar();
    }


    @Override
    public void salvar(Produto produto) {
        // Implementação do método salvar
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2,produto.getPreco());
            stmt.setInt(3, produto.getEstoque());

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

    @Override
    public List<Produto> buscarTodos() {
        // Implementação do método buscarTodos
        String sql = "SELECT * FROM produto;";
        List<Produto> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("estoque")
                );
                lista.add(produto);
            }
        } catch (SQLException e) {
                System.out.println("Erro ao listar os produtos: " + e.getMessage());
            }
            return lista;
        }
    @Override
    public void transferirEstoque(int idOrigem, int idDestino, int quantidade) {
        String sqlRetirar = "UPDATE produto SET estoque = estoque - ? WHERE id = ? AND estoque >= ?;";
        String sqlAdicionar = "UPDATE produto SET estoque = estoque + ? WHERE id = ?;";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false); // Inicia a transação
            try (PreparedStatement stmtRetirar = conn.prepareStatement(sqlRetirar);
                 PreparedStatement stmtAdicionar = conn.prepareStatement(sqlAdicionar)) {

                // Retira do estoque do produto de origem
                stmtRetirar.setInt(1, quantidade);
                stmtRetirar.setInt(2, idOrigem);
                stmtRetirar.setInt(3, quantidade);
                int linhasAfetadas = stmtRetirar.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException("Estoque insuficiente ou produto de origem não encontrado.");
                }

                // Adiciona ao estoque do produto de destino
                stmtAdicionar.setInt(1, quantidade);
                stmtAdicionar.setInt(2, idDestino);
                linhasAfetadas = stmtAdicionar.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException("Produto de destino não encontrado.");
                }

                conn.commit(); // Confirma a transação
                System.out.println("Transferência realizada com sucesso.");
            } catch (SQLException e) {
                conn.rollback(); // Reverte a transação em caso de erro
                System.out.println("Erro na transferência: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // Restaura o modo autocommit
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}
