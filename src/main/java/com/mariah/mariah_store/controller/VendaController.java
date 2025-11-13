package com.mariah.mariah_store.controller;

import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.RequestMapping;



import com.mariah.mariah_store.repository.ClienteRepository;
import com.mariah.mariah_store.repository.ProdutoRepository;
import com.mariah.mariah_store.service.VendaService;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaController(VendaService vendaService,
                           ClienteRepository clienteRepository,
                           ProdutoRepository produtoRepository) {
        this.vendaService = vendaService;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    
}