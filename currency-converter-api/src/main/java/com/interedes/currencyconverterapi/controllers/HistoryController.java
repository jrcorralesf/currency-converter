package com.interedes.currencyconverterapi.controllers;

import com.interedes.currencyconverterapi.dto.HistoryEntityDto;
import com.interedes.currencyconverterapi.entities.HistoryEntity;
import com.interedes.currencyconverterapi.services.HistoryService;
import com.interedes.currencyconverterapi.utils.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoryController {

    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<HistoryEntity>> getHistoryList(@RequestParam Map<String,String> pageableParams) {
        Pageable pageable = CommonUtils.getRequestPagination(pageableParams);
        Page<HistoryEntity> historyList= service.list(pageable);
        return new ResponseEntity<>(historyList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<HistoryEntity> saveHistory(@Valid @RequestBody HistoryEntityDto data) {
        HistoryEntity historySaved= service.create(data);
        return new ResponseEntity<>(historySaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteHistory(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
