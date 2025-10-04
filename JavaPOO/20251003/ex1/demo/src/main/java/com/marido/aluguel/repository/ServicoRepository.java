package com.marido.aluguel.repository;

import com.marido.aluguel.model.Servico;
import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {
    private List<Servico> servicos = new ArrayList<>();

    public void addServico(Servico servico) {
        servicos.add(servico);
    }

    public List<Servico> getServicos() {
        return servicos;
    }
}
