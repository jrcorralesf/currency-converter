package com.interedes.currencyconverterapi.repositories;

import com.interedes.currencyconverterapi.entities.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryEntityRepository extends JpaRepository<HistoryEntity, Long> {
    Page<HistoryEntity> findAllBy(Pageable pageable);
}