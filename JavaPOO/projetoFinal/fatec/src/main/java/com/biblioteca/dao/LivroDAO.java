package com.biblioteca.dao;

import com.biblioteca.model.*;
import com.biblioteca.util.ConexaoSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserir(Livro livro, Estoque est) {
        String sql1 = "INSERT INTO D_CADASTRO_LIVROS (nome_livro, dt_cadastro) VALUES (?, ?)";
        String sql2 = "INSERT INTO D_ESTOQUE_LIVROS (id_livro, qtd_estoque) VALUES (?, ?)";

        try (Connection conn = ConexaoSQLite.conectar()) {

            PreparedStatement stmt = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, livro.getNomeLivro());
            stmt.setString(2, livro.getDataCadastro().toString());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                est.setIdLivro(idGerado);
            }

            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1, est.getIdLivro());
            stmt2.setInt(2, est.getQuantidade());
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> listar() {
        String sql = """
        SELECT l.id_livro, l.nome_livro, l.dt_cadastro, e.qtd_estoque
        FROM D_CADASTRO_LIVROS l
        JOIN D_ESTOQUE_LIVROS e ON l.id_livro = e.id_livro
        """;

        List<Object[]> lista = new ArrayList<>();

        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_livro"),
                    rs.getString("nome_livro"),
                    rs.getString("dt_cadastro"),
                    rs.getInt("qtd_estoque")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(int id, String nome, int qtd) {
        String sql1 = "UPDATE D_CADASTRO_LIVROS SET nome_livro=? WHERE id_livro=?";
        String sql2 = "UPDATE D_ESTOQUE_LIVROS SET qtd_estoque=? WHERE id_livro=?";

        try (Connection conn = ConexaoSQLite.conectar()) {

            PreparedStatement stm1 = conn.prepareStatement(sql1);
            stm1.setString(1, nome);
            stm1.setInt(2, id);
            stm1.executeUpdate();

            PreparedStatement stm2 = conn.prepareStatement(sql2);
            stm2.setInt(1, qtd);
            stm2.setInt(2, id);
            stm2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql1 = "DELETE FROM D_ESTOQUE_LIVROS WHERE id_livro=?";
        String sql2 = "DELETE FROM D_CADASTRO_LIVROS WHERE id_livro=?";

        try (Connection conn = ConexaoSQLite.conectar()) {

            PreparedStatement s1 = conn.prepareStatement(sql1);
            s1.setInt(1, id);
            s1.executeUpdate();

            PreparedStatement s2 = conn.prepareStatement(sql2);
            s2.setInt(1, id);
            s2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
