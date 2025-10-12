package com.fatec.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:sqlite:mybd.db";

    public static Connection conectar(){
        try{
            System.err.println("Conexão estabelecida com sucesso!");
            return DriverManager.getConnection(URL);
        }catch(SQLException e){
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
