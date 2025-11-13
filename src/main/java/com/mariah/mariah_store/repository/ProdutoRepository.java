package com.mariah.mariah_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mariah.mariah_store.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}