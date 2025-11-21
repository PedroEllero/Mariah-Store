package com.mariah.mariah_store.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class VendaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_VendaItem;
     @Column(name = "VendaItem", nullable = false)
    private LocalDateTime data;
    private Long idCliente;
    private Double valorTotal;

    public Long getIdVendaItem() {
        return id_VendaItem;
    }

    public void setid_VendaItem(Long id_VendaItem) {
        this.id_VendaItem = id_VendaItem;
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
