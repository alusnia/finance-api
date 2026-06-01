package com.financeapi.finance_api.user.internal.registration;

record RegisterCommand(String firstName, String lastName, String email, String pesel) {
}
