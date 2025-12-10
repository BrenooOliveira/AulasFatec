package com.fatec.crudHospedes.domain.strategy.validacao;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.HospedeModel;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ValidarCamposHospede implements IValidacaoStrategy<HospedeModel>{
    @Override
    public void validar(HospedeModel h) {

        if (!StringUtils.hasText(h.getNome()))
            throw new BusinessException("Nome é obrigatório.");

        if (!StringUtils.hasText(h.getCpf()))
            throw new BusinessException("CPF é obrigatório.");

        if (!StringUtils.hasText(h.getEmail()))
            throw new BusinessException("E-mail é obrigatório.");

        if (!StringUtils.hasText(h.getTelefone()))
            throw new BusinessException("Telefone é obrigatório.");

        if (h.getDtNascimento() == null)
            throw new BusinessException("Data de nascimento é obrigatória.");
    }
}
