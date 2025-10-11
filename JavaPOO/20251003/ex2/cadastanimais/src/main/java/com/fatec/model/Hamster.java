package com.fatec.model;

public class Hamster extends Animal{
    public Hamster(String nome, int idade, String genero){
        super(nome,"Hamster",idade,genero);
    }

    public String som(){
        return "Squeak Squeak";
    }
}
