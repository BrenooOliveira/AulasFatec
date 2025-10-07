package com.excrud.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import com.excrud.util.Conexao;


public class VeiculosDAO {
     public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                     "id_veiculo INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "marca TEXT," +
                     "modelo TEXT," +
                     "placa TEXT," +
                     "ano INT," +
                     "status TEXT," +
                     "FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente)" +
                     ");";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'veiculos' criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela veiculos: " + e.getMessage());
        }
    }


}
