package com.fatec.model;

public class Cachorro extends Animal {
    public Cachorro(String nome, int idade, String genero) {
        super(nome, "Cachorro", idade, genero);
    }

    @Override
    public String som() {
        return "Au Au!";
    }
}
