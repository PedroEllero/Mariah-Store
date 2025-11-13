package com.mariah.mariah_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Fornecedor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String contato;

    // Getters e setters
}
