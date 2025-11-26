package com.mariah.mariah_store.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mariah.mariah_store.dto.ClienteUpdateDTO;
import com.mariah.mariah_store.exception.BadRequestException;
import com.mariah.mariah_store.exception.ResourceNotFoundException;
import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // CREATE
    public ClienteModel criarCliente(ClienteModel cliente) {
        // aqui você pode validar regras de negócio e lançar BadRequestException se quiser
        return clienteRepository.save(cliente);
    }

    // READ - all
    public List<ClienteModel> listarClientes() {
        return clienteRepository.findAll();
    }

    // READ - by id
    public ClienteModel buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " não encontrado"));
    }

    // UPDATE
    public ClienteModel atualizarCliente(Long id, ClienteUpdateDTO dados) {

        ClienteModel cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        cliente.setNome(dados.getNome());
        cliente.setEmail(dados.getEmail());
        cliente.setTelefone(dados.getTelefone());

        return clienteRepository.save(cliente);
    }


    // DELETE
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente com id " + id + " não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    public ClienteModel login(String email, String senha) {

    ClienteModel cliente = clienteRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado."));

    // valida senha
    if (!cliente.getSenha().equals(senha)) {
        throw new BadRequestException("Senha incorreta.");
    }

    return cliente;
}

}
