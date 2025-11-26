package com.mariah.mariah_store.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mariah.mariah_store.auth.JwtUtil;
import com.mariah.mariah_store.dto.ClienteResponse;
import com.mariah.mariah_store.dto.ClienteUpdateDTO;
import com.mariah.mariah_store.dto.LoginRequest;
import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    private final JwtUtil jwtUtil;

    // ÚNICO CONSTRUTOR
    public ClienteController(ClienteService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ClienteModel> criar(@Valid @RequestBody ClienteModel cliente) {
        ClienteModel salvo = service.criarCliente(cliente);
        URI location = URI.create("/clientes/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    // READ ALL
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteModel>> listar() {
        return ResponseEntity.ok(service.listarClientes());
    }

    // READ BY ID
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // UPDATE
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateDTO dto) {

        ClienteModel atualizado = service.atualizarCliente(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // LOGIN + JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1) Login seguro (BCrypt)
        ClienteModel cliente = service.login(request.getEmail(), request.getSenha());

        // 2) Gera token JWT
        String token = jwtUtil.generateToken(cliente.getEmail(), cliente.getRoles());

        // 3) Monta resposta do usuário
        ClienteResponse response = new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail()
        );

        // 4) Retorno final
        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "usuario", response,
                        "roles", cliente.getRoles()
                )
        );
    }
}
