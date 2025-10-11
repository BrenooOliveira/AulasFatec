package com.fatec.model;

public abstract class Animal {
    private String nome;
    private String especie;
    private int idade;
    private String genero;

    // Construtor 1 (Sobrecarga)
    public Animal(String nome, String especie, int idade, String genero) {
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.genero = genero;
    }

    // Construtor 2 (Sobrecarga)
    public Animal(String especie) {
        this.especie = especie;
        this.nome = "Sem Nome";
        this.idade = 0;
        this.genero = "Desconhecido";
    }

    // Método Abstrato (Polimorfismo)
    public abstract String som();

    // Getters
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public int getIdade() { return idade; }
    public String getGenero() { return genero; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setGenero(String genero) { this.genero = genero; }

    // Setters desnecessários, apenas para demonstração de Sobrecarga (não usados no app)
    public void setEspecie(String especie) { this.especie = especie; }
}
