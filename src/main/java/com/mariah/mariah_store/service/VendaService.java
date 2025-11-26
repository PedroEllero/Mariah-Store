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
import com.mariah.mariah_store.repository.VendaItemRepository;
import com.mariah.mariah_store.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaItemRepository vendaItemRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(
            VendaRepository vendaRepository,
            VendaItemRepository vendaItemRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {

        this.vendaRepository = vendaRepository;
        this.vendaItemRepository = vendaItemRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    // ============================================
    // REGISTRAR VENDA
    // ============================================
    public VendaResponseDTO registrarVenda(VendaRequestDTO dto) {

        ClienteModel cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new BadRequestException("A venda deve conter ao menos um item.");
        }

        VendaModel venda = new VendaModel();
        venda.setCliente(cliente);

        List<VendaItemModel> itensModel = new ArrayList<>();
        double total = 0;

        for (VendaItemRequestDTO item : dto.getItens()) {

            ProdutoModel produto = produtoRepository.findById(item.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

            if (item.getQuantidade() <= 0) {
                throw new BadRequestException("Quantidade inválida.");
            }

            double preco = produto.getPreco();
            double subtotal = preco * item.getQuantidade();

            VendaItemModel vi = new VendaItemModel();
            vi.setProduto(produto);
            vi.setQuantidade(item.getQuantidade());
            vi.setPrecoUnitario(preco);
            vi.setSubtotal(subtotal);
            vi.setVenda(venda);

            total += subtotal;
            itensModel.add(vi);
        }

        venda.setTotal(total);
        venda.setItens(itensModel);

        VendaModel salvo = vendaRepository.save(venda);

        return converterParaResponse(salvo);
    }

    public VendaResponseDTO buscarPorId(Long id) {
    VendaModel venda = vendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Venda com ID " + id + " não encontrada."));

    return converterParaResponse(venda);
}


    // ============================================
    // CONVERTER PARA RESPONSE
    // ============================================
    private VendaResponseDTO converterParaResponse(VendaModel venda) {

        VendaResponseDTO dto = new VendaResponseDTO();
        dto.setIdVenda(venda.getIdVenda());
        dto.setCliente(venda.getCliente().getNome());
        dto.setTotal(venda.getTotal());

        String data = venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        dto.setData(data);

        List<VendaItemResponseDTO> itensDTO = new ArrayList<>();

        for (VendaItemModel i : venda.getItens()) {
            VendaItemResponseDTO itemDTO = new VendaItemResponseDTO(
                    i.getProduto().getNome(),
                    i.getQuantidade(),
                    i.getSubtotal()
            );
            itensDTO.add(itemDTO);
        }

        dto.setItens(itensDTO);
        return dto;
    }

    // ============================================
    // LISTAR VENDAS DO CLIENTE
    // ============================================
    public List<VendaResponseDTO> listarVendasPorCliente(Long clienteId) {

        List<VendaModel> vendas = vendaRepository.findByClienteId(clienteId);

        List<VendaResponseDTO> lista = new ArrayList<>();
        for (VendaModel v : vendas) {
            lista.add(converterParaResponse(v));
        }

        return lista;

    }

    public List<VendaResponseDTO> listarVendas() {
    List<VendaModel> vendas = vendaRepository.findAll();

    List<VendaResponseDTO> lista = new ArrayList<>();

    for (VendaModel venda : vendas) {
        lista.add(converterParaResponse(venda));
    }

    return lista;
}


}
