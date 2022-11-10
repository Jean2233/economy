package com.github.jean2233.economy.data.wallet;

import com.github.jean2233.economy.data.transaction.TransactionResponse;
import com.github.jean2233.economy.util.Formats;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.github.jean2233.economy.data.transaction.TransactionResponse.*;

@Data
@AllArgsConstructor
public class Wallet {
    private double balance;
    private boolean dirty;

    public TransactionResponse deposit(double amount) {
        if (!isValidAmount(amount)) return INVALID_AMOUNT;

        this.balance += amount;
        this.dirty = true;

        return SUCCESS;
    }

    public TransactionResponse withdraw(double amount) {
        if (!isValidAmount(amount)) return INVALID_AMOUNT;
        if (!contains(amount)) return INSUFFICIENT_BALANCE;

        this.balance -= amount;
        this.dirty = true;

        return SUCCESS;
    }

    public TransactionResponse set(double amount) {
        if (!isValidAmount(amount)) return INVALID_AMOUNT;

        this.balance = amount;
        this.dirty = true;

        return SUCCESS;
    }

    public boolean contains(double amount) {
        return this.balance >= amount;
    }

    public String getFormattedBalance() {
        return Formats.apply(this.balance);
    }

    private boolean isValidAmount(double amount) {
        return !Double.isNaN(amount);
    }
}
