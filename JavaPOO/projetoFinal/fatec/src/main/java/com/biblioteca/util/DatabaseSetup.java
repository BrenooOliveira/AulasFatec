package com.biblioteca.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void inicializar() {
        try (Connection conn = ConexaoSQLite.conectar();
             Statement stmt = conn.createStatement()) {

            // Tabela de Livros
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS D_CADASTRO_LIVROS (
                    id_livro INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome_livro TEXT NOT NULL,
                    dt_cadastro TEXT NOT NULL
                );
            """);

            // Estoque
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS D_ESTOQUE_LIVROS (
                    id_livro INTEGER PRIMARY KEY,
                    qtd_estoque INTEGER NOT NULL,
                    FOREIGN KEY (id_livro) REFERENCES D_CADASTRO_LIVROS(id_livro)
                );
            """);

            // Empréstimos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS F_EMPRESTIMOS (
                    id_emprestimo INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_livro INTEGER NOT NULL,
                    nome_cliente TEXT NOT NULL,
                    tp_cliente TEXT NOT NULL,
                    dt_emprestimo TEXT NOT NULL,
                    dt_devolucao TEXT,
                    status TEXT NOT NULL,
                    FOREIGN KEY (id_livro) REFERENCES D_CADASTRO_LIVROS(id_livro)
                );
            """);

            System.out.println("Tabelas verificadas/criadas com sucesso.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
