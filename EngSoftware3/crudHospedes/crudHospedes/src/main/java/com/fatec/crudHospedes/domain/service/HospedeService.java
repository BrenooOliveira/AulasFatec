package com.fatec.crudHospedes.domain.service;

import java.util.List;

import com.fatec.crudHospedes.domain.model.HospedeModel;

public interface HospedeService {
    HospedeModel cadastrar(HospedeModel hospede);
    HospedeModel alterar(Long id, HospedeModel hospede);
    void inativar(Long id);
    HospedeModel consultar(Long id);
    List<HospedeModel> listarTodos();
}
