package com.mariah.mariah_store.controller;

import org.springframework.stereotype.Controller;
import com.mariah.mariah_store.repository.VendaRepository;
import com.mariah.mariah_store.repository.ClienteRepository;
import com.mariah.mariah_store.repository.ProdutoRepository;

@Controller
public class VendaViewController {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaViewController(VendaRepository vendaRepository,
                               ClienteRepository clienteRepository,
                               ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

   
}