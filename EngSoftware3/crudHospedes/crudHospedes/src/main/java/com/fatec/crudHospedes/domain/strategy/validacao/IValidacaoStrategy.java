package com.fatec.crudHospedes.domain.strategy.validacao;

public interface IValidacaoStrategy<T>{
    void validar(T target);
}
