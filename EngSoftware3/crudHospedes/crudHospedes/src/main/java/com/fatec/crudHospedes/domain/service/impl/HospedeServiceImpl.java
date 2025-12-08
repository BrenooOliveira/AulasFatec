package com.fatec.crudHospedes.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.crudHospedes.domain.model.HospedeModel;
import com.fatec.crudHospedes.domain.repository.HospedeRepository;
import com.fatec.crudHospedes.domain.service.HospedeService;
import com.fatec.crudHospedes.domain.strategy.validacao.IValidacaoStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospedeServiceImpl implements HospedeService {

    private final HospedeRepository repository;

    // Todas as validações para HospedeModel são injetadas automaticamente
    private final List<IValidacaoStrategy<HospedeModel>> validacoes;

    @Override
    public HospedeModel cadastrar(HospedeModel hospede){

        executarValidacoes(hospede);

        return repository.save(hospede);
    }

    @Override
    public HospedeModel alterar(Long id, HospedeModel hospede){
        HospedeModel existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospede não encontrado."));

        // Evita conflito ao alterar CPF
        if (!existente.getCpf().equals(hospede.getCpf())) {
            executarValidacoes(hospede);
        } else {
            // Executa todas exceto unicidade do CPF
            validacoes.stream()
                    .filter(v -> !v.getClass().getSimpleName().equals("ValidarUnicidadeCpf"))
                    .forEach(v -> v.validar(hospede));
        }

        existente.setNome(hospede.getNome());
        existente.setCpf(hospede.getCpf());
        existente.setDtNascimento(hospede.getDtNascimento());
        existente.setTelefone(hospede.getTelefone());
        existente.setEmail(hospede.getEmail());
        existente.setLogradouro(hospede.getLogradouro());

        return repository.save(existente);
    }

    private void executarValidacoes(HospedeModel hospede) {
        validacoes.forEach(v -> v.validar(hospede));
    }

    @Override
    public void inativar(Long id){
        HospedeModel existente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hospede não encontrado."));

        existente.setAtivo(false);
        repository.save(existente);
    }

    @Override
    public HospedeModel consultar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hospede não encontrado."));
    }

    @Override
    public List<HospedeModel> listarTodos(){
        return repository.findAll()
            .stream()
            .filter(HospedeModel::getAtivo)
            .toList();
    }
}
