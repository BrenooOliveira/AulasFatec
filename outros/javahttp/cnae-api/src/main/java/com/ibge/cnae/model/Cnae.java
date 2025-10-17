package com.ibge.cnae.model;

public class Cnae {
    private String id;
    private String descricao;

    public Cnae() {}

    public Cnae(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return id + " - " + descricao;
    }
}
