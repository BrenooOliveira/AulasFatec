package com.excrud.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    private static final String URL = "jdbc:sqlite:veiculos.db";

    public static Connection conectar(){
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Conectado com sucesso!");
            return conn;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
