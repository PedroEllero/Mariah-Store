package com.mariah.mariah_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mariah.mariah_store.model.ClienteModel;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {}

