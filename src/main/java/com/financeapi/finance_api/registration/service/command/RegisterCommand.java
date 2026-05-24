package com.financeapi.finance_api.registration.service.command;

public record RegisterCommand(String pesel, String name, String surname, String mothersMaidenName, String email) {
}
