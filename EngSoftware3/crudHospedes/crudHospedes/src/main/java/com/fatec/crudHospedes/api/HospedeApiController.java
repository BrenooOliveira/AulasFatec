package com.fatec.crudHospedes.api;

import com.fatec.crudHospedes.domain.model.HospedeModel;
import com.fatec.crudHospedes.domain.service.HospedeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
@RequiredArgsConstructor
public class HospedeApiController {

    private final HospedeService service;

    // LISTAR TODOS
    @GetMapping
    public List<HospedeModel> listar() {
        return service.listarTodos();
    }

    // CONSULTAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<HospedeModel> consultar(@PathVariable Long id) {
        HospedeModel encontrado = service.consultar(id);
        return ResponseEntity.ok(encontrado);
    }

    // CADASTRAR
    @PostMapping
    public ResponseEntity<HospedeModel> criar(@RequestBody HospedeModel hospede) {
        HospedeModel salvo = service.cadastrar(hospede);
        return ResponseEntity.ok(salvo);
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<HospedeModel> atualizar(
        @PathVariable Long id,
        @RequestBody HospedeModel hospede
    ) {
        HospedeModel atualizado = service.alterar(id, hospede);
        return ResponseEntity.ok(atualizado);
    }

    // INATIVAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
