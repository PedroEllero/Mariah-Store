package com.mariah.mariah_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mariah.mariah_store.model.FornecedorModel;
import com.mariah.mariah_store.service.FornecedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    // LISTAR TODOS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<FornecedorModel> listar() {
        return fornecedorService.listarTodos();
    }

    // BUSCAR POR ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public FornecedorModel buscarPorId(@PathVariable Long id) {
        return fornecedorService.buscarPorId(id);
    }

    // CRIAR
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorModel criar(@Valid @RequestBody FornecedorModel fornecedor) {
        return fornecedorService.salvar(fornecedor);
    }

    // ATUALIZAR
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public FornecedorModel atualizar(@PathVariable Long id,
                                     @Valid @RequestBody FornecedorModel fornecedorAtualizado) {
        return fornecedorService.atualizar(id, fornecedorAtualizado);
    }

    // DELETAR
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
    }
}
