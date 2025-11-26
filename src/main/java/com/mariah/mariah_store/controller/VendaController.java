package com.mariah.mariah_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mariah.mariah_store.dto.VendaRequestDTO;
import com.mariah.mariah_store.dto.VendaResponseDTO;
import com.mariah.mariah_store.service.VendaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // =====================
    // REGISTRAR UMA VENDA
    // =====================
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponseDTO registrar(@Valid @RequestBody VendaRequestDTO dto) {
        return vendaService.registrarVenda(dto);
    }

    // =====================
    // LISTAR TODAS AS VENDAS
    // =====================
    @GetMapping
    public List<VendaResponseDTO> listarTodas() {
        return vendaService.listarVendas();
    }

    // =====================
    // BUSCAR VENDA POR ID
    // =====================
    @GetMapping("/{id}")
    public VendaResponseDTO buscarPorId(@PathVariable Long id) {
        return vendaService.buscarPorId(id);
    }
    @GetMapping("/cliente/{clienteId}")
public List<VendaResponseDTO> listarPorCliente(@PathVariable Long clienteId) {
    return vendaService.listarVendasPorCliente(clienteId);
}

}
