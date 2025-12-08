package com.fatec.crudHospedes.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hospedes")
@Getter @Setter // lombok
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospedeModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nome;

    private String cpf;
    private LocalDate dtNascimento;

    private String telefone;

    private String email;
    private String logradouro;

    private Boolean ativo = true;

}
