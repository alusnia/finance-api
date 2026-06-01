package com.financeapi.finance_api.user.internal.auth;

record ChangePasswordCommand(String token, String newPassword) {
}
