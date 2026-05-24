package com.financeapi.finance_api.registration.service.command;

public record RegisterProfileCommand(String name, String surname, String cif, String email, String token) {
}
