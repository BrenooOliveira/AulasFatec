package com.marido.aluguel.model;

public abstract class Servico {
    protected String nome;
    protected String telefone;
    protected String bairro;

    public Servico(String nome, String telefone, String bairro) {
        this.nome = nome;
        this.telefone = telefone;
        this.bairro = bairro;
    }

    // Método que será sobrescrito
    public abstract double valorHora();

    // Sobrecarga
    public double valorHora(double desconto) {
        return valorHora() - desconto;
    }

    // Método sobrescrito nas subclasses
    public abstract String veiculo();

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getBairro() { return bairro; }

    @Override
    public String toString() {
        return String.format("Nome: %s | Tel: %s | Bairro: %s | Serviço: %s | Valor/h: R$%.2f | Veículo: %s",
                nome, telefone, bairro, this.getClass().getSimpleName(),
                valorHora(), veiculo());
    }
}
