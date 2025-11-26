package com.mariah.mariah_store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VendaItemRequestDTO {

    @NotNull(message = "O ID do produto é obrigatório.")
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "Quantidade mínima é 1.")
    private Integer quantidade;

    // GETTERS E SETTERS
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
