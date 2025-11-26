package com.mariah.mariah_store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mariah.mariah_store.exception.BadRequestException;
import com.mariah.mariah_store.exception.ResourceNotFoundException;
import com.mariah.mariah_store.model.FornecedorModel;
import com.mariah.mariah_store.repository.FornecedorRepository;
import com.mariah.mariah_store.repository.ProdutoRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    

    public FornecedorService(FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<FornecedorModel> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public FornecedorModel salvar(FornecedorModel fornecedor) {
        if (fornecedor == null) {
            throw new BadRequestException("Fornecedor não pode ser nulo.");
        }
        if (!StringUtils.hasText(fornecedor.getNome())) {
            throw new BadRequestException("Nome do fornecedor é obrigatório.");
        }
        if (!StringUtils.hasText(fornecedor.getCnpj())) {
            throw new BadRequestException("CNPJ do fornecedor é obrigatório.");
        }
        // aqui você pode adicionar outras validações de negócio (formato de CNPJ, telefone, etc.)

        return fornecedorRepository.save(fornecedor);
    }

    public FornecedorModel buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor com id " + id + " não encontrado."));
    }

    public FornecedorModel atualizar(Long id, FornecedorModel dadosAtualizados) {
        if (dadosAtualizados == null) {
            throw new BadRequestException("Dados para atualização não podem ser nulos.");
        }

        FornecedorModel existente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor com id " + id + " não encontrado."));

        if (StringUtils.hasText(dadosAtualizados.getNome())) {
            existente.setNome(dadosAtualizados.getNome());
        }
        if (StringUtils.hasText(dadosAtualizados.getCnpj())) {
            existente.setCnpj(dadosAtualizados.getCnpj());
        }
        if (StringUtils.hasText(dadosAtualizados.getTelefone())) {
            existente.setTelefone(dadosAtualizados.getTelefone());
        }

        return fornecedorRepository.save(existente);
    }

public void deletar(Long id) {

    boolean existe = fornecedorRepository.existsById(id);
    if (!existe) {
        throw new ResourceNotFoundException("Fornecedor com ID " + id + " não encontrado.");
    }

    long produtosVinculados = produtoRepository.countByFornecedor_IdFornecedor(id);


    if (produtosVinculados > 0) {
        throw new BadRequestException(
            "Este fornecedor não pode ser removido pois possui produtos cadastrados."
        );
    }

    fornecedorRepository.deleteById(id);
}


}
