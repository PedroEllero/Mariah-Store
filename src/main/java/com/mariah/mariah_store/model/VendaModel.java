package com.mariah.mariah_store.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


@Entity
public class VendaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenda;
     @Column(name = "venda", nullable = false)
    private LocalDateTime data;
    private Long idCliente;
    private Double valorTotal;
    
    @PrePersist
public void prePersist() {
    data = LocalDateTime.now();
}


       public Long getidVenda() {
        return idVenda;
    }

    public void setidVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public Long getidCliente() {
        return idCliente;
    }

    public void setidCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
