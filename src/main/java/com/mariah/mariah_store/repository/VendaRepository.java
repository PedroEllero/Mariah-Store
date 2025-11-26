package com.mariah.mariah_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mariah.mariah_store.model.VendaModel;

public interface VendaRepository extends JpaRepository<VendaModel, Long> {

    List<VendaModel> findByClienteId(Long idCliente);
}