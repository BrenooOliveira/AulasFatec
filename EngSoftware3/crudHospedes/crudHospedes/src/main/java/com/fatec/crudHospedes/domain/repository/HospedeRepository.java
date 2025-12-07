package com.fatec.crudHospedes.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.crudHospedes.domain.model.HospedeModel;

public interface HospedeRepository extends JpaRepository<HospedeModel, Long>{
    boolean existsByCpf(String cpf); // spring cria o select exists cpf
}
