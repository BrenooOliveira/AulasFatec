package javaStudy.aulaCarro;

public class Carro{
    // Atributos
    String marca;
    String modelo;
    int ano;
    int velocidade; 

    // Métodos
    void acelerar(int aceleracao){
        velocidade += aceleracao;
    }
    
    void frear(int frenagem){
        velocidade -= frenagem;
    }

    void buzinar(){
        System.out.println("Biiiiiiiii");
    }
}