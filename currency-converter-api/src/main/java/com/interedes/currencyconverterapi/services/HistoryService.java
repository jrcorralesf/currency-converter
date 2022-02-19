package com.interedes.currencyconverterapi.services;

import com.interedes.currencyconverterapi.dto.HistoryEntityDto;
import com.interedes.currencyconverterapi.entities.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryService {
    Page<HistoryEntity> list(Pageable pageable);
    HistoryEntity create(HistoryEntityDto data);
    void delete(Long id);
}
