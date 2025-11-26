package com.mariah.mariah_store.service;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
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

   public ClienteModel criarCliente(ClienteModel cliente) {

    // VALIDAÇÕES ------------------------

    // Nome obrigatório
    if (cliente.getNome() == null || cliente.getNome().isBlank()) {
        throw new BadRequestException("O nome é obrigatório.");
    }

    // Email obrigatório
    if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
        throw new BadRequestException("O e-mail é obrigatório.");
    }

    // Email já existe
    if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
        throw new BadRequestException("Já existe um cliente cadastrado com esse e-mail.");
    }

    // Telefone
    if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
        throw new BadRequestException("O telefone é obrigatório.");
    }

    // Senha obrigatória (antes do encode)
    if (cliente.getSenha() == null || cliente.getSenha().isBlank()) {
        throw new BadRequestException("A senha é obrigatória.");
    }

    // -----------------------------------

    // Codificar senha com BCrypt (obrigatório para JWT)
    cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));

    // Roles padrão
    if (cliente.getRoles() == null || cliente.getRoles().isEmpty()) {
        cliente.setRoles(Set.of("ROLE_USER"));
    }

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
    boolean senhaCorreta = passwordEncoder.matches(senha, cliente.getSenha());
    if (!senhaCorreta) {
        throw new BadRequestException("Senha incorreta.");
    }

    return cliente;
}

}
