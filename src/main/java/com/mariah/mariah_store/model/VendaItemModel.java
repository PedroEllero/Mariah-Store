package com.mariah.mariah_store.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


@Entity
public class VendaItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVendaItem;
     @Column(name = "vendaItem", nullable = false)
    private LocalDateTime data;
    private Long idCliente;
    private Double valorTotal;

        @PrePersist
public void prePersist() {
    data = LocalDateTime.now();
}

    public Long getIdVendaItem() {
        return idVendaItem;
    }

    public void setIdVendaItem(Long idVendaItem) {
        this.idVendaItem = idVendaItem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
