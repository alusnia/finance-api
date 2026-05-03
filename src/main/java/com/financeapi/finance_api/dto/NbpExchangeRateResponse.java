package com.financeapi.finance_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NbpExchangeRateResponse(List<NbpRateDetails> rates) {}
