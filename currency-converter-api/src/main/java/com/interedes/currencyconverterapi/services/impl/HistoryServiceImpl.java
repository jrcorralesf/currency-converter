package com.interedes.currencyconverterapi.services.impl;

import com.interedes.currencyconverterapi.dto.HistoryEntityDto;
import com.interedes.currencyconverterapi.entities.HistoryEntity;
import com.interedes.currencyconverterapi.repositories.HistoryEntityRepository;
import com.interedes.currencyconverterapi.services.HistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final ModelMapper modelMapper;
    private final HistoryEntityRepository repository;

    public HistoryServiceImpl(HistoryEntityRepository repository,
                              ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<HistoryEntity> list(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Override
    public HistoryEntity create(HistoryEntityDto data) {
        HistoryEntity historyToStore = modelMapper.map(data, HistoryEntity.class);
        return repository.save(historyToStore);
    }

    @Override
    public void delete(Long id) {
        HistoryEntity historyToDelete = repository.getById(id);
        historyToDelete.setDeletedAt(LocalDateTime.now());
        repository.save(historyToDelete);
    }
}
