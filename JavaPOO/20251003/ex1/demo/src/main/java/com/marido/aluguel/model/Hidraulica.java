package com.marido.aluguel.model;

public class Hidraulica extends Servico {
    public Hidraulica(String nome, String telefone, String bairro) {
        super(nome, telefone, bairro);
    }

    @Override
    public double valorHora() {
        return 70.0;
    }

    @Override
    public String veiculo() {
        return "Carro";
    }
}
