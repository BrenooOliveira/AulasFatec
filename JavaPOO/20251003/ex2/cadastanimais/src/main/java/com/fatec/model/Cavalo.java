package com.fatec.model;

public class Cavalo extends Animal{
    public Cavalo(String nome, int idade, String genero){
        super(nome,"Cavalo",idade,genero);
    }

    public String som(){
        return "Relincho";
    }
}
