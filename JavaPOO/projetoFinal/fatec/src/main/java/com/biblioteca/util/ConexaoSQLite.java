package com.biblioteca.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoSQLite {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao SQLite", e);
        }
    }
}
