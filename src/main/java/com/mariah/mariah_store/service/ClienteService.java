package com.mariah.mariah_store.service;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

import com.mariah.mariah_store.dto.ClienteRequest;
import com.mariah.mariah_store.dto.ClienteUpdateDTO;
import com.mariah.mariah_store.exception.BadRequestException;
import com.mariah.mariah_store.exception.ResourceNotFoundException;
import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
    this.clienteRepository = clienteRepository;
    this.passwordEncoder = passwordEncoder;
}








   public void criarCliente(ClienteRequest request) {
    if (clienteRepository.findByEmail(request.email()).isPresent()) {
        throw new BadRequestException("E-mail já cadastrado.");
    }

    ClienteModel cliente = new ClienteModel(request, passwordEncoder);
    clienteRepository.save(cliente);
    
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
    boolean senhaCorreta = passwordEncoder.matches(senha, cliente.getSenha());
    if (!senhaCorreta) {
        throw new BadRequestException("Senha incorreta.");
    }

    return cliente;
}

}
