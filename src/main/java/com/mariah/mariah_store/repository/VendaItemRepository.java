package com.mariah.mariah_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mariah.mariah_store.model.VendaItemModel;

public interface VendaItemRepository extends JpaRepository<VendaItemModel, Long> {}