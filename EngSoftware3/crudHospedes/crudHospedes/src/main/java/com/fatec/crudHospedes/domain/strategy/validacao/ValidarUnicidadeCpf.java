package com.fatec.crudHospedes.domain.strategy.validacao;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.HospedeModel;
import com.fatec.crudHospedes.domain.repository.HospedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidarUnicidadeCpf implements IValidacaoStrategy<HospedeModel> {

    private final HospedeRepository repository;

    @Override
    public void validar(HospedeModel h)  {

        boolean jaExiste = repository.existsByCpf(h.getCpf());

        if (jaExiste)
            throw new BusinessException("CPF já está cadastrado.");
    }
}
