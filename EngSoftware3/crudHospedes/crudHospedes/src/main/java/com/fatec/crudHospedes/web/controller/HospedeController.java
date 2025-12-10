package com.fatec.crudHospedes.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.HospedeModel;
import com.fatec.crudHospedes.domain.service.HospedeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/hospedes")
public class HospedeController {

    private final HospedeService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista",service.listarTodos());

        return "hospedes/listar";
    }

    @GetMapping("/cadastrar")
    public String formCadastrar(Model model){
        model.addAttribute("hospede",new HospedeModel());
        return "hospedes/form";
    }

    @PostMapping
    public String cadastrar(HospedeModel hospede, Model model) {
        try {
            service.cadastrar(hospede);
            return "redirect:/hospedes";

        } catch (BusinessException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("hospede", hospede);
            return "hospedes/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model){
        model.addAttribute("hospede", service.consultar(id));
        return "hospedes/form";
    }

    @PostMapping("/{id}")
    public String alterar(@PathVariable Long id, HospedeModel hospede, Model model) {
        try {
            service.alterar(id, hospede);
            return "redirect:/hospedes";

        } catch (BusinessException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("hospede", hospede);
            return "hospedes/form";
        }
    }


    @GetMapping("/{id}/inativar")
    public String inativar(@PathVariable Long id){
        service.inativar(id);
        return "redirect:/hospedes";
    }


}
