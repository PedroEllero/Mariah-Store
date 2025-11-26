package com.mariah.mariah_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mariah.mariah_store.model.ProdutoModel;
import com.mariah.mariah_store.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // LISTAR TODOS
    @GetMapping
    public List<ProdutoModel> listar() {
        return produtoService.listarTodos();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ProdutoModel buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    // CRIAR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel criar(@Valid @RequestBody ProdutoModel produto) {
        return produtoService.salvar(produto);
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public ProdutoModel atualizar(@PathVariable Long id, 
                                  @Valid @RequestBody ProdutoModel produtoAtualizado) {
        return produtoService.atualizar(id, produtoAtualizado);
    }

    // DELETAR
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}
