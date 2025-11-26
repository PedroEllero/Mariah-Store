package com.mariah.mariah_store.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.repository.ClienteRepository;

@Component("securityUtils")
public class SecurityUtils {

    private final ClienteRepository clienteRepository;

    public SecurityUtils(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean isOwner(Long clienteId) {
        // Pega a autenticação
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Se não tiver usuário logado → não é dono
        if (auth == null || auth.getName() == null) {
            return false;
        }

        String emailLogado = auth.getName();

        // Busca o cliente no banco
        ClienteModel cliente = clienteRepository.findById(clienteId).orElse(null);

        // Se ID não existe → não é dono
        if (cliente == null) {
            return false;
        }

        // Compara email do dono com email do token
        return cliente.getEmail().equals(emailLogado);
    }
}
