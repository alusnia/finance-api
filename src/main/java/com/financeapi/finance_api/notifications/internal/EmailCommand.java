package com.financeapi.finance_api.notifications.internal;

public record EmailCommand(String receiver, String subject, String body) {
}
