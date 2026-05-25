package com.financeapi.finance_api.registration.service.command;

public record RegisterCommand(String pesel, String firstName, String lastName, String mothersMaidenName, String email) {
}
