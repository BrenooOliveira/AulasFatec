package com.marido.aluguel.model;

public class Eletrica extends Servico {
    public Eletrica(String nome, String telefone, String bairro) {
        super(nome, telefone, bairro);
    }

    @Override
    public double valorHora() {
        return 80.0; // preço fixo hora elétrica
    }

    @Override
    public String veiculo() {
        return "Carro";
    }
}
