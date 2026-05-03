package com.financeapi.finance_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NbpRateDetails(
		@JsonProperty("mid")
		BigDecimal midRate
) {}
