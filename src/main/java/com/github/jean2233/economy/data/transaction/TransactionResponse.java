package com.github.jean2233.economy.data.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionResponse {
    SUCCESS("§aSuccess!"),
    INVALID_AMOUNT("§cInvalid amount."),
    INSUFFICIENT_BALANCE("§cInsufficient balance."),
    UNKNOWN_ERROR("§cUnknown error.");

    private final String message;
}
