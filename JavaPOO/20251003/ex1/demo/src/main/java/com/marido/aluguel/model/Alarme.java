package com.marido.aluguel.model;

public class Alarme extends Servico {
    public Alarme(String nome, String telefone, String bairro) {
        super(nome, telefone, bairro);
    }

    @Override
    public double valorHora() {
        return 90.0;
    }

    @Override
    public String veiculo() {
        return "Carro";
    }
}
