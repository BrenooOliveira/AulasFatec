package com.fatec.crudHospedes.domain.strategy.validacao;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.HospedeModel;
import org.springframework.stereotype.Component;

@Component
public class ValidarEmailHospede implements IValidacaoStrategy<HospedeModel> {

    @Override
    public void validar(HospedeModel h) {

        String email = h.getEmail();

        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new BusinessException("E-mail inválido.");
        }
    }
}
