package com.mariah.mariah_store.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Venda;
     @Column(name = "Venda", nullable = false)
    private LocalDateTime data;
    private Long idCliente;
    private Double valorTotal;

       public Long getid_Venda() {
        return id_Venda;
    }

    public void setid_Venda(Long id_Venda) {
        this.id_Venda = id_Venda;
    }

    public Long getidcliente() {
        return idCliente;
    }

    public void setClienteId(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
