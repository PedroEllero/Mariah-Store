package com.mariah.mariah_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mariah.mariah_store.model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    long countByFornecedor_IdFornecedor(Long idFornecedor);

}
