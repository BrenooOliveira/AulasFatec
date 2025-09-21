package javaStudy.aulaCarro;

public class CarroTestar {
    public static void main(String[] args) {

        Carro carro1 = new Carro();

        carro1.ano = 2021;
        carro1.marca = "Ford";
        carro1.modelo = "Fiesta";
        carro1.velocidade = 100;

        carro1.acelerar(10);

        carro1.buzinar();

    }
}
