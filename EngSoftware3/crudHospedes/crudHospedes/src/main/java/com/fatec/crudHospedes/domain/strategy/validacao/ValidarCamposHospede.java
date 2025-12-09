package com.fatec.crudHospedes.domain.strategy.validacao;

import com.fatec.crudHospedes.domain.model.HospedeModel;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ValidarCamposHospede implements IValidacaoStrategy<HospedeModel>{
    @Override
    public void validar(HospedeModel h) {

        if (!StringUtils.hasText(h.getNome()))
            throw new RuntimeException("Nome é obrigatório.");

        if (!StringUtils.hasText(h.getCpf()))
            throw new RuntimeException("CPF é obrigatório.");

        if (!StringUtils.hasText(h.getEmail()))
            throw new RuntimeException("E-mail é obrigatório.");

        if (!StringUtils.hasText(h.getTelefone()))
            throw new RuntimeException("Telefone é obrigatório.");

        if (h.getDtNascimento() == null)
            throw new RuntimeException("Data de nascimento é obrigatória.");
    }
}
