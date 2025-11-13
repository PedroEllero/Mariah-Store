package com.mariah.mariah_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    @Column(unique = true)
    private String email;

    private String telefone;

    // Getters e setters
}