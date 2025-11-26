package com.mariah.mariah_store.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mariah.mariah_store.dto.ClienteResponse;
import com.mariah.mariah_store.dto.LoginRequest;
import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClienteModel> criar(@Valid @RequestBody ClienteModel cliente) {
        ClienteModel salvo = service.criarCliente(cliente);
        URI location = URI.create("/clientes/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<ClienteModel>> listar() {
        return ResponseEntity.ok(service.listarClientes()); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id)); // 200 OK ou 404 via exception
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteModel cliente) {
        ClienteModel atualizado = service.atualizarCliente(id, cliente);
        return ResponseEntity.ok(atualizado); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarCliente(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    ClienteModel cliente = service.login(request.getEmail(), request.getSenha());

    ClienteResponse response = new ClienteResponse(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail()
    );

    return ResponseEntity.ok(response);
}

}
