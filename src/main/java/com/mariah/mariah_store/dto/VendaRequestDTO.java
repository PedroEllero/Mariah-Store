package com.mariah.mariah_store.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VendaRequestDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    @NotEmpty(message = "A lista de itens não pode estar vazia.")
    private List<VendaItemRequestDTO> itens;

    // GETTERS E SETTERS

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<VendaItemRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<VendaItemRequestDTO> itens) {
        this.itens = itens;
    }
}
