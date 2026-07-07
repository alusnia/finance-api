package com.financeapi.finance_api.fx.internal.reference.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NbpExchangeRateResponse(List<NbpRateDetails> rates) {}
