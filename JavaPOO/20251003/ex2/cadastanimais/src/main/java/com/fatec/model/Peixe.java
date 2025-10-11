package com.fatec.model;

public class Peixe extends Animal {
    public Peixe(String nome, int idade, String genero) {
        super(nome, "Peixe", idade, genero);
    }

    @Override
    public String som() {
        return "Glub Glub!";
    }
}
