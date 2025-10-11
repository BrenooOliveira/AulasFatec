package com.fatec.model;

public class Gato extends Animal{
    public Gato(String nome, int idade, String genero){
        super(nome,"Gato",idade,genero);
    }

    public String som(){
        return "Miau";
    }
}
