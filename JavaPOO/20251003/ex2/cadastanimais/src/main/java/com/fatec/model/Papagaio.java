package com.fatec.model;

public class Papagaio extends Animal {
    public Papagaio(String nome, int idade, String genero) {
        super(nome, "Papagaio", idade, genero);
    }

    @Override
    public String som() {
        return "Olá! (imita sons)";
    }
}
