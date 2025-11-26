package com.mariah.mariah_store.dto;

import java.util.List;

public class VendaResponseDTO {

    private Long idVenda;
    private String data;
    private Double total;
    private String cliente;
    private List<VendaItemResponseDTO> itens;

    // GETTERS E SETTERS

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<VendaItemResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<VendaItemResponseDTO> itens) {
        this.itens = itens;
    }
}
