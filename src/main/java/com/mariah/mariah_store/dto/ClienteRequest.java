package com.mariah.mariah_store.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
    @NotBlank(message = "O nome é obrigatório.")
     String nome,
     @Email(message = "E-mail inválido.")
     @NotBlank(message = "O e-mail é obrigatório.")
     String email,
     @NotBlank(message = "A senha é obrigatória.")
     @Size(min =3, max = 6, message = "A senha deve conter entre 6 e 20 caracteres.")
     String senha,
     @NotBlank(message = "O telefone é obrigatório.")
     @Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres.")
     String telefone,
     Set<String> roles
    ) {

}
