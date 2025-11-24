package com.mariah.mariah_store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class FornecedorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Fornecedor;
    private String nome;
    private String cnpj;
    private String telefone;

     public Long id_Fornecedor() {
        return id_Fornecedor;
    }

    public void id_Fornecedor(Long id_Fornecedor) {
        this.id_Fornecedor = id_Fornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
