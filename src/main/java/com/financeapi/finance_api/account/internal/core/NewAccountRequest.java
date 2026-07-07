package com.financeapi.finance_api.account.internal.core;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record NewAccountRequest(@NotBlank String currency) {
}
