package com.mariah.mariah_store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mariah.mariah_store.exception.BadRequestException;
import com.mariah.mariah_store.exception.ResourceNotFoundException;
import com.mariah.mariah_store.model.FornecedorModel;
import com.mariah.mariah_store.model.ProdutoModel;
import com.mariah.mariah_store.repository.FornecedorRepository;
import com.mariah.mariah_store.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<ProdutoModel> listarTodos() {
        return produtoRepository.findAll();
    }

    public ProdutoModel salvar(ProdutoModel produto) {

        if (produto == null) {
            throw new BadRequestException("Produto não pode ser nulo.");
        }

        if (!StringUtils.hasText(produto.getNome())) {
            throw new BadRequestException("Nome do produto é obrigatório.");
        }

        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new BadRequestException("Preço inválido.");
        }

        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new BadRequestException("Estoque inválido.");
        }

        if (produto.getFornecedor() == null || produto.getFornecedor().getIdFornecedor() == null) {
            throw new BadRequestException("Fornecedor é obrigatório.");
        }
        if (!StringUtils.hasText(produto.getCategoria())) {
    throw new BadRequestException("Categoria é obrigatória.");
        }

        if (!StringUtils.hasText(produto.getImagem())) {
            produto.setImagem("imagens/default.jpg"); // padrão
        }

        if (!StringUtils.hasText(produto.getDescricao())) {
            produto.setDescricao("Produto sem descrição.");
}


        // Verificar se o fornecedor existe
        FornecedorModel fornecedor = fornecedorRepository.findById(
            produto.getFornecedor().getIdFornecedor()
        ).orElseThrow(() -> new ResourceNotFoundException(
            "Fornecedor com id " + produto.getFornecedor().getIdFornecedor() + " não encontrado."
        ));

        produto.setFornecedor(fornecedor); // evitar referências inválidas

        return produtoRepository.save(produto);
    }

    public ProdutoModel buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado."));
    }

    public ProdutoModel atualizar(Long id, ProdutoModel dadosAtualizados) {

        ProdutoModel existente = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado."));

        if (StringUtils.hasText(dadosAtualizados.getNome())) {
            existente.setNome(dadosAtualizados.getNome());
        }

        if (dadosAtualizados.getPreco() != null && dadosAtualizados.getPreco() > 0) {
            existente.setPreco(dadosAtualizados.getPreco());
        }

        if (dadosAtualizados.getEstoque() != null && dadosAtualizados.getEstoque() >= 0) {
            existente.setEstoque(dadosAtualizados.getEstoque());
        }

        if (dadosAtualizados.getFornecedor() != null && 
            dadosAtualizados.getFornecedor().getIdFornecedor() != null) {

            FornecedorModel fornecedor = fornecedorRepository.findById(
                dadosAtualizados.getFornecedor().getIdFornecedor()
            ).orElseThrow(() -> new ResourceNotFoundException(
                "Fornecedor com id " + dadosAtualizados.getFornecedor().getIdFornecedor() + " não encontrado."
            ));

            existente.setFornecedor(fornecedor);
        }

        return produtoRepository.save(existente);

    }

    public void deletar(Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestException(
                "Este produto não pode ser removido pois já foi utilizado em vendas."
            );
        }
    }

}
