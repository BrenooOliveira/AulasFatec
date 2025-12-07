package com.fatec.crudHospedes.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.crudHospedes.domain.model.HospedeModel;
import com.fatec.crudHospedes.domain.repository.HospedeRepository;
import com.fatec.crudHospedes.domain.service.HospedeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospedeServiceImpl implements HospedeService {
    private final HospedeRepository repository;

    @Override
    public HospedeModel cadastrar(HospedeModel hospede){
        if (repository.existsByCpf(hospede.getCpf())){
            throw new RuntimeException("CPF já cadastrado.");
        }
        return repository.save(hospede);
    }


    @Override
    public HospedeModel alterar(Long id, HospedeModel hospede){
        HospedeModel existente = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hospede não encontrado."));

        existente.setNome(hospede.getNome());
        existente.setCpf(hospede.getCpf());
        existente.setDtNascimento(hospede.getDtNascimento());
        existente.setTelefone(hospede.getTelefone());
        existente.setEmail(hospede.getEmail());
        existente.setLogradouro(hospede.getLogradouro());

        return repository.save(existente);
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
