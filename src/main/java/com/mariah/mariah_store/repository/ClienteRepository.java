package com.mariah.mariah_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mariah.mariah_store.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}

