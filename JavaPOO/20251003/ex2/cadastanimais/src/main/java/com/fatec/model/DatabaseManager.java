package com.fatec.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:animais.db";

    public static Connection connect() {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.err.println("Erro de Conexão com o Banco de Dados: " + e.getMessage());
        }
        return conn;
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS animais (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nome TEXT NOT NULL,\n"
                + " especie TEXT NOT NULL,\n"
                + " idade INTEGER NOT NULL,\n"
                + " genero TEXT NOT NULL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public static void saveAnimal(Animal animal) {
        String sql = "INSERT INTO animais(nome, especie, idade, genero) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, animal.getNome());
            pstmt.setString(2, animal.getEspecie());
            pstmt.setInt(3, animal.getIdade());
            pstmt.setString(4, animal.getGenero());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao salvar animal: " + e.getMessage());
        }
    }

    public static List<Animal> getAllAnimals() {
        String sql = "SELECT nome, especie, idade, genero FROM animais";
        List<Animal> animais = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String especie = rs.getString("especie");
                int idade = rs.getInt("idade");
                String genero = rs.getString("genero");

                // Instancia a classe correta com base na espécie (Herança e Polimorfismo)
                Animal animal = createAnimalInstance(nome, especie, idade, genero);
                if (animal != null) {
                    animais.add(animal);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar animais: " + e.getMessage());
        }
        return animais;
    }

    private static Animal createAnimalInstance(String nome, String especie, int idade, String genero) {
        return switch (especie) {
            case "Cachorro" -> new Cachorro(nome, idade, genero);
            case "Gato" -> new Gato(nome, idade, genero);
            case "Papagaio" -> new Papagaio(nome, idade, genero);
            case "Hamster" -> new Hamster(nome, idade, genero);
            case "Peixe" -> new Peixe(nome, idade, genero);
            case "Cavalo" -> new Cavalo(nome, idade, genero);
            default -> null;
        };
    }
}
