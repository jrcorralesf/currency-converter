package com.interedes.currencyconverterapi.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HistoryEntityDto implements Serializable {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private double amount;
    private double conversionValue;
}
