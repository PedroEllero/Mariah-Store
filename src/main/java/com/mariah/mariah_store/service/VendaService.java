package com.mariah.mariah_store.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mariah.mariah_store.dto.VendaItemRequestDTO;
import com.mariah.mariah_store.dto.VendaItemResponseDTO;
import com.mariah.mariah_store.dto.VendaRequestDTO;
import com.mariah.mariah_store.dto.VendaResponseDTO;
import com.mariah.mariah_store.exception.BadRequestException;
import com.mariah.mariah_store.exception.ResourceNotFoundException;
import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.model.ProdutoModel;
import com.mariah.mariah_store.model.VendaItemModel;
import com.mariah.mariah_store.model.VendaModel;
import com.mariah.mariah_store.repository.ClienteRepository;
import com.mariah.mariah_store.repository.ProdutoRepository;
import com.mariah.mariah_store.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {

        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    // ===============================
    // REGISTRA UMA VENDA COMPLETA
    // ===============================
    public VendaResponseDTO registrarVenda(VendaRequestDTO dto) {

        // 1. VALIDAR CLIENTE
        ClienteModel cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente com ID " + dto.getClienteId() + " não encontrado."));

        // 2. VALIDAR ITENS
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new BadRequestException("A venda deve conter ao menos um item.");
        }

        // 3. CRIAR VENDA (a data é setada pelo @PrePersist)
        VendaModel venda = new VendaModel();
        venda.setCliente(cliente);

        List<VendaItemModel> itensModel = new ArrayList<>();

        double totalVenda = 0;

        // 4. PROCESSAR CADA ITEM
        for (VendaItemRequestDTO itemDTO : dto.getItens()) {

            ProdutoModel produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Produto com ID " + itemDTO.getProdutoId() + " não encontrado."));

            if (itemDTO.getQuantidade() <= 0) {
                throw new BadRequestException("Quantidade inválida para o produto " + produto.getNome());
            }

            double precoUnitario = produto.getPreco();
            double subtotal = precoUnitario * itemDTO.getQuantidade();

            totalVenda += subtotal;

            VendaItemModel item = new VendaItemModel();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(precoUnitario);
            item.setSubtotal(subtotal);
            item.setVenda(venda);

            itensModel.add(item);
        }

        // 5. FINALIZAR VENDA
        venda.setItens(itensModel);
        venda.setTotal(totalVenda);

        // 6. SALVAR NO BANCO
        VendaModel vendaSalva = vendaRepository.save(venda);

        // 7. CONVERTER PARA RESPONSE DTO
        return converterParaResponse(vendaSalva);
    }

    // ===============================
    // CONVERTE VENDA PARA RESPONSE DTO
    // ===============================
    private VendaResponseDTO converterParaResponse(VendaModel venda) {

        VendaResponseDTO dto = new VendaResponseDTO();
        dto.setIdVenda(venda.getIdVenda());
        dto.setTotal(venda.getTotal());
        dto.setCliente(venda.getCliente().getNome());

        String dataFormatada = venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        dto.setData(dataFormatada);

        // itens
        List<VendaItemResponseDTO> itensDTO = new ArrayList<>();

        for (VendaItemModel item : venda.getItens()) {
            VendaItemResponseDTO itemDTO = new VendaItemResponseDTO();
            itemDTO.setProduto(item.getProduto().getNome());
            itemDTO.setQuantidade(item.getQuantidade());
            itemDTO.setSubtotal(item.getSubtotal());
            itensDTO.add(itemDTO);
        }

        dto.setItens(itensDTO);

        return dto;
    }

    // ===============================
    // LISTAR TODAS AS VENDAS
    // ===============================
    public List<VendaResponseDTO> listarVendas() {

        List<VendaModel> vendas = vendaRepository.findAll();
        List<VendaResponseDTO> lista = new ArrayList<>();

        for (VendaModel venda : vendas) {
            lista.add(converterParaResponse(venda));
        }

        return lista;
    }

    // ===============================
    // BUSCAR VENDA POR ID
    // ===============================
    public VendaResponseDTO buscarPorId(Long id) {
        VendaModel venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Venda com ID " + id + " não encontrada."));

        return converterParaResponse(venda);
    }
}
